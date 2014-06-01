package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity
@Table(name="gadget")
public class Gadget extends Model
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private String id;
	
	@Column(name="type")
	private String type;
	
	@Column(name="user_defined_name")
	private String userDefinedName;
	
	@Column(name="availability")
	private String availability;
	

	@ManyToOne
	@JoinColumn(name="hub_id")
	private Hub hub;
	
	@OneToMany(mappedBy="gadget")
	private List<GadgetLog> gadgetLogList;
	
	public List<GadgetLog> getGadgetLogList()
	{
		return gadgetLogList;
	}

	public void setGadgetLogList(List<GadgetLog> gadgetLogList)
	{
		this.gadgetLogList = gadgetLogList;
	}

	public static Finder<String, Gadget> find = new Model.Finder<>(String.class, Gadget.class);
	
	public static List<Gadget> findAll()
	{
		return new ArrayList<Gadget>(Gadget.find.all());
	}
	
	
	
	/*public static Map<String, Map<String, Integer>> getNameDist(TagCloudType type)
	{
		List<Gadget> gadgets = Gadget.findAll();
		Map<String, Map<String, Integer>> results = new HashMap<String, Map<String, Integer>>();
		Iterator<Gadget> itor = gadgets.iterator();
		
		while(itor.hasNext())
		{
    		Gadget gadget = itor.next();
    		String gadgetType = gadget.getType();
    		String userDefinedName = gadget.getUserDefinedName();

    		if(!results.containsKey(gadgetType))
    		{
    			
    			Map<String, Integer> map = new HashMap<String, Integer>();
    			Util.fillMap(map, userDefinedName);
    			results.put(gadgetType, map);
    		}
    		else 
    		{
    			Map<String, Integer> map = results.get(gadgetType);
    			Util.fillMap(map, userDefinedName);
			}
		}
		
		return results;
	}*/
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getUserDefinedName()
	{
		return userDefinedName;
	}

	public void setUserDefinedName(String userDefinedName)
	{
		this.userDefinedName = userDefinedName;
	}

	public String getAvailability()
	{
		return availability;
	}

	public void setAvailability(String availability)
	{
		this.availability = availability;
	}

	public Hub getHub()
	{
		return hub;
	}

	public void setHub(Hub hub)
	{
		this.hub = hub;
	}

}
