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
    PolynomialSplineFunction psf;

    public RandomGenerator()
    {
    }

    public void initialize(int weight, PolynomialSplineFunction psf )
    {
        this.psf = psf;
        this.min = (int)psf.getKnots()[0];
        this.max = (int)psf.getKnots()[psf.getKnots().length-1];
        this.weight = weight;
    }

    public int getRandomValue()
    {
        int rand = -1;
        int controlPoint;
            while (rand < 0) {
                try{
                    controlPoint = ThreadLocalRandom.current().nextInt(min, max+1);
                }catch (IllegalArgumentException ex){ controlPoint = 0;}

                if(controlPoint >= psf.getKnots()[0]&& weight *ThreadLocalRandom.current().nextDouble()<psf.value(controlPoint))
                    rand = controlPoint;
            }

        return rand;
    }
}
