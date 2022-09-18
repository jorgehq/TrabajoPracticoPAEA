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

import pablosz.app.Implement.funcionesSQL;

/*
 Nota:No se reconoce la aplicacion de Query en este proyecto.Para consultar.
 Opte por el metodo anterior, existe el JpaRepositoy que te brinda todas las funciones los extiendo a una implementacion
 y la uso como variable en sesion Dao @Componente y puedo usar todas las funciones que interactual con la base de datos
 
 * */
@Component
public class sesionDao
{
	private static Logger LOG = LoggerFactory.getLogger(Application.class);
	@Autowired
	private EntityManager em;
	@Autowired
	private funcionesSQL f;
	
	public void insert(sesion s) {
		if(f.existsById(s.getId())){
			LOG.info("Sesion ya existe");
		}else {
			em.persist(s);	
		}
	}
	public void mostrar() {
		
		System.out.println(f.findAll().toString());
		
	}
	public void store(int id,Object o) {
		sesion s=f.findById(id).get();
		s.setObj(o.toString());
		em.persist(s);

	}
	

}
