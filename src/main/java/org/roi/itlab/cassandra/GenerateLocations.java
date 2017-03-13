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

    private GenerateLocations() {}

    private static String filename = "./src/test/resources/org/roi/payg/saint-petersburg_russia.csv";
    private static int metersPerLatDeg = 111135;
    private static int metersPerLngDeg = 55660;

    //returns list of all the pois with assigned types from the source list
    public static List<Poi> getPoisOfTypes (List<Poi> sourceList, int... types){
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
