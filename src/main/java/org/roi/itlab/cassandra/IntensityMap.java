package org.roi.itlab.cassandra;

import com.graphhopper.PathWrapper;
import com.graphhopper.util.GPXEntry;
import com.graphhopper.util.shapes.GHPoint;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.hash;

class IntensityMap {

    private class Segment {
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
    }

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

    private Map<Segment, Timetable> map;

    IntensityMap(List<Long> timeList, List<PathWrapper> pathList) {

        if (timeList.size( ) != pathList.size( )) {
            throw new IllegalArgumentException( );
        }

        map = new HashMap<>( );

        for (int i = 0; i < pathList.size(); ++i) {
            //receiving a list of gpx
            List<GPXEntry> gpxList = pathList.get(i).getInstructions().createGPXList();
            for (int j = 1; j < gpxList.size(); ++j) {
                final Segment segment = new Segment(gpxList.get(j - 1), gpxList.get(j));
                //checking if there has already been a movement on a given segment
                for (Segment s : map.keySet()) {
                    if (hash(s.hashCode()) == hash(segment.hashCode())) {
                        System.out.print("!");
                    }
                }
                if (map.containsKey(segment)) {
                    map.get(segment).intensify(gpxList.get(j - 1).getTime() + timeList.get(i));
                }
                else {
                    map.put(segment, new Timetable());
                    map.get(segment).intensify(gpxList.get(j - 1).getTime() + timeList.get(i));
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
        for (Segment s : map.keySet()) {
            builder.append(s.toString() + ":\n");
            for (int i = 0; i < Timetable.SIZE; ++i) {
                if (map.get(s).getIntensityByNumber(i) != 0) {
                    hour = i * 5 / 60;
                    minute = i * 5 % 60;
                    builder.append(hour + ":" + minute + " " + map.get(s).getIntensityByNumber(i) + "\n");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    int getIntensity(GHPoint start, GHPoint finish, long time) {
        Segment s = new Segment(start, finish);
        if (map.containsKey(s)) {
          return map.get(s).getIntensity(time);
        }
        return 0;

    }
}
