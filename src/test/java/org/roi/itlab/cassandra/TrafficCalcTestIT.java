package org.roi.itlab.cassandra;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.PathWrapper;
import com.graphhopper.reader.osm.GraphHopperOSM;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.Parameters;
import com.graphhopper.util.shapes.GHPoint;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class TrafficCalcTestIT {
    //test data
    private static final String testOsm = "./src/test/resources/org/roi/payg/saint-petersburg_russia.osm.pbf";
    private static final int N = 5;
    private static final double[ ][ ] coords = {{59.947553, 30.259824, 59.926765, 30.240469},
            {59.94693, 30.274029, 59.942309,30.257936},
            {59.929087, 30.24682, 59.948607, 30.22047},
            {59.948693, 30.280123, 59.944222, 30.230598},
            {59.94203, 30.262828, 59.935107, 30.264115}};
    private static final long[ ] time = {1488186000000L, 1488186180000L, 1488186300000L, 1488186180000L, 1488186180000L};

    private List<Long> timeList = new ArrayList<>(5);
    private List<PathWrapper> pathList = new ArrayList<>(5);

    @Test
    public void TestTrafficIT( ) {
        GraphHopper hopper = new GraphHopperOSM().setOSMFile(testOsm).forServer();
        hopper.setGraphHopperLocation("./target");
        hopper.setEncodingManager(new EncodingManager("car,bike"));
        hopper.importOrLoad();
        for (int i = 0; i < N; ++i) {
            GHRequest req = new GHRequest(coords[i][0], coords[i][1], coords[i][2], coords[i][3]).
                    setWeighting("fastest").
                    setVehicle("car").
                    setLocale(Locale.US);
            req.getHints().put(Parameters.Routing.ADD_DETAILS_TO_ANNOTATION, true);
            req.getHints().put(Parameters.Routing.SINGLE_NODE_INSTRUCTIONS, true);
            GHResponse rsp = hopper.route(req);
            if(rsp.hasErrors()) {
                System.out.println(rsp.getErrors());
                return;
            }
            PathWrapper path = rsp.getBest();
            timeList.add(time[i]);
            pathList.add(path);
        }
        MapOfIntensity intensityMap1 = new MapOfIntensity(timeList, pathList);
        System.out.println("\n" + intensityMap1);

        assertEquals(intensityMap1.getIntensity(new GHPoint(59.94395486885208, 30.26372187429045), new GHPoint(59.943898058173765, 30.263535050977804), 1488186250000L), 3);
        assertEquals(intensityMap1.getIntensity(new GHPoint(59.92686174646456,30.24094749780627), new GHPoint(59.92673416926181,30.24050434633028), 1488186250000L), 1);
        //Assert.assertNotEquals(intensityMap1.getIntensity(new GHPoint(59.92686174646456,30.24094749780627), new GHPoint(59.92673416926181,30.24050434633028), 1488185900000L), 1);
        //assertEquals(intensityMap1.getIntensity(new GHPoint(59.94215853383001,30.231562001218542), new GHPoint(59.942312388322776,30.231492524552923), 1488186500000L), 2);
    }


}
