package org.roi.itlab.cassandra;

public class Route {
    private final Edge[] edges;
    private final long[] timing;

    public Route(Edge[] edges, long[] timing) {
        this.edges = edges;
        this.timing = timing;
    }

    public Edge[] getEdges() {
        return edges;
    }

    public long[] getTiming() {
        return timing;
    }
}
