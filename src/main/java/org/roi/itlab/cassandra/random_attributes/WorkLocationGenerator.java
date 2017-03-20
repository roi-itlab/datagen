package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.random.MersenneTwister;
import org.mongodb.morphia.geo.GeoJson;
import org.mongodb.morphia.geo.Point;
import org.roi.itlab.cassandra.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vadim on 20.03.2017.
 */
public class WorkLocationGenerator {
    List<Location> locationList;
    LocationGenerator generator;
    org.apache.commons.math3.random.RandomGenerator rng;

    public WorkLocationGenerator()
    {
        locationList = new ArrayList<Location>();
        rng =   new MersenneTwister(1);
        load();
        generator = new LocationGenerator(this.rng, this.locationList);
    }

    public WorkLocationGenerator(org.apache.commons.math3.random.RandomGenerator rng)
    {
        locationList = new ArrayList<Location>();
        this.rng =   rng;
        load();
        generator = new LocationGenerator(this.rng, this.locationList);
    }

    private void load()
    {
        locationList.add(new Location(rng, GeoJson.point(59.94, 30.26), 2500, 6));
        locationList.add(new Location(rng, GeoJson.point(59.93, 30.32), 2000, 4));
        locationList.add(new Location(rng, GeoJson.point(59.91, 30.25), 1500, 5));
        locationList.add(new Location(rng, GeoJson.point(59.82, 30.32), 1000, 4));
        locationList.add(new Location(rng, GeoJson.point(60.01, 30.343), 1500, 1));
        locationList.add(new Location(rng, GeoJson.point(59.87, 30.45), 2000, 3));
        locationList.add(new Location(rng, GeoJson.point(59.96, 30.28), 1500, 2));
    }

    public Point sample()
    {
        return generator.sample();
    }
}
