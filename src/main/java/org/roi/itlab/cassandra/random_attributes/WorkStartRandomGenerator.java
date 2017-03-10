package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;

/**
 * author Anush
 */
public class WorkStartRandomGenerator extends RandomGeneratorBuilder {

    private final int EARLY_WORK_START = 7;
    private final int LATEST_WORK_START = 19;

    @Override
    public void buildGenerator() {

        double[] x = {7.0,9.0,12.0,19.0};
        double[] y  = {3.0,10.0,1.0,0.1};

        LinearInterpolator li = new LinearInterpolator();
        randomGenerator = new RandomGenerator();
        randomGenerator.setMin(EARLY_WORK_START);
        randomGenerator.setMax(LATEST_WORK_START);
        randomGenerator.setProportionalWeight(11);
        randomGenerator.setPsf(li.interpolate(x,y));
    }
}
