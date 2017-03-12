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

    @Override
    public String toString( ) {
        return start.toString() + ' ' + end.toString( );
    }

    @Override
    public boolean equals(Object ob) {
        if (ob == null) {
            return false;
        }
        if (ob.getClass() != this.getClass()) {
            return false;
        }
        if (start.equals(((Edge)ob).start) && end.equals(((Edge)ob).end)) {
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
                (int) (Double.doubleToLongBits(end.getLat() / 0.0001) ^
                        (Double.doubleToLongBits(end.getLat() / 0.0001) >>> 32));
        hash = 83 * hash +
                (int) (Double.doubleToLongBits(end.getLon() / 0.0001) ^
                        (Double.doubleToLongBits(end.getLon() / 0.0001) >>> 32));
        return hash;
    }
}
