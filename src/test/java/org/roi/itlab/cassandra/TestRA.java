package org.roi.itlab.cassandra;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.Frequency;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.roi.itlab.cassandra.random_attributes.*;
/**
 * author Anush
 */
public class TestRA {
    List<Person> list = new ArrayList<>();
    int mean_mean = 0;
    Frequency f = new Frequency();
    List<Comparable<?>> mode;

    @Before
    public  void init()
    {
        PersonDirector personDirector = new PersonDirector();
        PersonBuilder personBuilderImpl = new PersonBuilderImpl();
        personDirector.setPersonBuilder(personBuilderImpl);


        long mean =0;

        for(int j = 0 ; j < 1000000;++j) {
            personDirector.constructPerson();
            list.add(personDirector.getPerson());
            mean += personDirector.getPerson().getAge();
            f.addValue(list.get(j).getAge());
        }


        mean_mean = (int)mean / list.size();
        System.out.println(mean_mean);
        mode = f.getMode();

       /* for(int i = 0 ; i < mode.size();++i)
        {
            System.out.println("Mode " + mode.get(i) + "  freq = " + f.getCount(mode.get(i)));
        }
        for(int i = 18 ; i < 80;++i)
        {
            System.out.println( i+ " freq = " + f.getCount(i));
        }
        System.out.println(list.get(0).getAge()+ " "+ list.get(0).getExperience() + " "+ list.get(0).getWorkStart() +
        " " + list.get(0).getWorkDuration() + " "+ list.get(0).getWorkEnd());*/

    }

    @Test
    public void TestBuilder() throws Exception {
        Assert.assertEquals(mean_mean, 35, 15);
        Assert.assertEquals(mode.get(0).toString(), "30");
    }
}
