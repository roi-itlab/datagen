package org.roi.itlab.cassandra;


import com.graphhopper.json.geo.Point;

/**
 * Created by Vlad on 06.03.2017.
 */
public class Edge {
    final int id;
    private final Point start;
    private final Point end;

    public Edge(int id,Point start,Point end) {
        this.start=start;
        this.end=end;
        this.id = id;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}
