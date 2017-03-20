package org.roi.itlab.cassandra.random_attributes;

/**
 * Created by Vadim on 20.03.2017.
 */
public class ExperienceNormalGenerator {

    private NormalGenerator normalExperienceGenerator;
    private double[] x;
    private double[] means;
    private double[] sigma;

    public ExperienceNormalGenerator(org.apache.commons.math3.random.RandomGenerator rng)
    {
        //TODO rename arrays, add arrays/ csv to constractor
        x = new double[]{18, 25,  30, 60, 90};
        means = new double[]{0, 4, 8, 35, 43};
        sigma = new double[]{0, 0.2, 0.6, 5.0, 7.0};


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
