package org.roi.itlab.cassandra.random_attributes;

/**
 * author Anush
 */
public abstract class RandomGeneratorBuilder {
    protected RandomGenerator randomGenerator;

    public RandomGenerator getRandomGenerator() {
        return randomGenerator;
    }
    public void createRandomGenerator(){
        randomGenerator = new RandomGenerator();
    }
    public abstract void buildGenerator();
}
