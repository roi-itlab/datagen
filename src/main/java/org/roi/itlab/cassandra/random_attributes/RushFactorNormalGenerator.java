package org.roi.itlab.cassandra.random_attributes;

/**
 * Created by Vadim on 20.03.2017.
 */
public class RushFactorNormalGenerator {
    private NormalGenerator normalExperienceGenerator;
    private double[] x;
    private double[] means;
    private double[] sigma;

    public RushFactorNormalGenerator(org.apache.commons.math3.random.RandomGenerator rng)
    {
        //TODO rename arrays, add arrays/ csv to constractor
        x = new double[]{18, 25,  30, 60, 90};
        means = new double[]{35, 20,5,10,15};
        sigma = new double[]{10, 7.2, 5.6, 3.0, 7.0};


        normalExperienceGenerator = new NormalGenerator( rng, x, means, sigma);
    }

    public double getDouble(int value)
    {
        if(value <= x[0])
            return means[0];
        if(value >= x[x.length-1])
            return normalExperienceGenerator.getRandomDouble(x[x.length-1]);
        return normalExperienceGenerator.getRandomDouble(value);
    }

}
