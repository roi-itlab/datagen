package org.roi.itlab.cassandra.person;

import org.roi.itlab.cassandra.person.Person;

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
