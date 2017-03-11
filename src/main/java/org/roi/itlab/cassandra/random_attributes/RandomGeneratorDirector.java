package org.roi.itlab.cassandra.random_attributes;

/**
 * author Anush
 */
public class RandomGeneratorDirector {
    private RandomGeneratorBuilder randomGeneratorBuilder;

    public RandomGenerator getRandomGenerator() {
        return randomGeneratorBuilder.getRandomGenerator();
    }

    public void setRandomGeneratorBuilder(RandomGeneratorBuilder randomGeneratorBuilder) {
        this.randomGeneratorBuilder = randomGeneratorBuilder;
    }

    public void constructRandomGenerator() {

        randomGeneratorBuilder.buildGenerator();
    }
}

