package pablosz.app;

public class sessionlistener
{
	private int id;
	private int tiempo;
	private int lastUpdate;
	
	

	public sessionlistener() {
		
	}
	
	public sessionlistener(int id,int tiempo,int lastUpdate)
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
