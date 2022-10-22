package pablosz.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Async;

public class listener extends Thread 
{
	
	private List<sessionlistener> lista=new ArrayList<sessionlistener>() ;
	
	public void sesionClosed(int id) {
		
		System.out.println("Se cierra la sesion "+id);
		
		this.sesionStillClosed(id);
		
	}
	
	public void sesionStillClosed(int id){
		
		
				System.out.println("Sesion "+id+" sigue cerrada");
			
	}
	public void sesionAbierta(int id) {
		
		System.out.println("Se abre la sesion "+id);

	}
	public void sesionSigueAbierta(int id) {
		
				System.out.println("Sesion "+id+" sigue Abierta");
	}
	
	@Override
	public void run()
	{
		
	}

	@Async
	public void runListener() {
		while(true) {
			try {
				Thread.sleep(10000);
				this.revisar();
				
			}catch(InterruptedException e){
				
			}
		}
	}
	
	public void revisar() {
		for(sessionlistener s:lista) {
			if(((s.getTiempo()*1000)+s.getLastUpdate())<(int)System.currentTimeMillis()) {
				this.sesionSigueAbierta(s.getId());
			}else {
				this.sesionStillClosed(s.getId());
			}
		}
	}
	
	public void agregarALista(sessionlistener ls) {
		lista.add(ls);
	}
	
	public void eliminarDeLista(sessionlistener ls) {
		lista.remove(ls);
	}

}
