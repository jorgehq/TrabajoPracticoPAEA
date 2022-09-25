package pablosz.app;


public class listener 
{

	public listener() {
		
	}
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

}
