package org.roi.itlab.cassandra;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static java.util.Objects.hash;

class IntensityMap {

    private class Timetable {
        static final int SIZE = 288;
        private int[] timetable;

        Timetable(int[] arr) {
            timetable = arr.clone();
        }

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

        String toCSV() {
            StringBuilder sb = new StringBuilder(500);
            for (int i = 0; i < SIZE; i++) {
                sb.append(timetable[i]).append('|');
            }
            return sb.toString();
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
        return (int) (time / 1000 / 60 % (60 * 24));
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

    //edgeID|intensity at 00:00|intensity at 00:05|...|intensity at 23:55
    public void writeToCSV(OutputStreamWriter writer) throws IOException {
        for (Map.Entry<Edge, Timetable> entry :
                map.entrySet()) {
            writer.write(Integer.toString(entry.getKey().id));
            writer.write('|');
            writer.write(entry.getValue().toCSV());
            writer.write('\n');
        }
        writer.close();
    }

    public void loadFromCSV(String filename) throws IOException {
        Consumer<String> putEdge = s -> {
            String[] p = s.split("\\|");
            int[] timetable = new int[p.length - 1];
            for (int i = 1; i < p.length; i++) {
                timetable[i - 1] = Integer.parseInt(p[i]);
            }
            map.put(Routing.getEdge(Integer.parseInt(p[0])), new Timetable(timetable));
        };
        Files.lines(Paths.get(filename)).forEach(putEdge);
    }
}
