package org.roi.itlab.cassandra;

import java.util.ArrayList;
import java.util.Iterator;
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
    Frequency f1 = new Frequency();
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
            f1.addValue(list2.get(j).getAge());
            f.addValue(list2.get(j).getWorkStart());
        }

        for (Person a : list) {
            mean += a.getAge();
        }
        mean_mean =mean / list.size();
        System.out.println(mean_mean);
        mean =0;
        for (Person a : list2) {
            mean += a.getAge();
        }
        mean_mean2 =mean / list2.size();
        System.out.println(mean_mean2);
        mode = f1.getMode();
        //System.out.println(mode.get(0));

        for(int i = 0 ; i < mode.size();++i)
        {
            System.out.println("Mode " + mode.get(i) + "  freq = " + f1.getCount(mode.get(i)));
        }
        for(int i = 18 ; i < 91;++i)
        {
            System.out.println( i+ " freq = "  + f1.getCount(i));
        }

        Iterator<Comparable<?>>  iter = f.valuesIterator();
        StringBuilder outBuffer = new StringBuilder();
        while (iter.hasNext())
        {
            Comparable<?> value = iter.next();
            outBuffer.append(value);
            outBuffer.append('\t');
            outBuffer.append(f.getCount(value));
            outBuffer.append('\n');
        }
        System.out.println(outBuffer.toString());
        //System.out.println(f.toString());

        System.out.println(list.get(0).getAge()+ " "+ list.get(0).getExperience() + " "+ list.get(0).getWorkStart() +
        " " + list.get(0).getWorkDuration() + " "+ list.get(0).getWorkEnd());

        System.out.println(list.get(10).getAge()+ " "+ list.get(10).getExperience() + " "+ list.get(10).getWorkStart() +
                " " + list.get(10).getWorkDuration() + " "+ list.get(10).getWorkEnd());
    }

    @Test
    public void TestBuilder() throws Exception {
        Assert.assertEquals(mean_mean, 30, 15);
        Assert.assertEquals(mean_mean2, 30, 15);
        Assert.assertEquals(mode.get(0).toString(), "30");
    }
}
