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

    public int getMaxValue(double[] array) {
        double maxValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
            }
        }
        return (int)maxValue+1;
    }

}
