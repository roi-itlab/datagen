package org.roi.itlab.cassandra;

import com.graphhopper.PathWrapper;
import com.graphhopper.json.geo.Point;
import com.graphhopper.util.GPXEntry;
import com.graphhopper.util.shapes.GHPoint;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.hash;

class IntensityMap {

    /*private class Segment {
        private final GHPoint start;
        private final GHPoint finish;


        Segment(GHPoint start, GHPoint finish) {
            this.start = new GHPoint(start.getLat(), start.getLon());
            this.finish = new GHPoint(finish.getLat(), finish.getLon());
        }

        @Override
        public String toString( ) {
            return start.toString() + ' ' + finish.toString( );
        }

        @Override
        public boolean equals(Object ob) {
            if (ob == null) {
                return false;
            }
            if (ob.getClass() != this.getClass()) {
                return false;
            }
            if (start.equals(((Segment)ob).start) && finish.equals(((Segment)ob).finish)) {
                return true;
            }
            return false;
        }

        @Override
        public int hashCode( ) {
            int hash = 7;
            hash = 83 * hash + (int) (Double.doubleToLongBits(start.getLat() / 0.0001) ^
                    (Double.doubleToLongBits(start.getLat() / 0.0001) >>> 32));
            hash = 83 * hash +
                    (int) (Double.doubleToLongBits(start.getLon() / 0.0001) ^
                            (Double.doubleToLongBits(start.getLon() / 0.0001) >>> 32));
            hash = 83 * hash +
                    (int) (Double.doubleToLongBits(finish.getLat() / 0.0001) ^
                            (Double.doubleToLongBits(finish.getLat() / 0.0001) >>> 32));
            hash = 83 * hash +
                    (int) (Double.doubleToLongBits(finish.getLon() / 0.0001) ^
                            (Double.doubleToLongBits(finish.getLon() / 0.0001) >>> 32));
            return hash;
        }
    }*/

    private class Timetable {
        static final int SIZE = 288;
        private int[] timetable;

        Timetable( ) {
            timetable = new int[SIZE];
        }

        int getIntensity(long time) {
            return timetable[getMinutes(time) / 5];
        }

        int getIntensityByNumber(int n) {
            return timetable[n];
        }

        void intensify(long time) {
            ++timetable[getMinutes(time) / 5];
        }
    }

    private Map<Edge, Timetable> map;

    IntensityMap(List<Long> timeList, List<Route> routes) {

        if (timeList.size( ) != routes.size( )) {
            throw new IllegalArgumentException("Sizes of the lists must be equal!");
        }

        map = new HashMap<>( );

        for (int i = 0; i < routes.size(); ++i) {
            for (int j = 0; j < routes.get(i).getSize(); ++j) {
                //checking if there has already been a movement on a given segment
                if (map.containsKey(routes.get(i).getEdges()[j])) {
                    map.get(routes.get(i).getEdges()[j]).intensify(routes.get(i).getTiming()[j] + timeList.get(i));
                }
                else {
                    map.put(routes.get(i).getEdges()[j], new Timetable());
                    map.get(routes.get(i).getEdges()[j]).intensify(routes.get(i).getTiming()[j] + timeList.get(i));
                }
            }
        }
    }

    //translates the miliseconds from 1970-01-01 @ 00:00:00 to minutes from the beginning of the current day
    private int getMinutes(long time) {
        return (int)(time/1000/60 - time/1000/60/60/24 * 60 * 24);
    }

    @Override
    public String toString( ) {
        StringBuilder builder = new StringBuilder( );
        int hour;
        int minute;
        for (Edge e : map.keySet()) {
            builder.append(e.toString() + ":\n");
            for (int i = 0; i < Timetable.SIZE; ++i) {
                if (map.get(e).getIntensityByNumber(i) != 0) {
                    hour = i * 5 / 60;
                    minute = i * 5 % 60;
                    builder.append(hour + ":" + minute + " " + map.get(e).getIntensityByNumber(i) + "\n");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    int getIntensity(Edge edge, long time) {
        if (map.containsKey(edge)) {
          return map.get(edge).getIntensity(time);
        }
        return 0;
    }
}
