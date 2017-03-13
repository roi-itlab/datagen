package org.roi.itlab.cassandra;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.hash;

class IntensityMap {

    private class Timetable {
        static final int SIZE = 288;
        private int[] timetable;

        Timetable() {
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

    public IntensityMap() {
        map = new HashMap<>();
    }

    public IntensityMap(int capacity) {
        map = new HashMap<>(capacity);
    }

    public void put(long starttime, Route route) {
        long time = starttime;
        for (Edge e :
                route.getEdges()) {
            map.putIfAbsent(e, new Timetable());
            map.get(e).intensify(time);
            time += e.getTime();
        }
    }

    public void put(List<Long> timeList, List<Route> routes) {

        if (timeList.size() != routes.size()) {
            throw new IllegalArgumentException("Sizes of the lists must be equal!");
        }

        for (int i = 0; i < routes.size(); ++i) {
            this.put(timeList.get(i), routes.get(i));

        }
    }

    //translates the miliseconds from 1970-01-01 @ 00:00:00 to minutes from the beginning of the current day
    private int getMinutes(long time) {
        return (int) (time / 1000 / 60 - time / 1000 / 60 / 60 / 24 * 60 * 24);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int hour;
        int minute;
        for (Map.Entry<Edge, Timetable> entry : map.entrySet()) {
            builder.append(entry.getKey().toString() + ":\n");
            for (int i = 0; i < Timetable.SIZE; ++i) {
                if (entry.getValue().getIntensityByNumber(i) != 0) {
                    hour = i * 5 / 60;
                    minute = i * 5 % 60;
                    builder.append(hour + ":" + minute + " " + entry.getValue().getIntensityByNumber(i) + "\n");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    //traffic intensity on one edge
    int getIntensity(Edge edge, long time) {
        if (map.containsKey(edge)) {
            return map.get(edge).getIntensity(time);
        }
        return 0;
    }

    //traffic intensity on road network
    Map<Edge, Integer> getIntensity(long time) {
        Map<Edge, Integer> temp = new HashMap<>();
        for (Edge edge :
                map.keySet()) {
            temp.put(edge, getIntensity(edge, time));
        }
        return temp;
    }
}
