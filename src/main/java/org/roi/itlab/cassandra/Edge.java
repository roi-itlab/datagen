package org.roi.itlab.cassandra;


import com.graphhopper.json.geo.Point;
import com.graphhopper.util.PointList;

//Road segment
public class Edge {

    final int id;
    private final Point start;
    private final Point end;
    //including start and end points
    private PointList geometry;
    private double distance;
    // distance/average speed
    private int time;
    private double speed;

    public Edge(int id, Point start, Point end, PointList geometry, double distance, int time, double speed) {
        this.start = start;
        this.end = end;
        this.id = id;
        this.geometry = geometry;
        this.distance = distance;
        this.time = time;
        this.speed = speed;
    }


    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public PointList getGeometry() {
        return geometry;
    }

    public double getDistance() {
        return distance;
    }

    public int getTime() {
        return time;
    }

    public double getSpeed() {
        return speed;
    }

    @Override
    public boolean equals(Object ob) {
        if (ob == null) {
            return false;
        }
        if (ob.getClass() != this.getClass()) {
            return false;
        }
        if (this.id == ((Edge) ob).id) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "edge id: " + id + " start: " + start.toString() + " end: " + end.toString();
    }


    @Override
    public int hashCode() {
        return this.id;
    }
}
