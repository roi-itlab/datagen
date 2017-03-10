package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

/**
 * Created by Vadim
 */
public class WorkDurationGenerator {
    private RandomGenerator workDurationGenerator;

    public WorkDurationGenerator()
    {
        workDurationGenerator = new RandomGenerator();
        LinearInterpolator li = new LinearInterpolator();
        PolynomialSplineFunction psf;
        double[] x = {4.0,8.0,12.0};
        double[] y  = {1.0,4.0,2.0};
        psf = li.interpolate(x, y);

        workDurationGenerator.initialize(11, psf);
    }
    public int getRandomValue()
    {
        return workDurationGenerator.getRandomValue();
    }
}
