package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name="hub")
public class Hub extends Model
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum ListType
	{
		Phone,
		Gadget,
		HubLog
	}
	
	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="hardware_ver")
	private String hardwareVer;
	
	@Column(name="firmware_ver")
	private String firmwareVer;
	
	@Column(name="software_ver")
	private String softwareVer;
	
	@OneToMany(mappedBy="hub")
	private List<Phone> phoneList;
	
	@OneToMany(mappedBy="hub")
	private List<Gadget> gadgetList;
	
	@OneToMany(mappedBy="hub")
	private List<HubLog> hubLogList;

	public static Finder<String, Hub> find = new Model.Finder<>(String.class, Hub.class);
	
	public static List<Hub> findAll()
	{
		return new ArrayList<Hub>(Hub.find.all());
	}
	
	public List<HubLog> getHubLogList()
	{
		return hubLogList;
	}

	public void setHubLogList(List<HubLog> hubLogList)
	{
		this.hubLogList = hubLogList;
	}

	public static String getAvg(ListType type)
	{
		List<Hub> hubs = Hub.findAll();
		float result=0;
		if(type == ListType.Phone)
		{
			result = (float)Phone.findAll().size() / hubs.size();
		}
		else if(type == ListType.Gadget)
		{
			result =  (float)Gadget.findAll().size() / hubs.size();
		}
		else if(type == ListType.HubLog)
		{
			result =  (float)HubLog.findAll().size() / hubs.size();
		}
		
		return String.format("%.1f", result);
	}
	
	public List<Gadget> getGadgetList()
	{
		return gadgetList;
	}

	public void setGadgetList(List<Gadget> gadgetList)
	{
		this.gadgetList = gadgetList;
	}
	
	public List<Phone> getPhoneList()
	{
		return phoneList;
	}

	public void setPhoneList(List<Phone> phoneList)
	{
		this.phoneList = phoneList;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getHardwareVer()
	{
		return hardwareVer;
	}

	public void setHardwareVer(String hardwareVer)
	{
		this.hardwareVer = hardwareVer;
	}

	public String getFirmwareVer()
	{
		return firmwareVer;
	}

	public void setFirmwareVer(String firmwareVer)
	{
		this.firmwareVer = firmwareVer;
	}

	public String getSoftwareVer()
	{
		return softwareVer;
	}

	public void setSoftwareVer(String softwareVer)
	{
		this.softwareVer = softwareVer;
	}
}
