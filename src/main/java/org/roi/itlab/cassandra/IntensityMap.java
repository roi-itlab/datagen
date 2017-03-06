package org.roi.itlab.cassandra;

import com.graphhopper.PathWrapper;
import com.graphhopper.util.GPXEntry;
import com.graphhopper.util.shapes.GHPoint;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by liza_moskovskaya on 05/03/2017.
 */
public class IntensityMap {

    private class Traffic {
        //this class keeps an information about the coordinates of the segment, time interval (5 minutes), in which the movement occurs and the intensity of the traffic
        private final GHPoint start;
        private final GHPoint finish;
        private final long time;
        private int trafficIntensity;

        public Traffic(GHPoint start, GHPoint finish, long time) {
            this.start = new GHPoint(start.getLat(), start.getLon());
            this.finish = new GHPoint(finish.getLat(), finish.getLon());
            this.time = getMinutes(time) / 5 * 5;
            trafficIntensity = 1;
        }


        public int getTrafficIntensity() {
            return trafficIntensity;
        }


        public void intensify( ) {
            trafficIntensity += 1;
        }

        @Override
        public String toString( ) {
            return start.toString() + ' ' + finish.toString( ) + ' ' + time / 60 + ':' + time % 60;
        }

        @Override
        public boolean equals(Object ob) {
            if (ob == null) {
                return false;
            }
            if (this.start == ((Traffic)ob).start &&
                    this.finish == ((Traffic)ob).finish &&
                    getMinutes(this.time) == getMinutes(((Traffic)ob).time)) {
                return true;
            }
            return false;
        }
    }


    Map<String, Traffic> map;

    public IntensityMap(List<Long> timeList, List<PathWrapper> pathList) {

        if (timeList.size( ) != pathList.size( )) {
            throw new IllegalArgumentException( );
        }

        map = new HashMap<>( );

        for (int i = 0; i < pathList.size(); ++i) {
            //receiving a list of gpx
            List<GPXEntry> gpxList = pathList.get(i).getInstructions().createGPXList();
            for (int j = 1; j < gpxList.size(); ++j) {
                Traffic traffic = new Traffic(gpxList.get(j - 1), gpxList.get(j), gpxList.get(j - 1).getTime() + timeList.get(i));
                //checking if there has already been a movement on a given segment
                if (map.containsKey(traffic.toString())) {
                    //if it has been, increasing the intencity of traffic
                    map.get(traffic.toString()).intensify();
                }
                else {
                    //if not, creating new fild with our data
                    map.put(traffic.toString(), traffic);
                }
            }
        }
    }

    //translates the miliseconds from 1970-01-01 @ 00:00:00 to minutes from the beginning of the current day
    private long getMinutes(long time) {
        return (time/1000/60 - time/1000/60/60/24 * 60 * 24);
    }

    @Override
    public String toString( ) {
        StringBuffer buffer = new StringBuffer( );
        for (String tr : map.keySet()) {
            buffer.append(tr + ' ' + map.get(tr).getTrafficIntensity() + '\n');
        }
        return buffer.toString();
    }

    public int getIntensity(GHPoint start, GHPoint finish, long time) {
        Traffic tr = new Traffic(start, finish, time);
        if (map.containsKey(tr.toString())) {
            return map.get(tr.toString()).getTrafficIntensity();
        }
        return 0;
    }
}
