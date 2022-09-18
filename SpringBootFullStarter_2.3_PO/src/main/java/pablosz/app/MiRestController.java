package pablosz.app;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MiRestController
{

	

	@RequestMapping("/hola")
	public String test()
	{
		return "Hola Mundo!";
	}
	@RequestMapping("/ingresar")
	public String ingresar(Model request,@RequestParam("id") int id,@RequestParam("tlimite") int tlimite){
		/*
		li.setTimeout(tlimite);
		li.setContador(0);
		li.sesionAbierta(id);
		
		sesion s=new sesion();
		s.setId(id);
		s.setTlimite(tlimite);

		
		f.insertSesion(s);
		*/
		return "Sesion iniciada";
		
	}
	@RequestMapping("/eliminar")
	public String eliminar(Model request,@RequestParam("id") int id) {
	//	se.deleteById(id);
		return "sesion "+id+" eliminada";
	}
	@RequestMapping("/cerrar")
	public String cerrar(Model request,@RequestParam("id") int id) {
		
		return "sesion "+id+" cerrada";
	}
	@GetMapping("/mostrar")
	public List<sesion> mostrar(){
		
		return null;
	}
	
}
