package org.roi.itlab.cassandra;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonObject;
import org.junit.Ignore;
import org.junit.Test;
import org.roi.itlab.cassandra.person.Person;
import org.roi.itlab.cassandra.random_attributes.PersonGenerator;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class TrafficTestIT {
    private static final String INTENSITY_FILENAME = "./target/intensity_map";
    private static final String EDGES_FILENAME = "./target/edges_storage";
    private static final String GEO_JSON_FILENAME = "./target/geo.json";

    private static final String EdgesStrorageLoadFile = "./src/test/resources/edges_storage";
    private static final String IntenstityMapLoadFile = "./src/test/resources/intensity_map";

    private static final int DRIVERS_COUNT = 100_000;

    @Ignore
    @Test
    public void IntensityMapSaving() throws IOException {
        PersonGenerator personGenerator = new PersonGenerator();
        ArrayList<Person> drivers = new ArrayList<>(DRIVERS_COUNT);
        for (int i = 0; i < DRIVERS_COUNT; i++) {
            drivers.add(personGenerator.getResult());
            if (i % 10000 == 0)
                System.out.println(i + " drivers");
        }
        IntensityMap traffic = new IntensityMap(drivers);

        System.out.println("Max intensity: " + traffic.getMaxIntensity());

        Path path = FileSystems.getDefault().getPath(INTENSITY_FILENAME);
        Files.deleteIfExists(path);
        Files.createFile(path);
        OutputStream out = Files.newOutputStream(path, StandardOpenOption.WRITE);
        OutputStreamWriter writer = new OutputStreamWriter(out, Charset.defaultCharset());

        Path path2 = FileSystems.getDefault().getPath(EDGES_FILENAME);
        Files.deleteIfExists(path2);
        Files.createFile(path2);
        OutputStream out2 = Files.newOutputStream(path2, StandardOpenOption.WRITE);
        OutputStreamWriter writer2 = new OutputStreamWriter(out2, Charset.defaultCharset());

        Routing.saveEdgesStorage(writer2);
        traffic.writeToCSV(writer);
    }

    @Test
    public void IntensityMapLoading() throws IOException {
        IntensityMap traffic = new IntensityMap();
        Routing.loadEdgesStorage(EdgesStrorageLoadFile);
        traffic.loadFromCSV(IntenstityMapLoadFile);
        Path location = FileSystems.getDefault().getPath(GEO_JSON_FILENAME);

        traffic.makeGeoJSON(new File(location.toUri()));
        GeoJsonObject object = new ObjectMapper().readValue(new FileInputStream(new File(location.toUri())), GeoJsonObject.class);
        assertTrue(object instanceof FeatureCollection);
    }
}
