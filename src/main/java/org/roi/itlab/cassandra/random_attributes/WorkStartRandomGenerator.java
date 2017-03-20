package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.random.MersenneTwister;

/**
 * author Anush
 */
public class WorkStartRandomGenerator extends RandomGeneratorBuilder {

    @Override
    public void buildGenerator(int seed) {

        double[] x = {7.0,9.0,12.0,19.0};
        double[] y  = {3.0,10.0,1.0,0.1};
        org.apache.commons.math3.random.RandomGenerator rng = new MersenneTwister(seed);

        randomGenerator = new RandomGenerator(rng,x,y);
    }
}
