package org.roi.itlab.cassandra;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;


public class RoutingTestIT {
    private static final String testPois = "./src/test/resources/org/roi/payg/saint-petersburg_russia.csv";
    private static final int TRIPSCOUNT = 10000;
    private List<Poi> pois;
    private Random rng;
    private List<Trip> trips = new ArrayList<>();

    @Before


    public void init() throws IOException {

        pois = PoiLoader.loadFromCsv(testPois);
        rng = new Random(42);


        //random trips
        for (int i = 0; i < TRIPSCOUNT ; i++) {
            int temp = rng.nextInt(30000);
            Poi a = pois.get(temp);
            Poi b = pois.get(temp + rng.nextInt(50));
            trips.add(new Trip(a, b, 0));
        }
    }

    @Test(timeout = 40000)
    public void testRoutingPerformance() {
        int routingFailedCounter=0;
        for (Trip trip :
                trips) {
            try {
                trip.getRoute();
            } catch (IllegalStateException e) {
                routingFailedCounter++;
            }
        }

        System.out.println(routingFailedCounter);
    }
    @Test
    public void testRoute(){
        Route route = Routing.route(59.96226, 30.298873, 59.817727, 30.326528);
        for (Edge e :
                route.getEdges()) {
            System.out.println(e.getStart()+" "+ e.getEnd()+" "+e.getDistance());
        }
        Assert.assertEquals(route.getEdges()[0].getDistance(),5.488,0.0001);
    }
}
