package org.roi.itlab.cassandra;

import org.apache.commons.math3.random.RandomGenerator;
import org.roi.itlab.cassandra.person.Person;
import org.roi.itlab.cassandra.random_attributes.IntensityNormalGenerator;
import org.roi.itlab.cassandra.random_attributes.NormalGenerator;
import org.roi.itlab.cassandra.random_attributes.PersonGenerator;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

/**
 * Created by Mikhail Kuperman on 28.03.2017.
 */
public class City {

    private static final String INTENSITY_FILENAME = "./target/intensity_map";
    private static final String EDGES_FILENAME = "./target/edges_storage";
    private static final String DRIVERS_FILENAME = "./target/drivers.csv";
    private static final int PREVIOUS_YEARS = 5;
    private AccidentRate accidentRate;
    private ArrayList<Person> drivers;

    public City(int size, RandomGenerator rng) throws IOException {
        PersonGenerator personGenerator = new PersonGenerator(rng);
        drivers = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            drivers.add(personGenerator.getResult());
        }

        IntensityMap intensityMap = new IntensityMap();
        Routing.loadEdgesStorage(EDGES_FILENAME);
        intensityMap.loadFromCSV(INTENSITY_FILENAME);

        int maxIntensity = intensityMap.getMaxIntensity();
        accidentRate = new AccidentRate(intensityMap, new IntensityNormalGenerator(maxIntensity, rng), rng);
    }

    public void simulate() {
        for (Person person : drivers) {
            int previousAccidents = accidentRate.getAccidents(person, PREVIOUS_YEARS * 365);
            int accidents = accidentRate.getAccidents(person, 365);
            person.setAccidents(accidents);
            person.setPreviousAccidents(previousAccidents);
        }
    }

    public void save() throws IOException {
        Path path = FileSystems.getDefault().getPath(DRIVERS_FILENAME);
        Files.deleteIfExists(path);
        Files.createFile(path);
        OutputStream out = Files.newOutputStream(path, StandardOpenOption.WRITE);
        OutputStreamWriter writer = new OutputStreamWriter(out, Charset.defaultCharset());

        writer.write("Age,Experience,Skill,RushFactor,WorkStart,WorkDuration,WorkEnd,HomeLat,HomeLng,WorkLat,WorkLng,PreviousAccidents,Accidents" + '\n');
        for (Person person : drivers) {
            writer.write(person.getAge() + "," + person.getExperience() + "," + String.format("%.3f", person.getSkill()) + "," + String.format("%.3f", person.getRushFactor()) + "," + person.getWorkStart().getHour() + "," + person.getWorkDuration().getHour() + "," + person.getWorkEnd().getHour() + "," + String.format("%.4f", person.getHome().getLatitude()) + "," + String.format("%.4f", person.getHome().getLongitude()) + "," + String.format("%.4f", person.getWork().getLatitude()) + "," + String.format("%.4f", person.getWork().getLongitude()) + "," + person.getPreviousAccidents() + "," + person.getAccidents() + '\n');
        }
        writer.close();
    }
}
