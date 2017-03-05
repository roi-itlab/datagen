package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import java.util.*;

/**
 * This class generates a random number using LinearInterpolator
 * author Vadim
 * author Anush
 */
public class RandGen {
    private int max;
    private int min;
    private int y;
    private Random r;
    private PolynomialSplineFunction psf;

    public RandGen()
    {
        r = new Random(System.currentTimeMillis());
    }

    public void initialize(int min, int max, int y, PolynomialSplineFunction psf )
    {
        this.min = min;
        this.max = max;
        this.y =y;
        this.psf = psf;
    }

    private int getRandomValue()
    {
        int rand = -1;
            while (rand < 0) {
                int controlPoint;
                try{
                    controlPoint = (int)Math.signum(max-min)*r.nextInt(Math.abs(max - min)) + min;
                }catch (IllegalArgumentException ex){
                    controlPoint = 0;
                }
                double controlValue = y * r.nextDouble();
                if(controlPoint >= psf.getKnots()[0]&& controlValue<psf.value(controlPoint))
                    rand = controlPoint;

            }
        return rand;
    }

    public int generate(){
        return getRandomValue();
    }
}
