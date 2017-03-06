package org.roi.itlab.cassandra;

import com.graphhopper.GraphHopper;
import com.graphhopper.reader.osm.GraphHopperOSM;
import com.graphhopper.routing.Path;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.EdgeIteratorState;
import com.graphhopper.util.Instruction;
import com.graphhopper.util.InstructionList;
import com.graphhopper.util.TranslationMap;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;


public class TripTestIT {
    private static final String testOsm = "./src/test/resources/org/roi/payg/saint-petersburg_russia.osm.pbf";
    private static final String testPois = "./src/test/resources/org/roi/payg/saint-petersburg_russia.csv";
    private List<Poi> pois;
    private Random rng;
    private List<Trip> trips = new ArrayList<>();
    private List<Path> paths = new ArrayList<>();
    private GraphHopper hopper;

    @Before


    public void init() throws IOException {

        // create one GraphHopper instance
        hopper = new GraphHopperOSM().setOSMFile(testOsm).forServer().setEnableInstructions(false);
        // where to store graphhopper files?
        hopper.setGraphHopperLocation("./target");
        hopper.setEncodingManager(new EncodingManager("car,bike"));

        // now this can take minutes if it imports or a few seconds for loading
        // of course this is dependent on the area you import
        hopper.importOrLoad();
        pois = PoiLoader.loadFromCsv(testPois);
        rng = new Random(42);


        //random trips
        for (int i = 0; i < 10000; i++) {
            int temp = rng.nextInt(30000);
            Poi a = pois.get(temp);
            Poi b = pois.get(temp + rng.nextInt(50));

            trips.add(new Trip(a, b, 0));
        }
    }

    @Test
    public void testFeed() {
        int routingFailedCounter=0;
        //accumulating paths
        for (Trip trip :
                trips) {
            try {
                paths.add(trip.calcPath(hopper));
            } catch (IllegalStateException e) {
                routingFailedCounter++;
            }
        }

        System.out.println(paths.size());
        Assert.assertTrue(">10% unroutable paths", paths.size() > 9000);


        //trying to generate traffic on edges, but not using time.

        Map<Integer, EdgeIteratorState> edges = new HashMap<>();
        Map<Integer, Integer> frequencies = new HashMap<>();
        for (Path path :
                paths) {
            //InstructionList il = path.calcInstructions(new TranslationMap.TranslationHashMap(Locale.ENGLISH));
            for (EdgeIteratorState edge :
                    path.calcEdges()) {
                int id = edge.getEdge();
                edges.putIfAbsent(id, edge);
                frequencies.putIfAbsent(id, 1);
                frequencies.computeIfPresent(id, (a, b) -> b + 1);
            }
        }

        System.out.println(edges.size());
    }
}
