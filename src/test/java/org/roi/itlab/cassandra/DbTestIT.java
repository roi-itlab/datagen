package org.roi.itlab.cassandra;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

public class DbTestIT {

	Morphia morphia = new Morphia();
	Datastore datastore;
	
	@Before
	public void init() {
		// tell Morphia where to find your classes
		// can be called multiple times with different packages or classes
		morphia.mapPackage("org.roi.payg");

		// create the Datastore connecting to the default port on the local host
		datastore = morphia.createDatastore(new MongoClient(), "payg_test");
		datastore.ensureIndexes();	
		datastore.delete(datastore.createQuery(User.class));
	}
	
//    @Test
    public void testUser() {
    	long countAll = datastore.createQuery(User.class).countAll();
    	Assert.assertEquals(countAll, 0);
    	
    	User user = new User();
    	user.setName("User");
    	ObjectId id = (ObjectId) datastore.save(user).getId();
		Assert.assertNotNull(id);

    	countAll = datastore.createQuery(User.class).countAll();
    	Assert.assertEquals(countAll, 1);
		
		User userdb = datastore.get(User.class, id);
		Assert.assertNotNull(userdb);
		Assert.assertEquals(userdb.getName(), "User");
	}
    
}
