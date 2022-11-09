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
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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
@EnableAsync(proxyTargetClass=true)
public class Application  implements CommandLineRunner 
{
	/*
	public TaskExecutor getAsyncExcutor() {
		
	}
	*/
    private static Logger LOG = LoggerFactory.getLogger(Application.class);

    
    @Autowired
    private EntityManager em;
   
   
  
  
    @Autowired
    private Listener p;
    @Autowired
    private PersistentObject po;
    
    @Bean("threadPoolExecutor")
    public TaskExecutor getAsyncExecutor() {
    	ThreadPoolTaskExecutor	executor= new ThreadPoolTaskExecutor();
    	executor.setCorePoolSize(2);
    	executor.setMaxPoolSize(2);
    	executor.setQueueCapacity(100);
    	executor.setThreadNamePrefix("Async-");
    	executor.initialize();
    	return executor;
    }
    

	public static void main(String[] args) 
	{
		SpringApplication.run(Application.class,args);
	}
	
	

	
	@Override
	public void run(String... args) throws Exception
	{
		LOG.info("Todo funciona correctamente? "+(em!=null));


		
		
		
		
//Tengo dos componentes diferentes el Listener implementa sessionListener y lo modifica. Por otro lado esta SessionListener Autowired
// que llama a una instancia de esta. Esta instancia permitira al ejecutar sus metodos modificar las variables que estan en 
// la clase Listener.
		/*
		System.out.println(p.getSession1Opened()+" "+p.getSession2Opened());
		sl.sessionOpened(1);
		sl.sessionOpened(2);
		System.out.println(p.getSession1Opened()+" "+p.getSession2Opened());
*/
		
		
		
	
		/*
		 po.createSession(1,50);
		po.destroySession(1);
		int number=5;
		String nom="pablo";
		MiClase1 mc1 = new MiClase1(12,2,3);
		mc1.setAttPersistable("x");
		mc1.setAttNoPersistable("y");
		 * 
		persona pe=(persona)po.load(7,persona.class);
		System.out.println(pe.toString());
		
		po.remove(7,persona.class);
		
		po.mostrarTodasSesiones();
		po.destroySession(7);
		po.mostrarTodasSesiones();
		*/
		
	}
	

	}

