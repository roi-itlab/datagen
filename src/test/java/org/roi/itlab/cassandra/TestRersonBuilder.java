package org.roi.itlab.cassandra;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.math3.stat.Frequency;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.roi.itlab.cassandra.person.Person;
import org.roi.itlab.cassandra.person.PersonBuilder;
import org.roi.itlab.cassandra.person.PersonBuilderImpl;
import org.roi.itlab.cassandra.person.PersonDirector;
import org.roi.itlab.cassandra.random_attributes.CSVReader;
import org.roi.itlab.cassandra.random_attributes.RandomGenerator;


/**
 *author Vadim
 * author Anush
 */
public class TestRersonBuilder {
    List<Person> list = new ArrayList<>();
    int mean_mean = 0;

    Frequency workStartTime = new Frequency();
    Frequency age = new Frequency();
    List<Comparable<?>> mode;

    @Before
    public  void init()
    {
        PersonDirector personDirector = new PersonDirector();
        PersonBuilder personBuilderImpl = new PersonBuilderImpl();
        personDirector.setPersonBuilder(personBuilderImpl);

        for(int j = 0 ; j < 1000;++j) {
            personDirector.constructPerson(j);
            list.add(personDirector.getPerson());
            age.addValue(list.get(j).getAge());
            mean_mean += list.get(j).getAge();
            workStartTime.addValue(list.get(j).getWorkStart());
        }
        mean_mean = mean_mean / list.size();

        mode = age.getMode();
/*

        Iterator<Comparable<?>> iter = age.valuesIterator();
        StringBuilder outBuffer = new StringBuilder();
        while (iter.hasNext())
        {
            Comparable<?> value = iter.next();
            outBuffer.append(value);
            outBuffer.append('\t');
            outBuffer.append(age.getCount(value));
            outBuffer.append('\n');
        }
        System.out.println(outBuffer.toString());

        for(int i = 0 ; i < 15; ++i){
            System.out.println(list.get(i));
        }
    */
    /*
        CSVReader reader = new CSVReader();
        try{reader.Read();}
        catch (Exception e){
            System.out.println(e);
        }
        */
    }

    @Test
    public void TestBuilder() throws Exception {
        Assert.assertEquals(mean_mean, 30, 15);
        Assert.assertEquals(age.getUniqueCount(), 58);
        Assert.assertEquals(mode.get(0).toString(), "33");
        Assert.assertEquals(workStartTime.getUniqueCount(), 13);
    }
}
