package org.roi.itlab.cassandra;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphhopper.util.DistanceCalc;
import com.graphhopper.util.DistanceCalcEarth;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.util.Pair;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mongodb.morphia.geo.Point;
import org.roi.itlab.cassandra.person.Person;
import org.roi.itlab.cassandra.person.PersonBuilder;
import org.roi.itlab.cassandra.person.PersonBuilderImpl;
import org.roi.itlab.cassandra.person.PersonDirector;
import org.roi.itlab.cassandra.random_attributes.LocationGenerator;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static org.junit.Assert.assertTrue;


public class GeoJSONGenerationTestIT {

    //test data, copied from TrafficCalcTestIT

    private static final DistanceCalc DIST_EARTH = new DistanceCalcEarth();
    private static final String testPois = "./src/test/resources/org/roi/payg/saint-petersburg_russia.csv";
    private static final String officePois = "./src/test/resources/org/roi/payg/saint-petersburg_russia_office.csv";
    private static final String target = "./target/intensity.txt";

    private static final int ROUTES_COUNT = 100;
    static List<Route> routesToWork = new ArrayList<>(ROUTES_COUNT);
    static List<Route> routesFromWork = new ArrayList<>(ROUTES_COUNT);
    static List<Person> drivers;

    @BeforeClass
    public static void init() throws IOException {
        List<Poi> pois = PoiLoader.loadFromCsv(testPois);
        List<Poi> offices = PoiLoader.loadFromCsv(officePois);
        List<Point> homelocations = new LocationGenerator(new MersenneTwister(1), pois, new Pair<Integer, Double>(20, 200.0)).sample(ROUTES_COUNT);
        List<Point> worklocations = new LocationGenerator(new MersenneTwister(1), offices, new Pair<Integer, Double>(1, 200.0)).sample(ROUTES_COUNT);

        //generating locations and routes;
        int routingFailedCounter = 0;
        for (Point home :
                homelocations) {
            Predicate<Point> notTooFarFromHome = work -> DIST_EARTH.calcDist(home.getLatitude(), home.getLongitude(),
                    work.getLatitude(), work.getLongitude()) < 15_000;
            Predicate<Point> notTooCloseToHome = work -> DIST_EARTH.calcDist(home.getLatitude(), home.getLongitude(),
                    work.getLatitude(), work.getLongitude()) > 2_000;
            Point work = worklocations.stream().
                    filter(notTooFarFromHome).
                    filter(notTooCloseToHome).
                    findAny().orElse(home);

            try {
                Route routeToWork = Routing.route(home.getLatitude(), home.getLongitude(), work.getLatitude(), work.getLongitude());
                Route routeFromWork = Routing.route(work.getLatitude(), work.getLongitude(), home.getLatitude(), home.getLongitude());
                routesToWork.add(routeToWork);
                routesFromWork.add(routeFromWork);
            } catch (IllegalStateException e) {
                routingFailedCounter++;
            }
        }

        //generating drivers
        PersonDirector personDirector = new PersonDirector();
        PersonBuilder personBuilderImpl = new PersonBuilderImpl();
        personDirector.setPersonBuilder(personBuilderImpl);
        drivers = new ArrayList<>(routesFromWork.size());
        for (int i = 0; i < routesFromWork.size(); i++) {
            personDirector.constructPerson(i);
            drivers.add(personDirector.getPerson());
        }
        System.out.println(routingFailedCounter);
    }


    @Test
    public void geoJSONCreating() throws IOException {
        //filling intensity map
        IntensityMap traffic = new IntensityMap();
        for (int i = 0; i < routesFromWork.size(); i++) {
            long startTime = drivers.get(i).getWorkStart().toSecondOfDay() * 1000;
            long endTime = drivers.get(i).getWorkEnd().toSecondOfDay() * 1000;
            traffic.put(startTime, routesToWork.get(i));
            traffic.put(endTime, routesFromWork.get(i));
        }

        Path path = FileSystems.getDefault().getPath(target);
        Files.deleteIfExists(path);
        Files.createFile(path);
        OutputStream out = Files.newOutputStream(path, StandardOpenOption.WRITE);
        //OutputStreamWriter writer = new OutputStreamWriter(out, Charset.defaultCharset());

        // lines of:{intensity} {length in meters of road segments with this intensity}
        // for every 5 minutes separated with "_\n"
        long time = 0;
        for (int i = 0; i < 288; i++) {
            Map<Integer, Double> intensityDistance = new HashMap<>();
            Map<Edge, Integer> intensity = traffic.getIntensity(time);
            time += 300000L;
            for (Map.Entry<Edge, Integer> entry :
                    intensity.entrySet()) {
                intensityDistance.putIfAbsent(entry.getValue(), 0.0);
                intensityDistance.computeIfPresent(entry.getValue(), (key, sum) -> sum + entry.getKey().getDistance());
            }
            //System.out.println(time);
            //System.out.println(intensityDistance);
            for (Map.Entry<Integer, Double> e :
                    intensityDistance.entrySet()) {
                //writer.write(e.getKey() + " " + e.getValue() + '\n');
            }
            //writer.write("_\n");
        }
        //writer.close();


        Path location = FileSystems.getDefault().getPath("src/test/resources/test.geojson");

        File file = new File(location.toUri());
        file.createNewFile();


        traffic.makeGeoJSON(new File(location.toUri()));
        GeoJsonObject object = new ObjectMapper().readValue(new FileInputStream(new File(location.toUri())), GeoJsonObject.class);

        assertTrue(object instanceof FeatureCollection);

    }

}
