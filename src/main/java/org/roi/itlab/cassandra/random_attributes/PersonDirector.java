package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vadim on 02.03.2017.
 */
public class PersonDirector {
    private PersonBuilder builder;

    public PersonDirector(final PersonBuilder builder)
    {
        this.builder = builder;
    }
    public Person constract()
    {
        builder.buildAttributes();
        return builder.getResult();
    }

    public static void main(String[] args)
    {
        PersonBuilderImpl builder = new PersonBuilderImpl();
        PersonDirector director = new PersonDirector(builder);
        List<Person> list = new ArrayList<>();
        DescriptiveStatistics stats = new DescriptiveStatistics();

        System.out.println("age     experience  workStart    duration");
        for(int i = 0; i < 10000; ++i) {
            list.add(director.constract());
            stats.addValue(list.get(i).getAge());
        }

        for(Person a : list){
            //System.out.println(a);
           // System.out.println(a.getAge() + "   " + a.getExperience() + "   " + a.getWorkStart() + "    " + a.getWorkDuration());
        }
        System.out.println(stats.getMean());
        System.out.println(stats.getPopulationVariance());

        System.out.println("WG");
        PersonBuilderWG g = new PersonBuilderWG();
        director = new PersonDirector(g);
        list.clear();
        for(int i = 0; i < 10000; ++i) {
            list.add(director.constract());
        }

        for(Person a : list){
            //System.out.println(a);
           // System.out.println(a.getAge() + "   " + a.getExperience() + "   " + a.getWorkStart() + "    " + a.getWorkDuration());
        }
        System.out.println("Ok");

    }
}
