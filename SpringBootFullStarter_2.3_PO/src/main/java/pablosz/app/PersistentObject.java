package pablosz.app;



import static org.mockito.ArgumentMatchers.anyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.assertj.core.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;




@Component
public class PersistentObject 
{
	listener lis=new listener();

	private static Logger LOG = LoggerFactory.getLogger(Application.class);
	@Autowired
	private EntityManager em;
	
	

	
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
	
	
	//Funcion insert permite iniciar una nueva sesion si la sesion ya existe no se crea de nuevo.
	public void createSession(long key,int i) {

			em.persist(new sesion(key,i));	
		
	}
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

	//Funcion pedida que permite persistir cualquier objeto en una sesion relacionada  con la id 
	public void store(long id,Object o) {
		sesion s=this.buscarID(id);
		ObjectMapper om=new ObjectMapper();

		try
		{
			String json=om.writeValueAsString(o);
			if(s.getObj()=="") {
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
	}
	
	
	/*
	 List <listener> lista=
	 while(true){
	 	try{
	 		for(int i=0;i<listaListener.length;i++){
	 			
	 		}
	 		sleep(10000)
	 	}
	 }
	 * */
	
}


