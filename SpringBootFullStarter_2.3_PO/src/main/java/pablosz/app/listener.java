package pablosz.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
@Component
public class Listener implements SessionListener
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
	}







}

