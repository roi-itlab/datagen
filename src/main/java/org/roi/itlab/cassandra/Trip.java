package org.roi.itlab.cassandra;

public class Trip {
    private final Poi from;
    private final Poi to;
    private long startTime;
    private Route route;
    private boolean successRouting = false;

    public Trip(Poi from, Poi to, long startTime) {
        this.from = from;
        this.to = to;
        this.startTime = startTime;
    }

    public void setStartTime(long startTime) {
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

    private void calcRoute() {
        try {
            this.route = Routing.route(this);
            this.successRouting = true;
        } catch (IllegalStateException e) {
            throw e;
        }
    }

    public Route getRoute() {
        if (successRouting) {
            return this.route;
        } else {
            calcRoute();
            return this.route;
        }
    }

}
