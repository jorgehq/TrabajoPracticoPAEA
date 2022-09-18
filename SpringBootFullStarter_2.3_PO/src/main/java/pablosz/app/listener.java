package pablosz.app;

import org.springframework.stereotype.Component;

@Component
public class listener implements Runnable
{
	private boolean estaCerrado=false;
	private int timeout=0;
	private int contador=0;
	



	@Override
	public void run()
	{
		
		
	}
	public int getContador()
	{
		return contador;
	}

	public void setContador(int contador)
	{
		this.contador=contador;
	}
	public int getTimeout()
	{
		return timeout;
	}

	public void setTimeout(int timeout)
	{
		this.timeout=timeout;
	}

	public synchronized void notificarCierre() {
		estaCerrado=true;
	}
	public synchronized void notificarApertura() {
		estaCerrado=false;
	}
	public void sesionClosed(int id) {
		this.notificarCierre();
		System.out.println("Se cierra la sesion "+id);
		this.sesionStillClosed(id);
		
	}
	
	public void sesionStillClosed(int id){
		while(estaCerrado) {
			try
			{
				Thread.sleep((long)10000.0);
				System.out.println("Sesion "+id+" sigue cerrada");
			}
			catch(InterruptedException e)
			{
				
				e.printStackTrace();
			};
		}
	}
	public void sesionAbierta(int id) {
		this.notificarApertura();
		System.out.println("Se abre la sesion "+id);
		this.sesionSigueAbierta(id);

	}
	public void sesionSigueAbierta(int id) {
		
		
		while(estaCerrado==false && contador!=this.getTimeout()) {
			
			try
			{
				Thread.sleep((long)10000.0);
				System.out.println("Sesion "+id+" sigue Abierta");
				contador+=10;
			}
			catch(InterruptedException e)
			{
				
				e.printStackTrace();
			};
		}
		sesionClosed(id);
	}

}
