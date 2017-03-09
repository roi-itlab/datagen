package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;

/**
 * author Anush
 */
public class WorkDurationRandomGenerator extends RandomGeneratorBuilder {

    private final int SHORTEST_WORK_DURATION = 4;
    private final int LONGEST_WORK_DURATION = 12;
    @Override
    public void buildGenerator() {
        double[] x = {4.0,8.0,12.0};
        double[] y  = {1.0,4.0,2.0};
        LinearInterpolator li = new LinearInterpolator();
        randomGenerator = new RandomGenerator();
        randomGenerator.setMin(SHORTEST_WORK_DURATION);
        randomGenerator.setMax(LONGEST_WORK_DURATION);
        randomGenerator.setProportionalWeight(4);
        randomGenerator.setPsf(li.interpolate(x,y));
    }
}
