package org.roi.itlab.cassandra.random_attributes;

/**
 * Created by Vadim on 26.02.2017.
 */
public class RandomAttributesDirector {
    private RandomAttributesBuilder builder;

    public RandomAttributesDirector(final RandomAttributesBuilder builder)
    {
        this.builder = builder;
    }

    public Attributes constract()
    {
        //test without random distributions
        return builder.setAge(25).setExperience(10).setAgression(5).build();
    }
}
