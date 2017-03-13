package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.analysis.interpolation.*;

import java.util.Arrays;


/**
 * Created by Anush
 */

public class AgeRandomGenerator extends RandomGeneratorBuilder{

    @Override
    public void buildGenerator() {
        double[] x = {18.0,25.0,30.0,60.0,90.0};
        double[] y  = {1.0,2.0,3.0,1.0, 0.1};
        randomGenerator = new RandomGenerator(x,y);
        //LinearInterpolator li = new LinearInterpolator();
        //randomGenerator.setPsf(li.interpolate(x,y));
        //randomGenerator.setProportionalWeight(getMaxValue(y));


    }

}