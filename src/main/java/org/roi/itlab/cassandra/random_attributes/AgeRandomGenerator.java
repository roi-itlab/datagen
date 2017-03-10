package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

/**
 * Created by Vadim
 */
public class AgeRandomGenerator {

    private RandomGenerator ageGenerator;

    public AgeRandomGenerator()
    {
        ageGenerator = new RandomGenerator();
        LinearInterpolator li = new LinearInterpolator();
        PolynomialSplineFunction psf;
        double[] x = {18.0,25.0,30.0,60.0,90.0};
        double[] y  = {1.0,2.0,3.0,1.0, 0.1};
        psf = li.interpolate(x, y);
        ageGenerator.initialize(4, psf);
    }

    public int getRandomValue()
    {
        return ageGenerator.getRandomValue();
    }

}
