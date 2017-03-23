package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.analysis.interpolation.*;
import org.apache.commons.math3.random.MersenneTwister;

/**
 * author Anush
 */
public class WorkDurationRandomGenerator extends RandomGeneratorBuilder {

    @Override
    public void buildGenerator(int seed) {
        double[] x = {4.0,8.0,12.0};
        double[] y  = {1.0,4.0,2.0};
        randomGenerator = new RandomGenerator(new MersenneTwister(seed),x,y);
    }
}
