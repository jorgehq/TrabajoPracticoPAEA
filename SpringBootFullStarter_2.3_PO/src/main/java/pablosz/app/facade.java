package pablosz.app;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class facade
{
	
	@Autowired
	private EntityManager em;
	
	public List<sesion> buscarTodos(){
		String hql="FROM sesion s";
		Query q=em.createNamedQuery(hql);
		return q.getResultList();
	}
	public void insertSesion(int id,int tl) {
		sesion s=new sesion(id,tl);
		em.persist(s);
	    
	}
	

}
