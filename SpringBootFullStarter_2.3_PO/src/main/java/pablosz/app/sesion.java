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
	private int id;
	@Column(name="tlimite")
	private int tlimite;
	@Column(name="obj")
	private String obj;

	
	public sesion() {};
	
	public sesion(int id,int tlimite)
	{
		setId(id);
		setTlimite( tlimite);
		setObj("");
	
	}
	
	

	@Override
	public String toString()
	{
		
		return "sesion [id="+id+", tlimite="+tlimite+", obj="+obj+"]";
	}

	public String getObj()
	{
		return obj;
	}

	public void setObj(String obj)
	{
		this.obj=obj;
	}

	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id=id;
	}
	public int getTlimite()
	{
		return tlimite;
	}
	public void setTlimite(int tlimite)
	{
		this.tlimite=tlimite;
	}

	
}
