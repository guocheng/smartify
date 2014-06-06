package models;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import controllers.Util;
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
	
	public static Map<String, Map<String, Integer[]>> getFuncDistribution()
	{
		Map<String, List<Gadget>> gadgets = Gadget.getGadgetsByType();
		Map<String, Map<String, Integer[]>> results = new HashMap<String, Map<String, Integer[]>>();
		
		
		for(String gadgetType : gadgets.keySet())
		{
			Map<String, Integer[]> funcType = new HashMap<String, Integer[]>();
			results.put(gadgetType, funcType);
			
			List<Gadget> gadgetList = gadgets.get(gadgetType);
			
			for(Gadget gadget: gadgetList)
			{
				List<GadgetLog> logs = gadget.getGadgetLogList();
				
				for(GadgetLog log: logs)
				{
					String logType = log.getCommandType();
					
					if(!funcType.containsKey(logType))
					{
						Integer[] ints = new Integer[12];
						Arrays.fill(ints, 0);
						Timestamp time = log.getAccessTime();
						SimpleDateFormat format = new SimpleDateFormat("k");	// 1 - 24
						String hour = format.format(time);
						
						int index = (Integer.parseInt(hour) - 1) / 2; 	// convert to 0 - 23
						ints[index] += 1;
							
						funcType.put(logType, ints);
					}
					else 
					{
						Integer[] ints = funcType.get(logType);
						
						Timestamp time = log.getAccessTime();
						SimpleDateFormat format = new SimpleDateFormat("k");	// 1 - 24
						String hour = format.format(time);
						
						int index = (Integer.parseInt(hour) - 1) / 2; 	// convert to 0 - 23
						ints[index] += 1;
						
						funcType.put(logType, ints);
					}
				}
			}
		}
		
		return results;
	}
	// return a list of <gadgetType, list of gadget of this type>
    private static Map<String, List<Gadget>> getGadgetsByType()
    {
    	List<Gadget> gadgets = Gadget.findAll();
    	Map<String, List<Gadget>> results = new HashMap<String, List<Gadget>>();
    	
		Iterator<Gadget> itor = gadgets.iterator();
		
		while(itor.hasNext())
		{
			Gadget gadget = itor.next();
    		String gadgetType = gadget.getType();
    		
    		if(!results.containsKey(gadgetType))
    		{
    			
    			List<Gadget> items = new ArrayList<Gadget>();
    			items.add(gadget);    			
    			results.put(gadgetType, items);
    		}
    		else 
    		{
    			List<Gadget> items = results.get(gadgetType);
    			items.add(gadget);
			}
		}
    	return results;
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
