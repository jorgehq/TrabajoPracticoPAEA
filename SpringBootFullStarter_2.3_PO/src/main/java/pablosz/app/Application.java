package pablosz.app;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;


// +---------------------------------------------------------+
// | NOTA: Si queremos organizar los componentes y entidades | 
// | dentro de diferentes paquetes tendremos que especificar |
// | explicitamente cada uno de estos descomenando las       |
// | siguientes lineas: @ComponentScan y @EntityScan         |
// +---------------------------------------------------------+
// @ComponentScan(basePackages={"pablosz.pkgcomponentes1,pablosz.pkgcomponentes2,etc..."})
// @EntityScan(basePackages={"pablosz.pkgdomain1,pkgpablosz.domain2,etc"})

@SpringBootApplication
@Transactional
@EnableAsync
public class Application implements CommandLineRunner
{
	/*
	public TaskExecutor getAsyncExcutor() {
		
	}
	*/
    private static Logger LOG = LoggerFactory.getLogger(Application.class);

    @Autowired
	private sesionDao d;
    @Autowired
    private EntityManager em;
    
    

	public static void main(String[] args)
	{
		SpringApplication.run(Application.class,args);
	}
	
	

	
	@Override
	public void run(String... args) throws Exception
	{
		LOG.info("Todo funciona correctamente? "+(em!=null));
		listener ls=new listener();
		sesion n=new sesion(3,80);
		ls.agregarALista(new sessionlistener(n.getId(),n.getTlimite(),n.getLast()));
		ls.runListener();
		System.out.println(n.toString());
		
		
		d.insert(n);
		
		persona p=new persona("Tomas","Perez",3);
		d.store(3,p);
		
		d.mostrarTodasSesiones();
		
		d.load(3,persona.class);
		
		d.remove(3,persona.class);
		
		d.mostrarTodasSesiones();
		d.destroySesion(3);
		d.mostrarTodasSesiones();
		
	}
		

}
