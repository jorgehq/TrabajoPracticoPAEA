package pablosz.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
@Component
public class Listener  implements SessionListener 
{


	private static final long loopThread=1000;
	
	private static final long key1 = 1;
	private static final long timeOut1 = 5000;
	
	private static final long key2 = 2;
	private static final long timeOut2 = 10000;
	
	private int session1Opened=0;
	private int session2Opened=0;	
	private int session1StillOpened=0;
	private int session2StillOpened=0;
	
	
	public void cero() {
		session1Opened=0;
		session2Opened=0;
		session1StillOpened=0;
		session2StillOpened=0;

	}
	
	
	
	@Async("threadPoolExecutor")
	public  void runListener(List<objListener>lista)   {
		System.out.println("Estoy creando un hilo");
		while(true) {
			
				try
				{
					Thread.sleep(1000);
				}
				catch(InterruptedException e)
				{
					
				}
				System.out.println("Hilo running....");
				
				for(objListener obj:lista) {
					System.out.println("Vencimiento: "+obj.getVencimiento()+""
							+ " == Tiempo en mili actual: "+System.currentTimeMillis()+""
									+ " == ESta abierto la sesion? "+obj.isOpen());

					
					if(obj.getVencimiento()<System.currentTimeMillis()&&obj.isOpen()) {
						sessionClosed(obj.getId());
						obj.setOpen(false);
						
					}else if(obj.isOpen()==false) {
						sessionStillClosed(obj.getId());
					}else {
						sessionStillOpened(obj.getId());
						
					}
				
					
				}
				System.out.println("Sesion 2 variable Still "+session2StillOpened+" and Sesion 1 variable Still "+session1StillOpened);


		}
		
	}
	
	public Listener()
	{
		super();
	}



	public void resulta() {
		System.out.println(this.session1Opened);
		System.out.println(this.session2Opened);
	}
	
	public long getloopThread() {
		return loopThread;
	}
	
	public int getSession1Opened()
	{
		return session1Opened;
	}



	public void setSession1Opened(int session1Opened)
	{
		this.session1Opened=session1Opened;
	}



	public int getSession2Opened()
	{
		return session2Opened;
	}



	public void setSession2Opened(int session2Opened)
	{
		this.session2Opened=session2Opened;
	}



	public int getSession1StillOpened()
	{
		return session1StillOpened;
	}



	public void setSession1StillOpened(int session1StillOpened)
	{
		this.session1StillOpened=session1StillOpened;
	}



	public int getSession2StillOpened()
	{
		return session2StillOpened;
	}



	public void setSession2StillOpened(int session2StillOpened)
	{
		this.session2StillOpened=session2StillOpened;
	}



	//Funciones dadas por la implementacion que se modificaron

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
		System.out.println("Still Opened "+key);
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
		System.out.println("Closed "+key);
	}

	@Override
	public void sessionStillClosed(long key)
	{
		final int k=(int)key;
		final int k1=(int)key1;
		final int k2=(int)key2;
		/*
		 * Porque sigue aumentando el still open
		switch( k )
		{
			case k1:
				session1StillOpened++;
				break;
			case k2:
				session2StillOpened++;
				break;
		}
		*/
		System.out.println("Still closed "+key);
	}
	
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
		System.out.println("Opened "+key);
	}







}

