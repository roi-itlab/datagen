package org.roi.itlab.cassandra.random_attributes;

/**
 * Created by Vadim on 01.03.2017.
 */
public interface PersonBuilder {
    PersonBuilder setAge(final int age);
    PersonBuilder setWorkStart(final int workStart);
    PersonBuilder setWorkDuration(final int workDuration);
    PersonBuilder setExperience(final int experience);

    Person build();
}
