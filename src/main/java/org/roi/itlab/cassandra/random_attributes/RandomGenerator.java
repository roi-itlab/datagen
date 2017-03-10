package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Vadim on 02.03.2017.
 */
public class RandomGenerator {
    private int max;
    private int min;
    private int weight;
    private Random r;
    PolynomialSplineFunction psf;

    public RandomGenerator()
    {
        r = new Random(System.currentTimeMillis());
    }

    public void initialize(int min, int max, int weight, PolynomialSplineFunction psf )
    {
        this.psf = psf;
        this.min = (int)psf.getKnots()[0];
        this.max = (int)psf.getKnots()[psf.getKnots().length-1];
        this.weight = weight;
    }
    public void initialize(int weight, PolynomialSplineFunction psf )
    {
        this.psf = psf;
        this.min = (int)psf.getKnots()[0];
        this.max = (int)psf.getKnots()[psf.getKnots().length-1];
        this.weight = weight;
    }
    private int rand()
    {//Math.signum(max-min)*r.nextInt(Math.abs(max-min)) + min
        int m = -1;
        if(max+min > 0) {
            while (m < 0) {
                m = distribution(r.nextInt(max+1) - min, weight * r.nextDouble());
            }
        }
        else
        {
            m = distribution( 0 , weight * r.nextDouble());
        }
        return m;
    }

    private int distribution(int a, double b)
    {
        if(a >= psf.getKnots()[0] && b < psf.value((double)a))
        {
            return a;
        }
        return -1;
    }

    public int getRandomValue()
    {
        int rand = -1;
        int controlPoint;
            while (rand < 0) {
                //rand = distribution(r.nextInt(max) - min, weight * r.nextDouble());

                try{
                    controlPoint = ThreadLocalRandom.current().nextInt(min, max+1);
                }catch (IllegalArgumentException ex){ controlPoint = 0;}

                if(controlPoint >= psf.getKnots()[0]&& weight *ThreadLocalRandom.current().nextDouble()<psf.value(controlPoint))
                    rand = controlPoint;
            }

        return rand;
    }
}
