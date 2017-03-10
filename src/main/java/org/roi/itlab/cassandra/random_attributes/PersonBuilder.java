package org.roi.itlab.cassandra.random_attributes;

import org.roi.itlab.cassandra.Person;

import java.time.LocalTime;

/**
 * Created by Vadim on 01.03.2017.
 */
public abstract class PersonBuilder {
    protected Person person;

    public abstract PersonBuilder setAge(final int age);
    public abstract PersonBuilder setWorkStart(final LocalTime workStart);
    public abstract PersonBuilder setWorkDuration(final LocalTime workDuration);
    public abstract PersonBuilder setExperience(final int experience);
    public abstract PersonBuilder setWorkEnd(final LocalTime workEnd);

    public abstract void buildAttributes();

    public abstract Person getResult();
}
