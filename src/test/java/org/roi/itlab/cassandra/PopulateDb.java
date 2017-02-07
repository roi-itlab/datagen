package org.roi.itlab.cassandra;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

public class PopulateDb {

    public static void main(String[] args) {
    	Morphia morphia = new Morphia();
    	Datastore datastore;
		morphia.mapPackage("org.roi.payg");

		// create the Datastore connecting to the default port on the local host
		datastore = morphia.createDatastore(new MongoClient(), "payg_test");
		datastore.ensureIndexes();	
		datastore.delete(datastore.createQuery(User.class));
    	
    	User user = new User();
    	user.setName("User");
    	ObjectId id = (ObjectId) datastore.save(user).getId();
    	System.out.println("Added user " + id.toString());
	}
}
