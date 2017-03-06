package org.roi.itlab.cassandra;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.routing.Path;

import java.util.List;
import java.util.Locale;

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
}