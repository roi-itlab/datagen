package org.roi.itlab.cassandra.random_attributes;

import org.roi.itlab.cassandra.Person;

import java.time.LocalTime;

/**
 * Created by Vadim on 01.03.2017.
 */
public abstract class PersonBuilder {
    protected Person person;

    public abstract void buildAttributes();

    public Person getResult()
    {
        return person;
    }
}
