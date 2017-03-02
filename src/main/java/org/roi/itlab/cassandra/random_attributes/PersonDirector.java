package org.roi.itlab.cassandra.random_attributes;

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
        return builder.build();
    }

    public static void main(String[] args)
    {
        PersonBuilderImpl builder = new PersonBuilderImpl();
        PersonDirector director = new PersonDirector(builder);
        List<Person> list = new ArrayList<>();

        System.out.println("age     experience  workStart    duration");
        for(int i = 0; i < 10; ++i) {
            list.add(director.constract());
        }

        for(Person a : list){
            //System.out.println(a);
            System.out.println(a.getAge() + "   " + a.getExperience() + "   " + a.getWorkStart() + "    " + a.getWorkDuration());
        }

    }
}
