package pablosz.app;

//Clase que guarda datos prioritarios de una sesion para realizar el control  de cada sesion sobre el
// tiempo limite y la ultima modificacion. Con estos datos se identificara si supero el tiempo para poder cerrarlo.
public class objListener
{
	private int id;
	private int tiempo;
	private int lastUpdate;
	
	

	public objListener() {
		
	}
	
	public objListener(int id,int tiempo,int lastUpdate)
	{
		super();
		this.id=id;
		this.tiempo=tiempo;
		this.lastUpdate=lastUpdate;
	}
	

	public int getLastUpdate()
	{
		return lastUpdate;
	}

	public void setLastUpdate(int lastUpdate)
	{
		this.lastUpdate=lastUpdate;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id=id;
	}

	public int getTiempo()
	{
		return tiempo;
	}

	public void setTiempo(int tiempo)
	{
		this.tiempo=tiempo;
	}

}
