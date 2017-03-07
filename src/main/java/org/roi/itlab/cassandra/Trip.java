package org.roi.itlab.cassandra;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.json.geo.Point;
import com.graphhopper.routing.Path;
import com.graphhopper.storage.NodeAccess;
import com.graphhopper.util.EdgeIteratorState;

import java.util.*;

public class Trip {
    private final Poi from;
    private final Poi to;
    private final long startTime;

    public Trip(Poi from, Poi to, long startTime) {
        this.from = from;
        this.to = to;
        this.startTime = startTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public Poi getFrom() {
        return from;
    }

    public Poi getTo() {
        return to;
    }

    public Path calcPath(GraphHopper hopper) {
        GHRequest req = new GHRequest(from.getLoc().getLatitude(), from.getLoc().getLongitude(), to.getLoc().getLatitude(), to.getLoc().getLongitude()).
                setWeighting("fastest").
                setVehicle("car").
                setLocale(Locale.US);

        //getting unwrapped paths and processing request
        GHResponse rsp = new GHResponse();
        List<Path> paths = hopper.calcPaths(req, rsp);
        if (rsp.hasErrors()) {
            throw new IllegalStateException("routing failed");
        }
        return paths.get(0);
    }

    public Map<Edge, Long> calcEdges(GraphHopper hopper) {
        Map<Edge, Long> map = new HashMap<>();
        Path path = calcPath(hopper);
        NodeAccess nodes = hopper.getGraphHopperStorage().getBaseGraph().getNodeAccess();
        for (EdgeIteratorState edge :
                path.calcEdges()) {
            double x1 = nodes.getLongitude(edge.getAdjNode());
            double x2 = nodes.getLongitude(edge.getBaseNode());
            double x3 = nodes.getLat(edge.getAdjNode());
            double x4 = nodes.getLat(edge.getBaseNode());
            int id = edge.getEdge();
            long time = startTime;
            map.put(new Edge(id,new Point(x4,x2),new Point(x3,x1)), time);
        }
        return map;
    }
}