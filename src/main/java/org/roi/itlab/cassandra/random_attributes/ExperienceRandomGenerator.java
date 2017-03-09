package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;

/**
 * author Anush
 */
public class ExperienceRandomGenerator extends RandomGeneratorBuilder{

    private int age;
    private final int LICENSE_AGE = 18;
    private final int MIN_EXP = 0;

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void buildGenerator() {

        int max_exp = age - LICENSE_AGE;

        double[] x = {0.0,5.0,30.0,60.0};
        double[] y  = {1.0,2.0,4.0,2.0};
        LinearInterpolator li = new LinearInterpolator();
        randomGenerator = new RandomGenerator();
        randomGenerator.setMin(MIN_EXP);
        randomGenerator.setMax(max_exp);
        randomGenerator.setProportionalWeight(4);
        randomGenerator.setPsf(li.interpolate(x,y));
    }
}
