package pablosz.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import pablosz.app.Application;
import pablosz.app.PersistentObject;
import pablosz.app.SessionListener;
import pablosz.app.Listener;


@SpringBootTest(classes=Application.class)
@Transactional
@EnableAsync

public class SessionListenerTest 
{
	@Autowired
	private PersistentObject po;
	@Autowired
	private Listener l;
	
	private static final long loopThread=1000;
	
	private static final long key1 = 1;
	private static final long timeOut1 = 5000;
	
	private static final long key2 = 2;
	private static final long timeOut2 = 10000;
	
	
	
	@BeforeEach
	public void before()
	{
		l.cero();
		po.createSession(key1,timeOut1);
		po.createSession(key2,timeOut2);
		
		
	}
	
	@AfterEach
	public void after()
	{
		po.destroySession(key1);
		po.destroySession(key2);
	}
	
	@Test
	public void testSessionOpenedClosed()
	{
		
		System.out.println("=================================Comienzo test 1===================================");
		
		System.out.println("Primera evaluacion= SEssion 1 :"+l.getSession1Opened()+" SEssion 2 :"+l.getSession2Opened());
		
		assertTrue(l.getSession1Opened()==1);
		assertTrue(l.getSession2Opened()==1);
		
		esperar(timeOut1/2);
		System.out.println("Segunda evaluacion= SEssion 1 :"+l.getSession1Opened()+" SEssion 2 :"+l.getSession2Opened());
		assertTrue(l.getSession1Opened()==1);		
		assertTrue(l.getSession2Opened()==1);
		
		esperar((timeOut1/2)+loopThread*2);
		System.out.println("Tercera evaluacion= SEssion 1 :"+l.getSession1Opened()+" SEssion 2 :"+l.getSession2Opened());	
		assertTrue(l.getSession1Opened()==0);
		assertTrue(l.getSession2Opened()==1);
		
		long restaEsperar = Math.abs(timeOut1-timeOut2)+loopThread*2;
		esperar(restaEsperar);
		assertTrue(l.getSession1Opened()==0);
		assertTrue(l.getSession2Opened()==0);
		
		System.out.println("SEssion 1 :"+l.getSession1Opened()+" SEssion 2 :"+l.getSession2Opened());
		System.out.println("SEssion sigue abierta 1 :"+l.getSession1StillOpened()+" SEssion sigue abierta 2 :"+l.getSession2StillOpened());
		System.out.println("==================00Termino el test 1===============================");
	}
	
	@Test
	public void testSessionStillOpenedClosed()
	{
		System.out.println("=================================0Test 2=============================");
		System.out.println("SEssion 1 :"+l.getSession1Opened()+" SEssion 2 :"+l.getSession2Opened());
		System.out.println("SEssion sigue abierta 1 :"+l.getSession1StillOpened()+" SEssion sigue abierta 2 :"+l.getSession2StillOpened());
		
		assertTrue(l.getSession2Opened()==1);
		
		// no deberia haberse llamado a sessionSTillOpen
		int i=0;
		assertTrue(l.getSession2StillOpened()==i);
		
		
		long acum = 0;
		while( acum<timeOut2)
		{
			// espero, aun debe estar abierta
			
			
			i++;
			
			esperar(loopThread+10);
			
			System.out.println("Sesion 2 todavia abierto instancia :"+l.getSession2StillOpened());
			System.out.println("Sesion 2 instancia I "+i);
			
			//Esto no va a funcionar ya que las instancias de sesion todavia abierta siempre se detendra en 9 porque en la 10° vuellta
			//se cierra la sesion, por lo el problema viene con el i++ que seguira acumulando y comparara 9==10 y rompera
		   //assertTrue(l.getSession2StillOpened()==i);
			
			acum+=loopThread;
			System.out.println("Acumulador "+acum);
			
		}
		
		// expiro el tiempo, paso a closed
		System.out.println("Expiro Sesion 2  :"+l.getSession2Opened());
		assertTrue(l.getSession2Opened()==0);

		// espero un loop, debe seguir en closed
		esperar(loopThread);
		System.out.println("Ultimo Sesion 2 still :"+l.getSession2StillOpened());
		assertTrue(l.getSession2StillOpened()==i-1);
		System.out.println("==================00Termino el test 2===============================");

	}	
	
/*
	@Override
	public void sessionOpened(long key)
	{
		final int k=(int)key;
		final int k1=(int)key1;
		final int k2=(int)key2;
		
		switch( k )
		{
			case k1:
				session1Opened++;
				break;
			case k2:
				session2Opened++;
				break;
		}
	}

	@Override
	public void sessionStillOpened(long key)
	{
		final int k=(int)key;
		final int k1=(int)key1;
		final int k2=(int)key2;
		
		switch( k )
		{
			case k1:
				session1StillOpened++;
				break;
			case k2:
				session2StillOpened++;
				break;
		}		
	}
	

	@Override
	public void sessionClosed(long key)
	{
		final int k=(int)key;
		final int k1=(int)key1;
		final int k2=(int)key2;
		
		switch( k )
		{
			case k1:
				session1Opened--;
				break;
			case k2:
				session2Opened--;
				break;
		}		
	}

	@Override
	public void sessionStillClosed(long key)
	{
		final int k=(int)key;
		final int k1=(int)key1;
		final int k2=(int)key2;
		
		switch( k )
		{
			case k1:
				session1StillOpened++;
				break;
			case k2:
				session2StillOpened++;
				break;
		}		
	}
	*/
	public void esperar(long n)
	{
		try
		{
			Thread.sleep(n);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
