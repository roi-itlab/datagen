package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.random.*;
import org.mongodb.morphia.geo.GeoJson;
import org.roi.itlab.cassandra.Location;
import org.mongodb.morphia.geo.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vadim on 19.03.2017.
 */
public class LocationGenerate {
    //TODO  refactor list<Local> out

    List<Location> locationList;
    LocationGenerator generator;
    org.apache.commons.math3.random.RandomGenerator rng;

    public LocationGenerate()
    {
        locationList = new ArrayList<Location>();
        rng =   new MersenneTwister(1);
        load();
        generator = new LocationGenerator(this.rng, this.locationList);
    }

    private void load()
    {
        locationList.add(new Location(rng, GeoJson.point(59.95, 30.49), 2000, 6));
        locationList.add(new Location(rng, GeoJson.point(59.91, 30.48), 2000, 6));
        locationList.add(new Location(rng, GeoJson.point(59.87, 30.37), 2000, 5));
        locationList.add(new Location(rng, GeoJson.point(60.00, 30.27), 500, 4));
        locationList.add(new Location(rng, GeoJson.point(60.05, 30.36), 1500, 7));
        locationList.add(new Location(rng, GeoJson.point(59.56, 30.30), 2000, 2));
        locationList.add(new Location(rng, GeoJson.point(59.93, 30.31), 1500, 2));
    }

    public Point sample()
    {
        return generator.sample();
    }
}
