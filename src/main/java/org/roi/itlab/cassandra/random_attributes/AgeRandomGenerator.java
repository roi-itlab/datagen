package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.analysis.interpolation.HermiteInterpolator;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;

/**
 * author Anush
 */
public class AgeRandomGenerator extends RandomGeneratorBuilder{

    private final int LICENSE_AGE = 18;
    private final int MAX_AGE = 75;
    @Override
    public void buildGenerator() {
        double[] x = {18.0,25.0,30.0,60.0,90.0};
        double[] y  = {1.0,2.0,3.0,1.0, 0.1};
        LinearInterpolator li = new LinearInterpolator();
        randomGenerator = new RandomGenerator();
        randomGenerator.setMin(LICENSE_AGE);
        randomGenerator.setMax(MAX_AGE);
        randomGenerator.setProportionalWeight(4);
        randomGenerator.setPsf(li.interpolate(x,y));

    }
}
