package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import java.util.concurrent.ThreadLocalRandom;

/**
 * author Anush
 */
public class RandomGenerator{
    private int max;
    private int min;
    private int proportionalWeight;
    private PolynomialSplineFunction psf;

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setProportionalWeight(int proportionalWeight) {
        this.proportionalWeight = proportionalWeight;
    }

    public void setPsf(PolynomialSplineFunction psf) {
        this.psf = psf;
    }


    public int getRandomValue() {
        int rand = -1;
        int controlPoint;
        while (rand < 0) {
            try{
                controlPoint = ThreadLocalRandom.current().nextInt(min, max);
            }catch (IllegalArgumentException ex){ controlPoint = 0;}
            double controlValue = proportionalWeight *ThreadLocalRandom.current().nextDouble();
            if(controlPoint >= psf.getKnots()[0]&& controlValue<psf.value(controlPoint))
                rand = controlPoint;

        }
        return rand;
    }
}
