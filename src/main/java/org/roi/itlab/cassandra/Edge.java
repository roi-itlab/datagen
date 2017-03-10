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

    public Edge(int id, Point start, Point end, PointList geometry, double distance) {
        this.start = start;
        this.end = end;
        this.id = id;
        this.geometry = geometry;
        this.distance = distance;
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
}
