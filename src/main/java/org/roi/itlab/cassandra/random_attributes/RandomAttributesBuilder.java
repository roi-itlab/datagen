package org.roi.itlab.cassandra.random_attributes;

/**
 * Created by Vadim on 26.02.2017.
 */
public interface RandomAttributesBuilder {
    RandomAttributesBuilder setAge(final int age);
    RandomAttributesBuilder setExperience(final int experience);
    RandomAttributesBuilder setAgression(final int agression);
    Attributes build();
}
