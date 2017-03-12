package org.roi.itlab.cassandra;

import com.graphhopper.json.geo.Point;
import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TrafficCalcTestIT {
    //test data
    private static final int N = 5;
    private static final double[ ][ ] coords = {{59.947553, 30.259824, 59.926765, 30.240469},
                                                {59.94693, 30.274029, 59.942309,30.257936},
                                                {59.929087, 30.24682, 59.948607, 30.22047},
                                                {59.948693, 30.280123, 59.944222, 30.230598},
                                                {59.94203, 30.262828, 59.935107, 30.264115}};
    private static final long[ ] time = {1488186000000L, 1488186180000L, 1488186300000L, 1488186180000L, 1488186180000L};
    private List<Route> routes;
    private List<Long> startTimes;

    @Before

    public void init( ) {
        routes = new ArrayList<>(N);
        startTimes = new ArrayList<>(N);
        for (int i = 0; i < N; ++i) {
            Poi start = new Poi (Integer.toString(i), Integer.toString(i), 0, coords[i][0], coords[i][1]);
            Poi end = new Poi (Integer.toString(i), Integer.toString(i), 0, coords[i][2], coords[i][3]);
            Trip trip = new Trip(start, end, time[i]);
            routes.add(trip.getRoute());
            startTimes.add(trip.getStartTime());
        }
    }

    @Test

    public void TestTrafficIT( ) {
        IntensityMap intensityMap = new IntensityMap(startTimes, routes);
        System.out.println("\n" + intensityMap);

        //TODO: find out, how new routes look like, to use their segments in an assumption
        assertEquals(intensityMap.getIntensity(new Edge(0,
                                                        new Point(59.94395486885208, 30.26372187429045),
                                                        new Point(59.943898058173765, 30.263535050977804),
                                                        null,
                                                        0.0),
                                                1488186250000L),
                    3);
        assertEquals(intensityMap.getIntensity(new Edge(0,
                                                        new Point(59.92686174646456,30.24094749780627),
                                                        new Point(59.92673416926181,30.24050434633028),
                                                        null,
                                                        0.0),
                                                1488186250000L),
                    1);
        Assert.assertNotEquals(intensityMap.getIntensity(new Edge(  0,
                                                                    new Point(59.92686174646456,30.24094749780627),
                                                                    new Point(59.92673416926181,30.24050434633028),
                                                                    null,
                                                                    0.0),
                                                1488185900000L),
                    1);
    }


}
