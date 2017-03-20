package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.random.MersenneTwister;

/**
 * author Anush
 */
public class ExperienceRandomGenerator extends RandomGeneratorBuilder{

    private int age;
    private final int LICENSE_AGE = 18;

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void buildGenerator(int seed) {

        int max_exp = age - LICENSE_AGE;

        double[] x = {0.0,5.0,30.0,60.0};
        double[] y  = {1.0,2.0,4.0,2.0};
        org.apache.commons.math3.random.RandomGenerator rng = new MersenneTwister(seed);

        randomGenerator = new RandomGenerator(rng,x,y);
        randomGenerator.setMax(max_exp);
    }

}
