package org.roi.itlab.cassandra.random_attributes;


import org.roi.itlab.cassandra.Person;

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


}
