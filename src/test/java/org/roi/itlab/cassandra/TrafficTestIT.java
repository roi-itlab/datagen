package org.roi.itlab.cassandra;

import com.graphhopper.util.DistanceCalcEarth;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.roi.itlab.cassandra.person.Person;
import org.roi.itlab.cassandra.random_attributes.PersonGenerator;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

public class TrafficTestIT {
    private static final String target2 = "./target/drivers.txt";
    private static final String IntensityMapSaveFile = "./target/traffic/intensity_map";
    private static final String EdgesStorageSaveFile = "./target/traffic/edges_storage";
    private static final String EdgesStrorageLoadFile = "./src/test/resources/edges_storage";
    private static final String IntenstityMapLoadFile = "./src/test/resources/intensity_map";

    private static final int DRIVERS_COUNT = 100_000;
    static List<Person> drivers;

    @BeforeClass
    public static void init() throws IOException {
        //generating drivers
        PersonGenerator personGenerator = new PersonGenerator();
        drivers = new ArrayList<>(DRIVERS_COUNT);
        for (int i = 0; i < DRIVERS_COUNT; i++) {
            drivers.add(personGenerator.getResult());
        }
    }

    @Test
    public void saveDrivers() throws IOException {
        Path path = FileSystems.getDefault().getPath(target2);
        Files.deleteIfExists(path);
        Files.createFile(path);
        OutputStream out = Files.newOutputStream(path, StandardOpenOption.WRITE);
        OutputStreamWriter writer = new OutputStreamWriter(out, Charset.defaultCharset());

        writer.write("Age,Experience,Skill,RushFactor,WorkStart,WorkDuration,WorkEnd,HomeLat,HomeLng,WorkLat,WorkLng" + '\n');
        for (Person person : drivers) {
            writer.write(person.getAge() + "," + person.getExperience() + "," + String.format("%.3f", person.getSkill()) + "," + String.format("%.3f", person.getRushFactor()) + "," + person.getWorkStart() + "," + person.getWorkDuration() + "," + person.getWorkEnd() + "," + String.format("%.4f", person.getHome().getLatitude()) + "," + String.format("%.4f", person.getHome().getLongitude()) + "," + String.format("%.4f", person.getWork().getLatitude()) + "," + String.format("%.4f", person.getWork().getLongitude()) + '\n');
        }
        writer.close();
    }

    @Ignore
    @Test
    public void IntensityMapSaving() throws IOException {
        IntensityMap traffic = new IntensityMap();
        int routingFailedCounter = 0;

        for (Person driver :
                drivers) {
            try {
                Route routeToWork = Routing.route(driver.getHome(), driver.getWork());
                Route routeFromWork = Routing.route(driver.getWork(), driver.getHome());
                long startTime = driver.getWorkStart().toSecondOfDay() * 1000;
                long endTime = driver.getWorkEnd().toSecondOfDay() * 1000;
                traffic.put(startTime, routeToWork);
                traffic.put(endTime, routeFromWork);
            } catch (IllegalStateException e) {
                routingFailedCounter++;
            }
        }

        System.out.println(routingFailedCounter);

        Path path = FileSystems.getDefault().getPath(IntensityMapSaveFile);
        Files.deleteIfExists(path);
        Files.createFile(path);
        OutputStream out = Files.newOutputStream(path, StandardOpenOption.WRITE);
        OutputStreamWriter writer = new OutputStreamWriter(out, Charset.defaultCharset());

        Path path2 = FileSystems.getDefault().getPath(EdgesStorageSaveFile);
        Files.deleteIfExists(path2);
        Files.createFile(path2);
        OutputStream out2 = Files.newOutputStream(path2, StandardOpenOption.WRITE);
        OutputStreamWriter writer2 = new OutputStreamWriter(out2, Charset.defaultCharset());

        Routing.saveEdgesStorage(writer2);
        traffic.writeToCSV(writer);

    }

    @Test
    public void IntensityMapLoading() throws IOException {
        IntensityMap loadedtraffic = new IntensityMap();
        Routing.loadEdgesStorage(EdgesStrorageLoadFile);
        loadedtraffic.loadFromCSV(IntenstityMapLoadFile);
        Path location = FileSystems.getDefault().getPath("src/test/resources/test.geojson");

        loadedtraffic.makeGeoJSON(new File(location.toUri()));

    }

}
