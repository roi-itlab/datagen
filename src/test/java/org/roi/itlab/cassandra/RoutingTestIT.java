package org.roi.itlab.cassandra;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;


public class RoutingTestIT {
    private static final String testPois = "./src/test/resources/org/roi/payg/saint-petersburg_russia.csv";
    private static final int ROUTSCOUNT = 10000;
    private List<Poi> pois, starts, ends;
    private Random rng;

    @Before
    public void init() throws IOException {

        pois = PoiLoader.loadFromCsv(testPois);
        starts = new ArrayList<>(ROUTSCOUNT);
        ends = new ArrayList<>(ROUTSCOUNT);
        rng = new Random(42);


        for (int i = 0; i < ROUTSCOUNT; i++) {
            int temp = rng.nextInt(30000);
            Poi a = pois.get(temp);
            Poi b = pois.get(temp + rng.nextInt(50));
            starts.add(a);
            ends.add(b);
        }
    }

    @Test(timeout = 40000)
    public void testRoutingPerformance() {
        int routingFailedCounter = 0;
        for (int i = 0; i < ROUTSCOUNT; i++) {

            try {
                Routing.route(starts.get(i), ends.get(i));
            } catch (IllegalStateException e) {
                routingFailedCounter++;
            }
        }

        System.out.println(routingFailedCounter);
    }

    @Test
    public void testRoute() {
        Route route = Routing.route(59.96226, 30.298873, 59.817727, 30.326528);
        System.out.println(route);
        Assert.assertEquals(route.getEdges()[0].getDistance(), 5.488, 0.0001);
    }

    @Test
    public void emptyRouteTest() {
        Route route = Routing.route(59.96226, 30.298873, 59.96226, 30.298873);
        Assert.assertEquals(route.getEdges().length, 0);
    }
}
