package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.util.concurrent.ThreadLocalRandom;
/**
 * author Vadim
 * author Anush
 */
public class RandomGenerator{
    private double max;
    private double min;
    private int proportionalWeight;
    private PolynomialSplineFunction psf;

    public RandomGenerator(){}

    public RandomGenerator(double[] x, double[] y)
    {
        LinearInterpolator li = new LinearInterpolator();
        setPsf(li.interpolate(x,y));
        this.min = psf.getKnots()[0];
        this.max = psf.getKnots()[psf.getKnots().length-1];
        proportionalWeight = getMaxValue(y);

    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setProportionalWeight(int proportionalWeight) {
        this.proportionalWeight = proportionalWeight;
    }

    public void setPsf(PolynomialSplineFunction psf) {
        this.psf = psf;
        this.min = psf.getKnots()[0];
        this.max = psf.getKnots()[psf.getKnots().length-1];
    }


    public int getRandomInt() {
        int rand = -1;
        int controlPoint;
        while (rand < 0) {
            try{
                controlPoint = ThreadLocalRandom.current().nextInt((int)min, (int)max+1);
            }catch (IllegalArgumentException ex){ controlPoint = 0;}
            double controlValue = proportionalWeight * ThreadLocalRandom.current().nextDouble();
            if(controlPoint >= psf.getKnots()[0]&& controlValue<psf.value(controlPoint))
                rand = controlPoint;
        }
        return rand;
    }

    public double getRandomDouble() {
        double rand = -1.0;
        double controlPoint;
        while (rand < 0) {
            try{
                controlPoint = ThreadLocalRandom.current().nextDouble(min, max+1.0);
            }catch (IllegalArgumentException ex){ controlPoint = 0;}
            double controlValue = proportionalWeight * ThreadLocalRandom.current().nextDouble();
            if(controlPoint >= psf.getKnots()[0]&& controlValue<psf.value(controlPoint))
                rand = controlPoint;
        }
        return rand;
    }

    public int getMaxValue(double[] array) {
        double maxValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
            }
        }
        return (int)maxValue+1;
    }
}
