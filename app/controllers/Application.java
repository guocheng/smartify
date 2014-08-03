package controllers;

import java.io.File;

import models.Gadget;
import models.Hub;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.gadget;
import views.html.hub;
import views.html.download;

import com.fasterxml.jackson.databind.node.ObjectNode;

import controllers.Util.DistType;
import controllers.Util.NestedDistType;

public class Application extends Controller
{
	public final static String HUB_DOWNLOAD_URL =  "/home/pi/download/hub";
	public final static String APK_DOWNLOAD_URL = "/home/pi/download/apk";
	public final static String HTTP ="http://";
	public final static String PORT = "9000";
    public final static String IP = "112.126.73.140";

    public static Result index() {
        return redirect(routes.Application.hub());
    }

    //TODO: need to read version number from a xml file
    public static Result getVersion() throws Exception
    {
    	ObjectNode result = Json.newObject();
    	
    	String[] versions = Util.getVersions();
    	result.put("android", versions[1]);
    	result.put("hub", versions[0]);

        result.put("android_url", HTTP + IP + "/download/apk");
        result.put("hub_url", HTTP + IP + "/download/hub");

        File folder = new File(HUB_DOWNLOAD_URL);
        String filename = folder.listFiles()[0].getAbsolutePath();
        result.put("hub_file_checksum", Util.getChecksum(filename));
        
        return ok(result);
    }



    public static Result hub()
    {
    	System.out.println("Average phone: " + Hub.getAvg(Hub.ListType.Phone));
    	System.out.println("Average gadget: " + Hub.getAvg(Hub.ListType.Gadget));
    	System.out.println("Average log: " + Hub.getAvg(Hub.ListType.HubLog));
    	System.out.println("Total # of hubs: " + Hub.findAll().size());

    	System.out.println("** Phone Distribution:");
    	Util.printMap(Util.getNestedDist(NestedDistType.PhoneModel));

    	return ok(hub.render(Util.getDistributionInt(Hub.findAll(), DistType.HubVersion),
    			Util.getNestedDist(NestedDistType.PhoneModel),
    			Hub.getAvg(Hub.ListType.Phone),
    			Hub.getAvg(Hub.ListType.Gadget),
    			Hub.findAll().size(),
                Gadget.findAll().size()
    			));
    	//return ok(Json.toJson(hubs));
    }


    public static Result gadget()
    {
//    	System.out.println("Total # of gadgets: " + Gadget.findAll().size());
//    	System.out.println("** Name Distribution:");
//    	Util.printMap(Util.getNestedDist(NestedDistType.GadgetName));
//    	System.out.println("** Function Distribution:");
//    	Util.printMap(Util.getNestedDist(NestedDistType.GadgetFunc));
//    	System.out.println("** Connection Distribution:");
//    	Util.printMap(Util.getNestedDist(NestedDistType.GadgetCommMethod));
//    	System.out.println("** Function Time Distribution");
//    	Util.printNestedMap(Gadget.getFuncDistribution());


    	return ok(gadget.render(Util.getDistributionInt(Gadget.findAll(), DistType.GadgetType),
    			Util.getNestedDist(NestedDistType.GadgetName),
    			Util.getNestedDist(NestedDistType.GadgetFunc),
    			Util.getNestedDist(NestedDistType.GadgetCommMethod),
    			Gadget.getFuncDistribution()
    			));
    }

    public static Result getHubUpdateFile(String type)
    {
    	File folder = null;
    	if(type.contains("hub"))
    	{
    		folder = new File(HUB_DOWNLOAD_URL);
    	}
    	else if(type.contains("apk"))
    	{
            String userAgent = request().getHeader("User-Agent");
            if(userAgent.contains("MicroMessenger"))
            {
            	return ok(download.render());
            }
            
            folder = new File(APK_DOWNLOAD_URL);
    	}

    	File[] listOfFiles = folder.listFiles();
    	if(listOfFiles.length == 1)
    	{
    		response().setContentType("application/zip");
    		// content-disposition can be omitted as Play will set the correct file type & name when sending
    		// a file object back as response
    		//response().setHeader("Content-disposition","attachment; filename="+listOfFiles[0].getName());
    		return ok(listOfFiles[0]);
    		//return ok();
    	}
    	else
    	{
			return internalServerError();
		}
    }
}
