package pablosz.app;

//Clase que guarda datos prioritarios de una sesion para realizar el control  de cada sesion sobre el
// tiempo limite y la ultima modificacion. Con estos datos se identificara si supero el tiempo para poder cerrarlo.
public class objListener
{
	private long id;
	private long vencimiento;
	private boolean open;
	
	

	public objListener() {
		
	}
	
	public objListener(long id,long tiempo)
	{
		super();
		this.id=id;
		long actual=(long)(tiempo+System.currentTimeMillis());
		this.vencimiento=actual;
		this.open=true;
	}
	

	public boolean isOpen()
	{
		return open;
	}

	public void setOpen(boolean open)
	{
		this.open=open;
	}

	public  long getId()
	{
		return id;
	}

	public void setId( long id)
	{
		this.id=id;
	}

	public  long getVencimiento()
	{
		return vencimiento;
	}

	public void setVencimiento( long tiempo)
	{
		this.vencimiento=tiempo;
	}

}
