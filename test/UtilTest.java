import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.Hub;

import org.junit.Test;

import controllers.Util;
import controllers.Util.DistType;


public class UtilTest
{

	@Test
	public void testDistribution()
	{
		List<Hub> hubs = new ArrayList<Hub>();
		
		for(int i=0;i<10;i++)
		{
			Hub hub = new Hub();
			if(i%2 == 0)
			{
				hub.setSoftwareVer("1.0");
			}
			else 
			{
				hub.setSoftwareVer("2.0");
			}
			
			hubs.add(hub);
		}
		
		Map<String, Float[]> results = Util.getDistribution(hubs, DistType.HubVersion);
		assertTrue(results.get("1.0")[0].floatValue() == 0.5);
		assertTrue(results.get("1.0")[1].floatValue() == 5);
		assertTrue(results.get("2.0")[0].floatValue() == 0.5);
		assertTrue(results.get("2.0")[1].floatValue() == 5);
	}
	
	@Test
	public void testHourSegment()
	{
		assertEquals(5, Util.getHourSegment(11));
		
	}

}
