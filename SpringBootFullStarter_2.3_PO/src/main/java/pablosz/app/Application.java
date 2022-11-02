package pablosz.app;
import static org.mockito.ArgumentMatchers.any;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import pablosz.ann.NotPersistable;


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
	private PersistentObject po;
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


		/*
		int number=5;
		String nom="pablo";
		MiClase1 mc1 = new MiClase1(12,2,3);
		mc1.setAttPersistable("x");
		mc1.setAttNoPersistable("y");
		

		System.out.println(mc1.toString());
		po.notPersistableRemove(mc1,mc1.getClass());
	    System.out.println(mc1.toString());
	*/
		/*
		persona pe=(persona)po.load(7,persona.class);
		System.out.println(pe.toString());
		
		po.remove(7,persona.class);
		
		po.mostrarTodasSesiones();
		po.destroySession(7);
		po.mostrarTodasSesiones();
		*/
		
	}

	}

