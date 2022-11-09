package pablosz.app;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import pablosz.ann.Persistable;

@Entity
@Table (name="sesion")
public class sesion
{
	@Id
	@Column(name="id")
	private long id;
	@Column(name="tlimite")
	private long tlimite;
	@Column(name="obj")
	private String obj;
	@Column(name="lastA")
	private int lastA;

	
	public sesion() {};
	
	public sesion(long id,long tlimite)
	{
		setId(id);
		setTlimite( tlimite);
		setObj(null);
		setLast((int)System.currentTimeMillis());
	
	}
	
	

	

	@Override
	public String toString()
	{
		return "sesion [id="+id+", tlimite="+tlimite+", obj="+obj+", lastA="+lastA+"]";
	}

	public int getLast()
	{
		return lastA;
	}

	public void setLast(int last)
	{
		this.lastA=last;
	}

	public String getObj()
	{
		return obj;
	}

	public void setObj(String obj)
	{
		this.obj=obj;
	}

	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id=id;
	}
	public long getTlimite()
	{
		return tlimite;
	}
	public void setTlimite(long tlimite)
	{
		this.tlimite=tlimite;
	}

	
}
