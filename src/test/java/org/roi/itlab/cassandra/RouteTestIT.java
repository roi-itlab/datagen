package org.roi.itlab.cassandra;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.graphhopper.util.*;
import org.junit.Test;

import static org.junit.Assert.*;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.PathWrapper;
import com.graphhopper.reader.osm.GraphHopperOSM;
import com.graphhopper.routing.util.EncodingManager;

public class RouteTestIT {

    private static final String testOsm = "./src/PersonExample/resources/org/roi/payg/saint-petersburg_russia.osm.pbf";
	
    @Test
    public void testFeed() {
    	// create one GraphHopper instance
    	GraphHopper hopper = new GraphHopperOSM().setOSMFile(testOsm).forServer();
    	// where to store graphhopper files?
    	hopper.setGraphHopperLocation("./target");
    	hopper.setEncodingManager(new EncodingManager("car,bike"));

    	// now this can take minutes if it imports or a few seconds for loading
    	// of course this is dependent on the area you import
    	hopper.importOrLoad();

    	// simple configuration of the request object, see the GraphHopperServlet classs for more possibilities.
		GHRequest req = new GHRequest(59.96226, 30.298873, 59.817727, 30.326528).
    	    setWeighting("fastest").
    	    setVehicle("car").
    	    setLocale(Locale.US);
		req.getHints().put(Parameters.Routing.ADD_DETAILS_TO_ANNOTATION, true);
		req.getHints().put(Parameters.Routing.SINGLE_NODE_INSTRUCTIONS, true);
    	GHResponse rsp = hopper.route(req);

    	// first check for errors
    	if(rsp.hasErrors()) {
    	   // handle them!
    	   System.out.println(rsp.getErrors());
    	   return;
    	}

    	// use the best path, see the GHResponse class for more possibilities.
    	PathWrapper path = rsp.getBest();
 	   System.out.println(path);

    	// points, distance in meters and time in millis of the full path
    	PointList pointList = path.getPoints();
    	double distance = path.getDistance();
    	long timeInMs = path.getTime();

    	InstructionList il = path.getInstructions();
    	// iterate over every turn instruction
    	for(Instruction instruction : il) {
    	   System.out.println(instruction + " " + instruction.getAnnotation());
    	}

    	// or get the json
    	List<Map<String, Object>> iList = il.createJson();

    	// or get the result as gpx entries:
    	List<GPXEntry> list = il.createGPXList();
    	long duration = 0;
		for (GPXEntry entry : list) {
     	   System.out.println(entry);
     	   duration = entry.getTime();
    	}
    	
		assertEquals(duration, 1443140);
	}
}
