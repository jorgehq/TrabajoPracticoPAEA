package pablosz.app;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public interface SessionListener
{
public  void sessionClosed(long id);
	
public void sessionStillClosed(long id);
public void sessionOpened(long id);
		


public void sessionStillOpened(long id);



		
			
	
	
}
