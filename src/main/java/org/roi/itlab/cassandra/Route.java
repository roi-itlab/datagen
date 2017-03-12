package org.roi.itlab.cassandra;

public class Route {
    private final Edge[] edges;
    private final long[] timing;
    private final int size;

    public Route(Edge[] edges, long[] timing) {
        if (edges.length != timing.length) {
            throw new IllegalArgumentException("Sizes of the array must be equal!");
        }
        this.edges = edges;
        this.timing = timing;
        this.size = edges.length;
    }

    public Edge[] getEdges() {
        return edges;
    }

    public long[] getTiming() {
        return timing;
    }

    public int getSize( ) { return size; }
}
