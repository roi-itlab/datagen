package org.roi.itlab.cassandra;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.mongodb.morphia.geo.GeoJson;
import org.mongodb.morphia.geo.Point;


/**
 * Created by Игорь on 05.03.2017.
 */

public class GenerateLocations {

    public GenerateLocations() {
    }

    public class Location {

        private double radius;
        private Point center;
        private double weight = 0;

        public Location(Point center, double radius) {
            this.center = center;
            this.radius = radius;
        }

        public double getRadius() {
            return radius;
        }

        public Point getCenter() {
            return center;
        }

        public double getWeight() {
            return weight;
        }

        public Point getPoint() {
            Random r = new Random(System.nanoTime());
            int metersPerLatDeg = 111135;
            int metersPerLngDeg = 55660;

            double distance = r.nextDouble() * radius;
            double angle = r.nextDouble() * 2 * Math.PI;
            double dlat = distance * Math.cos(angle) / metersPerLatDeg;
            double dlng = distance * Math.sin(angle) / metersPerLngDeg;
            double lat = center.getLatitude() + dlat;
            double lng = center.getLongitude() + dlng;
            return GeoJson.point(lat, lng);
        }

    }

    //returns list of all the pois with assigned types from the source list
    public static List<Poi> getPoisOfTypes(List<Poi> sourceList, int... types) {
        List<Poi> resultList = new ArrayList<>();
        for (int type : types) {
            for (Poi poi : sourceList) {
                if (poi.getType() == type) {
                    resultList.add(poi);
                }
            }
        }
        return resultList;
    }

    //returns list of locations with set centers and radius
    public List<Location> poisToLocations(List<Poi> pois, double locationRadius){
        List<Location> locList = new ArrayList<>();
        for (Poi poi : pois) {
            locList.add(new Location(poi.getLoc(), locationRadius));
        }
        return locList;
    }

    //adds weights to locations in locList
    public void add(List<Location> locList, double weight) {
        for (Location loc : locList) {
            loc.weight = weight;
        }
    }

    //normalizes weights in locList (makes their sum equal to 1)
    public void normalize(List<Location> locList){
        double coef = 0;
        for (Location loc : locList){
            coef += loc.weight;
        }
        if (coef == 0)
            return;
        if (coef != 1) {
            for (Location loc : locList){
                loc.weight /= coef;
            }
        }
    }

    //schools: 21
    //offices: 46, 33, 85, 31.. maybe more

    //returns list of points generated in certain locations
    public List<Point> getRandomPoints(List<Location> locList, int numberOfLocations) {

        Random r = new Random(System.nanoTime());

        normalize(locList);

        List<Point> pointList = new ArrayList<>();

        for (int i = 0; i < numberOfLocations; i++) {
            double k = r.nextDouble();
            int j = -1;
            while (k >= 0) {
                j++;
                k -= locList.get(j).getWeight();
            }
            pointList.add(locList.get(j).getPoint());
        }

        return pointList;

    }

    public List<Point> generate() throws IOException {

        double radius = 1000;
        double weight = 0.1;
        int totalLocations = 10;

        String filename = "./src/test/resources/org/roi/payg/saint-petersburg_russia.csv";
        List<Poi> spbList = PoiLoader.loadFromCsv(filename);
        List<Poi> objects = GenerateLocations.getPoisOfTypes(spbList, 21);

        List<Location> locList = poisToLocations(objects, radius);
        add(locList, weight);

        return getRandomPoints(locList, totalLocations);

    }

    //returns list of random points generated nearby pois from the source list
    public static List<Point> generateRandomLocations(int numberOfLocations, List<Poi> sourceList, double radius) {
        Random r = new Random(System.currentTimeMillis());
        List<Point> resultList = new ArrayList<>();
        for (int i = 0; i < numberOfLocations; i++){
            int center = r.nextInt(sourceList.size());
            double distance = r.nextDouble()*radius;
            double angle = r.nextDouble()*2*Math.PI;
            double dlat = distance*Math.cos(angle)/metersPerLatDeg;
            double dlng = distance*Math.sin(angle)/metersPerLngDeg;
            double lat = sourceList.get(center).getLoc().getLatitude() + dlat;
            double lng = sourceList.get(center).getLoc().getLongitude() + dlng;
            resultList.add(GeoJson.point(lat, lng));
        }
        return resultList;
    }
}
