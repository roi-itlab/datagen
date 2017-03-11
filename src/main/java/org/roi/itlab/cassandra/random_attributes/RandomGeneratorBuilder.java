package org.roi.itlab.cassandra.random_attributes;

/**
 * author Anush
 * author Vadim
 */
public abstract class RandomGeneratorBuilder {
    protected RandomGenerator randomGenerator;

    public RandomGeneratorBuilder()
    {
        randomGenerator = new RandomGenerator();
    }
    public RandomGenerator getRandomGenerator() {
        return randomGenerator;
    }

    public abstract void buildGenerator();
}
