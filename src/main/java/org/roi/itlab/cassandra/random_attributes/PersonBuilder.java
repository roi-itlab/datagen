package org.roi.itlab.cassandra.random_attributes;

import java.time.LocalTime;

/**
 * Created by Vadim on 01.03.2017.
 */
public interface PersonBuilder {
    PersonBuilder setAge(final int age);
    PersonBuilder setWorkStart(final LocalTime workStart);
    PersonBuilder setWorkDuration(final LocalTime workDuration);
    PersonBuilder setExperience(final int experience);
    PersonBuilder setWorkEnd(final LocalTime workEnd);

    void buildAttributes();

    Person getResult();
}
