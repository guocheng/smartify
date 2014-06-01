package controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import models.Gadget;
import models.GadgetLog;
import models.Hub;
import models.Phone;

public class Util
{
	public enum DistType
	{
		HubVersion,
		GadgetType,
		PhoneMaker
	}
	
	public enum NestedDistType
	{
		GadgetName,
		GadgetFunc,
		GadgetCommMethod,
		PhoneModel
	}
	
	public static String getIPaddress()
	{
		InetAddress ip;
		try
		{
			ip = InetAddress.getLocalHost();
			return ip.getHostAddress();
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();	
			return null;
		}
	}
	
    public static void printMap(Map mp) 
    {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
    
    public static void fillMap(Map<String, Integer> map, String key)
	{		
		if(!map.containsKey(key))
		{
			map.put(key, new Integer(1));
		}
		else 
		{
			Integer i = map.get(key).intValue() + 1;
			map.put(key, i);
		}	
	}
    
    public static Map<String, Map<String, Integer>> getNestedDist(NestedDistType type)
	{
		List<Gadget> gadgets = null;
		List<Phone> phones = null;
		Iterator itor = null;
		
		if(type == NestedDistType.PhoneModel)
		{
			phones = Phone.findAll();
			itor = phones.iterator();
		}
		else 
		{
			gadgets = Gadget.findAll();
			itor = gadgets.iterator();
		}
		
		Map<String, Map<String, Integer>> results = new HashMap<String, Map<String, Integer>>();
		
		while(itor.hasNext())
		{
			Gadget gadget = null;
    		Phone phone = null;
    		String key = null;
    		
			if(type == NestedDistType.PhoneModel)
			{
				phone = (Phone) itor.next();
				key = phone.getManufacturer();
			}
			else 
			{
				gadget = (Gadget) itor.next();
	    		key = gadget.getType();
			}

    		List<GadgetLog> logs = null;
    		
    		if(type == NestedDistType.GadgetFunc || type == NestedDistType.GadgetCommMethod)
    		{
    			logs = gadget.getGadgetLogList();
    		}

    		if(!results.containsKey(key))
    		{
    			Map<String, Integer> map = new HashMap<String, Integer>();
    			
    			if(type == NestedDistType.GadgetName)
    			{
    				Util.fillMap(map, gadget.getUserDefinedName());
    			}
    			else if(type == NestedDistType.GadgetFunc)
    			{
        			for(GadgetLog log: logs)
        			{
        				Util.fillMap(map, log.getCommandType());
        			}
    			}
    			else if(type == NestedDistType.GadgetCommMethod)
    			{
        			for(GadgetLog log: logs)
        			{
        				Util.fillMap(map, log.getConnectionType() );
        			}
    			}
    			else if(type == NestedDistType.PhoneModel)
    			{
    				Util.fillMap(map, phone.getModel());
    			}
    			
    			results.put(key, map);
    		}
    		else 
    		{
    			Map<String, Integer> map = results.get(key);	
    			
    			if(type == NestedDistType.GadgetName)
    			{
    				Util.fillMap(map, gadget.getUserDefinedName());
    			}
    			else if(type == NestedDistType.GadgetFunc)
    			{
        			for(GadgetLog log: logs)
        			{
        				Util.fillMap(map, log.getCommandType());
        			}
    			}
    			else if(type == NestedDistType.GadgetCommMethod)
    			{
        			for(GadgetLog log: logs)
        			{
        				Util.fillMap(map, log.getConnectionType() );
        			}
    			}
    			else if(type == NestedDistType.PhoneModel)
    			{
    				Util.fillMap(map, phone.getModel());
    			}
			}
		}
		
		return results;
	}
    
    
    
    /**
     * @param list - a list of objects to be counted
     * @param type - either DistType.HubVersion or DistType.GadgetType
     * @return a map contains a list of key:value pair where key = a unique type in the list,
     * value = the percentage of this type within the entire list. For instance, a list of 
     * 10 gadgets are passed in. There are 3 locks, 3 switches and 4 humidifiers. The returned
     * Map will be = {lock:30%, switch:30%, humidifiers:30%}
     */
    public static Map<String, Float[]> getDistribution(List list, DistType type)
    {
    	Iterator itor = list.iterator();
    	Map<String, Integer> maps = new HashMap<String, Integer>();
    	
    	while(itor.hasNext())
    	{
    		String key = null;
    		Object obj = itor.next();
    		
    		switch(type)
    		{
	    		case HubVersion: key = ((Hub)obj).getSoftwareVer(); 	break;
	    		case GadgetType: key = ((Gadget)obj).getType();			break;
	    		case PhoneMaker: key = ((Phone)obj).getManufacturer();	break;
				default: break; 
    		}
    		
    		Util.fillMap(maps, key);
    	}
    	
    	Map<String, Float[]> results = new HashMap<String, Float[]>();
        Iterator<Entry<String, Integer>> it = maps.entrySet().iterator();
        while (it.hasNext()) 
        {
        	Map.Entry<String, Integer> pairs = (Entry<String, Integer>)it.next();
        	Float[] floats = {new Float((float)pairs.getValue().intValue() / (float)list.size()), new Float(pairs.getValue().floatValue())};
        	results.put(pairs.getKey(), floats);
        }
        
        return results;
    }
}
