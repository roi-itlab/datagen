package org.roi.itlab.cassandra;

import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.geo.GeoJson;
import org.mongodb.morphia.geo.Point;

public class Poi {
    @Id
    private String id;
    
    private String name;
    private int type;
    private Point loc;

    public Poi() {
    }
    	
    public Poi(String id, String name, int type, double lat, double lng) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.setLoc(GeoJson.point(lat, lng));
	}
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Point getLoc() {
		return loc;
	}

	public void setLoc(Point loc) {
		this.loc = loc;
	}
}
