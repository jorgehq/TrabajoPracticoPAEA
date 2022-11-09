package pablosz.app;



import static org.mockito.ArgumentMatchers.anyList;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.lang.Runnable;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.assertj.core.util.Arrays;
import org.hibernate.mapping.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import pablosz.ann.NotPersistable;





@Component


public class PersistentObject
{
	
	

	private static Logger LOG = LoggerFactory.getLogger(Application.class);
	private boolean hiloOn=false;
	
	@Autowired
	private EntityManager em;
	
	//Para que funciones esta implementacion se debe invocar antes un Autowired de Listener que implementa SessionListener y  modifica 
//sus metodos sobreescribiendolos. Con los metodos modificados existira un vinculo donde no tienes que interactuar con Listener
//para modificar sus variables sino que la llamada de los metodos de la implementacion que se hizo un autowired
//haran ese trabajo.
	
	@Autowired
	private SessionListener sl;


	private List<objListener> lista= new ArrayList<objListener>();
	
	
	/*
	 Falta crear una lista con la que el hilo va a trabajar controlando los tiempos con los datos de la sesion.
	 El hilo se usa invocandolo con el nombre dentro de la clase o desde afuera con autowired po.runListener
	 De todas las formas correra el hilo.
	 * */
	@Async("threadPoolExecutor")
	public void runListener() {
		
		while(true) {
			
				try
				{
					Thread.sleep(1000);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				System.out.println("Hilo running....");
				
				for(objListener obj:lista) {
					if(obj.getVencimiento()>System.currentTimeMillis()&&obj.isOpen()) {
						sl.sessionClosed(obj.getId());
						obj.setOpen(false);
						
					}else if(obj.isOpen()==false) {
						sl.sessionStillClosed(obj.getId());
					}else {
						sl.sessionStillOpened(obj.getId());
					}
					
				}
				if(lista.size()==0) {
					hiloOn=false;
					break;
				}
				
				

		}
	}
	
	
    

	
	public boolean isHiloOn()
	{
		return hiloOn;
	}





	public void setHiloOn(boolean hiloOn)
	{
		this.hiloOn=hiloOn;
	}





	//Funcion insert permite iniciar una nueva sesion si la sesion ya existe no se crea de nuevo.
	//En esta se inicia el hilo y se controlara que no exista otro.
	//Falta guardar datos en la lista de objListener como objeto;
	public void createSession(long key,long i) {
		
		em.persist(new sesion(key,i));
	
			//Version nueva delego esas variables a una clase nueva que se inyecta en el test como una nueva instancia y funciona
		System.out.println("Session "+key+" creada");
		if(hiloOn) {
				System.out.println("Hilo running....");
				sl.sessionOpened(key);
				lista.add(new objListener(key,i));
		}else {
				System.out.println("Hilo Start");
				sl.sessionOpened(key);
				lista.add(new objListener(key,i));
				hiloOn=true;
				sl.runListener(lista);
				
			}
		
	}
	

	//Funcion pedida que permite persistir cualquier objeto en una sesion relacionada  con la id 
	public void store(long id,Object o) {
		sesion s=this.buscarID(id);
		ObjectMapper om=new ObjectMapper();
		notPersistableRemove(o,o.getClass());

		try
		{
			String json=om.writeValueAsString(o);
			if(s.getObj()==null) {
				s.setObj(json);
			}else {
				s.setObj(s.getObj()+";"+json);
			}
			
		}
		catch(JsonProcessingException e)
		{
			e.printStackTrace();
		}
		
		em.persist(s);

	}
	public Boolean esString(String objeto) {
		if(objeto.charAt(0)=='"') {
			return true;
		}
		return false;
		
		
	}
	
	public <T> Object load(long id,Class<T> cl) {
		sesion s=this.buscarID(id);
		Object ob= new Object();
		
			if(s==null) {
				return null;
				
			}else {
				ob=this.comprobarInstancias(cl,s.getObj());
			}
			s.setLast((int)System.currentTimeMillis());
			em.persist(s);
		return ob;
		
	}
	public <T> Object remove(long id,Class<T> cl) {
		Object r=this.load(id,cl);
		sesion s=buscarID(id);
		s.setObj(removerInstancia(cl,s.getObj()));
		s.setLast((int)System.currentTimeMillis());
		em.persist(s);
		return r;
		
	}
	
	public void destroySession(long id) {
		
		this.eliminarID(id);
		LOG.info("Sesion "+id+" eliminada");
		sl.sessionClosed(id);
		
		int i=0;
		for(objListener obj:lista) {
			if(obj.getId()==id) {
				break;
			}
			i++;
		}
		lista.remove(i);
		System.out.println("Tamanio list "+lista.size());
		
	}
	
	
/*
 * Funciones que Su funcionalidad es recorrer la lista definida en busca de una determinada clase dada por parametro
 * Una vez encontrada se realizara la accion definida por el metodo.
 * */
	
	public <T>Object comprobarInstancias(Class<T> cl,String objetos){
		String[] instancias=objetos.split(";");
		System.out.println("Buscando instancia de clase en la lista");
		ObjectMapper om=new ObjectMapper();
		
		for(int i=0;i<instancias.length;i++) {
			
			try
			{
				if(esString(instancias[i]) && cl.getName()=="java.lang.String") {
					
					System.out.println("Instancia Correcta en "+i);
					String s=(String)om.readValue(instancias[i],cl);
					s.replace("\"","");
					return (Object)s;
				}else if(cl.getName()!="java.lang.String") {
					Object o= om.readValue(instancias[i],cl);
					System.out.println("Instancia Correcta en "+i);
					return o;
				}else {
					System.out.println("Instancia Incorrecta en "+i);
				}
			
				
			}
			catch(JsonMappingException e)
			{
				
				System.out.println("No es la instancias correcta en "+i);
			}
			catch(JsonProcessingException e)
			{
				// TODO Auto-generated catch block
				System.out.println("No es la instancias correcta en "+i);
				
			}
		}
		return null;
		
		
		
		
	}
	
	public <T>String removerInstancia(Class<T> cl,String objetos){
		List<Object> container = Arrays.asList(objetos.split(";"));
		ObjectMapper om=new ObjectMapper();
		System.out.println("Proceso de remover..........");
		String nuevo="";
	
		for(int i=0;i<container.size();i++) {
			try
			{
				if(esString((String)container.get(i)) && cl.getName()=="java.lang.String") {
					
					System.out.println("Instancia Correcta en "+i+" Removiendo");
					
				}else if(cl.getName()!="java.lang.String") {
					 om.readValue((String)container.get(i),cl);
					
					System.out.println("Instancia Correcta en "+i+" Removiendo");
					
				}else {
					System.out.println("Instancia Incorrecta en "+i+" Agregando");
					if(nuevo=="") {
						nuevo=""+(String)container.get(i);	
					}else {
						nuevo=nuevo+";"+(String)container.get(i);
					}
					
				}
			
				
			}
			catch(JsonMappingException e)
			{
				
				System.out.println("No es la instancias correcta en "+i);
				if(nuevo=="") {
					nuevo=""+(String)container.get(i);	
				}else {
					nuevo=nuevo+";"+(String)container.get(i);
				}
			}
			catch(JsonProcessingException e)
			{
				// TODO Auto-generated catch block
				System.out.println("No es la instancias correcta en "+i);
				if(nuevo=="") {
					nuevo=""+(String)container.get(i);	
				}else {
					nuevo=nuevo+";"+(String)container.get(i);
				}
			}
		}
		return nuevo;
		
		
		
		
	}
	//===================================Funciones Complementarias=======================================
	
	public void mostrarTodasSesiones() {
		String hql="from sesion s";
		Query q=em.createQuery(hql);
		
		System.out.println(q.getResultList().toString());
	}
	
	public sesion buscarID(long id) {
		String hql="from sesion s where id="+id;
		Query q=em.createQuery(hql);
		
		try {
			sesion s=(sesion)q.getSingleResult();
			return s;
		}catch(NoResultException e) {
			
			return null;
		}
		
	}
	public void eliminarID(long id) {
		String hql="delete from sesion s where id="+id;
		Query q=em.createQuery(hql);
		q.executeUpdate();
		
	}
	
	//Metodo que devuelve todos los campos de una clase incluyendo las subclaces. ESto se logra mediante la recursividad usando superClass
	public   List<Field> getAllFields(Class<?> type) {

		  List<Field> fields = new ArrayList<Field>();
	        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
	        	for(Field a:(c.getDeclaredFields())){
	        		fields.add(a);
	        	}
	        }
	        return fields;
       
    }
	
	//Metodo que controla que el objeto no tenga variables no persistable seteandolos en null cuando se encuentre
    public  void notPersistableRemove (Object modificado,Class<?> obj) {
    	List<Field> campos=getAllFields(obj);
		
	    for(Field f:campos) {
		
			if(f.isAnnotationPresent(NotPersistable.class)) {
				//Permite que se pueda modificarla variable
				f.setAccessible(true);
				
				try
				{
					//Modificacion de la variable
					f.set(modificado,null);
				}
				catch(IllegalArgumentException e)
				{
					
					e.printStackTrace();
				}
				catch(IllegalAccessException e)
				{
					
					e.printStackTrace();
				}
				
				System.out.println("No peristable");
			}else {
				
				
				System.out.println("Persistable");
			}
			
		}
	    
	   
    }










		
	
}


