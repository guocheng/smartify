package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
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
	public final static String VERSIONS_FILE_PATH =  "/home/pi/download/versions";
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
	
	// return String[], String[0] = hub version, String[1] = apk version
	public static String[] getVersions() throws Exception
	{
		FileInputStream f = new FileInputStream(VERSIONS_FILE_PATH);
		BufferedReader br = new BufferedReader(new InputStreamReader(f, Charset.forName("UTF-8")));
		
		String line;
		String[] result = new String[2];
		
		while ((line = br.readLine()) != null) 
		{
		    String[] split = line.split(":");
		    if(split[0].trim().toLowerCase().equals("hub"))
		    {
		    	result[0] = split[1].trim().toLowerCase();
		    }
		    else if(split[0].trim().toLowerCase().equals("apk"))
		    {
		    	result[1] =   split[1].trim().toLowerCase();
		    }
		}
		
		br.close();
		br = null;
		f = null;
		
		return result;
	}
	
	
	public static int getHourSegment(int hr)
	{
		
		return hr/2;
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
    
    public static void printNestedMap(Map<String, Map<String, Integer[]>> mp) 
    {
        for(String key:mp.keySet())
        {
        	System.out.print(key + " = ");
        	
        	Map<String, Integer[]> innerMap = mp.get(key);
        	
        	System.out.print("{");
        	for(String k:innerMap.keySet())
        	{
        		System.out.print(k + " = ");
        		
        		Integer[] ints = innerMap.get(k);
        		
        		System.out.print("[");
        		
        		for(int i=0;i<ints.length;i++)
        		{
        			if(i!=ints.length - 1)
        			{
        				System.out.print(ints[i]+",");
        			}
        			else 
        			{
        				System.out.print(ints[i]);
					}
        		}
        		
        		System.out.print("]");
        	}
        	System.out.println("} ");
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
   
    
    /**
     * @param type
     * @return return type <String, Map<String, Integer>> like the following 
     * 			lock = {后门=1, 家门=2} 
     * 			humidifier = {客厅=1, 卧室=2}
     * 			switch = {客厅灯=3, 厕所灯=1}
     */
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
    
    public static Map<String, Integer> getDistributionInt(List list, DistType type)
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
    	
    	return maps;
    }
    
    public static float getFileSize(String filename)  
    {
		File f = new File(filename);
		return f.length();
    }
    
    public static String getChecksum(String filename) throws Exception
    {
    	MessageDigest md = MessageDigest.getInstance("SHA-1");
        FileInputStream fis = new FileInputStream(filename);
 
        byte[] dataBytes = new byte[1024];
 
        int nread = 0; 
        while ((nread = fis.read(dataBytes)) != -1) {
          md.update(dataBytes, 0, nread);
        };
        fis.close();
        byte[] mdbytes = md.digest();
 
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
          sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
