package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name="phone")
public class Phone extends Model
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private String id;

	@ManyToOne
	@JoinColumn(name="hub_id")
	private Hub hub;
	
	@Column(name="model")
	private String model;
	
	@Column(name="manufacturer")
	private String manufacturer;
	
	@Column(name="os")
	private String os;
	
	@Column(name="os_ver")
	private String osVer;
	
	public static Finder<String, Phone> find = new Model.Finder<>(String.class, Phone.class);
	
	public static List<Phone> findAll()
	{
		return new ArrayList<Phone>(Phone.find.all());
	}
	
	public static Phone findById(String id)
	{
		List<Phone> phones = Phone.findAll();
		for(Phone p: phones)
		{
			if(p.hub.equals(id))
			{
				return p;
			}
		}
		
		return null;
	}
		
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Hub getHub()
	{
		return hub;
	}

	public void setHub(Hub hub)
	{
		this.hub = hub;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}

	public String getManufacturer()
	{
		return manufacturer;
	}

	public void setManufacturer(String manufacturer)
	{
		this.manufacturer = manufacturer;
	}

	public String getOs()
	{
		return os;
	}

	public void setOs(String os)
	{
		this.os = os;
	}

	public String getOsVer()
	{
		return osVer;
	}

	public void setOsVer(String osVer)
	{
		this.osVer = osVer;
	}
}
