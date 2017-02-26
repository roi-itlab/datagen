package org.roi.itlab.cassandra.random_attributes;

/**
 * Created by Vadim on 26.02.2017.
 */
public class RandomAttributesBuilderImpl implements RandomAttributesBuilder {
    private Attributes att;

    @Override
    public RandomAttributesBuilder setAge(int age) {
        att.setAge(age);
        return this;
    }

    @Override
    public RandomAttributesBuilder setAgression(int agression) {
        att.setAgression(agression);
        return this;
    }

    @Override
    public RandomAttributesBuilder setExperience(int experience) {
        att.setExpirience(experience);
        return this;
    }

    @Override
    public Attributes build() {
        return att;
    }
}
