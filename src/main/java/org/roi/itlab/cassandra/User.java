package org.roi.itlab.cassandra;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public class User {
    @Id
    private ObjectId id;
    
    private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
