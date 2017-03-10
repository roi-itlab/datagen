package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

/**
 * Created by Vadim
 */
public class WorkStartGenerator {
    private RandomGenerator workStartGenerator;

    public WorkStartGenerator()
    {
        workStartGenerator = new RandomGenerator();
        LinearInterpolator li = new LinearInterpolator();
        PolynomialSplineFunction psf;
        double[] x = {7.0,9.0,12.0};
        double[] y = {3.0,10.0,1.0};
        psf = li.interpolate(x, y);

       // workStartGenerator.initialize(11, psf);
    }
    public int getRandomValue()
    {
        return workStartGenerator.getRandomValue();
    }
}
