package org.roi.itlab.cassandra;

import org.junit.Before;
import org.junit.Test;
import org.roi.itlab.cassandra.person.Person;

import java.io.IOException;

import static org.junit.Assert.assertNotEquals;
import static org.roi.itlab.cassandra.TrafficTestIT.drivers;

/**
 * Created by Аня on 22.03.2017.
 */
public class AccidentRateIT {

    private IntensityMap intensityMap;
    private Person person;
    private Route routeToWork, routeToHome;
    private AccidentRate accidentRate;

    @Before
    public void setUp() throws IOException {
        //IntensityMap filing
        intensityMap = new IntensityMap();
        TrafficTestIT.init();
        for (int i = 0; i < TrafficTestIT.routesFromWork.size(); i++) {
            long startTime = drivers.get(i).getWorkStart().toSecondOfDay() * 1000;
            long endTime = drivers.get(i).getWorkEnd().toSecondOfDay() * 1000;
            intensityMap.put(startTime, TrafficTestIT.routesToWork.get(i));
            intensityMap.put(endTime, TrafficTestIT.routesFromWork.get(i));
        }

        person = TrafficTestIT.drivers.get(19);
        routeToWork = TrafficTestIT.routesToWork.get(19);
        routeToHome = TrafficTestIT.routesFromWork.get(19);

        accidentRate = new AccidentRate(intensityMap, person, routeToHome,routeToWork);
    }

    @Test
    public void accidentRateTest() {
        accidentRate.setAccidentRate(1500L, 100);
        assertNotEquals(0, person.getAccidentRate());
        System.out.println("Accident rate: " + person.getAccidentRate());
    }
}
