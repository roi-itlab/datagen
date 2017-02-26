package org.roi.itlab.cassandra;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.roi.itlab.cassandra.random_attributes.Attributes;
import org.roi.itlab.cassandra.random_attributes.RandomAttributesBuilder;
import org.roi.itlab.cassandra.random_attributes.RandomAttributesBuilderImpl;
import org.roi.itlab.cassandra.random_attributes.RandomAttributesDirector;

/**
 * Created by Vadim on 26.02.2017.
 */
public class TestRA {
    Attributes attributes;

    @Before
    public  void init()
    {
        RandomAttributesBuilder rab = new RandomAttributesBuilderImpl();
        RandomAttributesDirector rad = new RandomAttributesDirector(rab);

        attributes = rad.constract();
    }

    @Test
    public void TestBuilder() throws Exception
    {
        Assert.assertEquals(attributes.getAge(), 25);
        Assert.assertEquals(attributes.getExpirience(), 10);
        Assert.assertEquals(attributes.getAgression(), 5);
    }

}
