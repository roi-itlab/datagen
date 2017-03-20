package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.random.MersenneTwister;

/**
 * author Anush
 */
public class WorkDurationRandomGenerator extends RandomGeneratorBuilder {

    @Override
    public void buildGenerator(int seed) {
        double[] x = {4.0,8.0,12.0};
        double[] y  = {1.0,4.0,2.0};
        org.apache.commons.math3.random.RandomGenerator rng = new MersenneTwister(seed);

        randomGenerator = new RandomGenerator(rng,x,y);

    }
}
