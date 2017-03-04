package org.roi.itlab.cassandra;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.Frequency;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.roi.itlab.cassandra.random_attributes.*;


/**
 * Created by Vadim on 26.02.2017.
 */
public class TestRG {
    List<Person> list = new ArrayList<>();
    int mean_mean = 0;
    List<Person> list2 = new ArrayList<>();
    int mean_mean2 = 0;
    Frequency f = new Frequency();
    List<Comparable<?>> mode;

    @Before
    public  void init()
    {
        PersonBuilder rab = new PersonBuilderImpl();
        PersonDirector rad = new PersonDirector(rab);
        PersonBuilder rab1 = new PersonBuilderWG();
        PersonDirector rad2 = new PersonDirector(rab1);

        int mean =0;

        for(int j = 0 ; j < 1000000;++j) {
            list.add(rad.constract());
            list2.add(rad2.constract());
            f.addValue(list2.get(j).getAge());
        }

        for (Person a : list) {
            mean += a.getAge();
        }
        mean_mean =mean / list.size();
        //System.out.println(mean_mean);
        mean =0;
        for (Person a : list2) {
            mean += a.getAge();
        }
        mean_mean2 =mean / list2.size();
        //System.out.println(mean_mean2);
        mode = f.getMode();
        //System.out.println(mode.get(0));
        /*
        for(int i = 0 ; i < mode.size();++i)
        {
            System.out.println("Mode " + mode.get(i) + "  freq = " + f.getCount(mode.get(i)));
        }
        for(int i = 18 ; i < 75;++i)
        {
            System.out.println( i+ " freq = " + f.getCount(i));
        }

        System.out.println(list.get(0).getAge()+ " "+ list.get(0).getExperience() + " "+ list.get(0).getWorkStart() +
        " " + list.get(0).getWorkDuration() + " "+ list.get(0).getWorkEnd());

        */
    }

    @Test
    public void TestBuilder() throws Exception {
        Assert.assertEquals(mean_mean, 30, 12);
        Assert.assertEquals(mean_mean2, 30, 12);
        Assert.assertEquals(mode.get(0).toString(), "30");
    }
}
