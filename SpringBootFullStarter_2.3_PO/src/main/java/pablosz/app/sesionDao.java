package pablosz.app;



import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
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

import pablosz.app.Implement.funcionesSQL;

/*
 Nota:No se reconoce la aplicacion de Query en este proyecto.Para consultar.
 Opte por el metodo anterior, existe el JpaRepositoy que te brinda todas las funciones los extiendo a una implementacion
 y la uso como variable en sesion Dao @Componente y puedo usar todas las funciones que interactual con la base de datos
 
 * */
@Component
public class sesionDao
{
	listener lis=new listener();

	private static Logger LOG = LoggerFactory.getLogger(Application.class);
	@Autowired
	private EntityManager em;
	@Autowired
	private funcionesSQL f;
	
	//Funcion insert permite iniciar una nueva sesion si la sesion ya existe no se crea de nuevo.
	public void insert(sesion s) {
		if(f.existsById(s.getId())){
			LOG.info("Sesion ya existe");
		}else {
			em.persist(s);	
		}
	}
	//Funcion que permite mostrar todos las sesiones guardadas en la tabla sql
	public void mostrar() {
		
		System.out.println(f.findAll().toString());
		
	}
	//Funcion pedida que permite persistir cualquier objeto en una sesion relacionada  con la id 
	public void store(int id,Object o) {
		sesion s=f.findById(id).get();
		ObjectMapper om=new ObjectMapper();
		try
		{
			s.setObj(om.writeValueAsString(o));
		}
		catch(JsonProcessingException e)
		{
			e.printStackTrace();
		}
		
		em.persist(s);

	}
	public <T> Object load(int id,Class<T> cl) {
		ObjectMapper om=new ObjectMapper();
		sesion s=f.findById(id).get();
		Object ob= new Object();
		try
		{
			
			ob=om.readValue(s.getObj(),cl);
			
			
		}
		catch(JsonMappingException e)
		{
			
			e.printStackTrace();
		}
		catch(JsonProcessingException e)
		{
			e.printStackTrace();
		}
		return ob;
		
	}
	public <T> Object remove(int id,Class<T> cl) {
		Object r=this.load(id,cl);
		
		this.store(id,"");
		
		return r;
		
	}
	public void destroySesion(int id) {
		f.deleteById(id);
		LOG.info("Sesion "+id+" eliminada");
	}
	
}

