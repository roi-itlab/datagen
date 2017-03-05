package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Vadim on 02.03.2017.
 */
public class RandGen {
    private int max;
    private int min;
    private int y;
    private Random r;
    PolynomialSplineFunction psf;

    public RandGen()
    {
        r = new Random(System.currentTimeMillis());
    }

    public void initialize(int min, int max, int y, PolynomialSplineFunction psf )
    {
        this.psf = psf;
        this.min = min;
        this.max = max;
        this.y =y;
    }
    private int rand()
    {
        int m = -1;
        if(max+min > 0) {
            while (m < 0) {
                m = distribution(r.nextInt(max) + min, y * r.nextDouble());
            }
        }
        else
        {
            m = distribution(max + min, y * r.nextDouble());
        }
        return m;
    }

    private int distribution(int a, double b)
    {
        /*
        for(int i=1; i < list.size();++i)
        {
            if(a <= list.get(i).x && a >= list.get(i-1).x &&
                    b < (double)(list.get(i).y-list.get(i-1).y)/(list.get(i).x-list.get(i-1).x)*(a-list.get(i-1).x)+list.get(i-1).y)
            {
                    return a;

            }
        }*/

        if(a >= psf.getKnots()[0] && b < psf.value((double)a))
        {
            return a;
        }
        return -1;
    }
    public int generate()
    {
        return rand();
    }
}
