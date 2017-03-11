package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.analysis.interpolation.*;

/**
 * author Anush
 */
public class WorkStartRandomGenerator extends RandomGeneratorBuilder {

    private final int LATEST_WORK_START = 12;

    @Override
    public void buildGenerator() {

        double[] x = {7.0,9.0,12.0,19.0};
        double[] y  = {3.0,10.0,1.0,0.1};

        LinearInterpolator li = new LinearInterpolator();
        randomGenerator = new RandomGenerator();
        randomGenerator.setPsf(li.interpolate(x,y));
        //randomGenerator.setMax(LATEST_WORK_START);
        randomGenerator.setProportionalWeight(11);
    }
}
