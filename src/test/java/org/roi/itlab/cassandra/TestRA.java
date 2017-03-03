package org.roi.itlab.cassandra;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        RandomAttributesDirector rad = new RandomAttributesDirector();

        attributes = rad.constract();
    }

    @Test
    public void TestBuilder() throws Exception {
        //System.out.println(attributes.getAge() + " " + attributes.getAgression() + " " + attributes.getExpirience());

        // Assert.assertEquals(attributes.getAge(), 25);
        //Assert.assertEquals(attributes.getExpirience(), 10);
        //.assertEquals(attributes.getAgression(), 5);
  /*  }

    public  void m()
   {
    */
        Attributes attribut;


        RandomAttributesBuilder rab = new RandomAttributesBuilderImpl();
        RandomAttributesDirector rad = new RandomAttributesDirector();


        int mean_mean = 0;
        for(int j = 0 ; j < 1000;++j) {
            List<Attributes> list = new ArrayList<Attributes>();
            //System.out.println("start adding list");
            for (int i = 0; i < 1000; ++i) {
                attribut = rad.constract();
                list.add(attribut);
            }

            int mean = 0;

            //System.out.println(attributes.getAge() + " " + attributes.getAgression() + " " + attributes.getExpirience());
            //System.out.println(list.size());
            for (Attributes a : list) {
                mean += a.getAge();
            }
            mean_mean +=mean / list.size();
        }
        System.out.println("Mean " + mean_mean/1000);
        Assert.assertEquals(mean_mean/1000, 30, 5);
    }
}
