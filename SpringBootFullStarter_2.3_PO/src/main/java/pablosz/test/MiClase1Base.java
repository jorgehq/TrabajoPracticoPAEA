package pablosz.test;

import pablosz.ann.Persistable;

public class MiClase1Base 
{
	private String attNoPersistable;
	
	@Persistable
	private String attPersistable;

	public String getAttNoPersistable()
	{
		return attNoPersistable;
	}
	public void setAttNoPersistable(String attNoPersistable)
	{
		this.attNoPersistable=attNoPersistable;
	}
	public String getAttPersistable()
	{
		return attPersistable;
	}
	public void setAttPersistable(String attPersistable)
	{
		this.attPersistable=attPersistable;
	}
}
