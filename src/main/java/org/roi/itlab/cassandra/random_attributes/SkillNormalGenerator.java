package org.roi.itlab.cassandra.random_attributes;

/**
 * Created by Vadim on 20.03.2017.
 */
public class SkillNormalGenerator {
    private NormalGenerator normalExperienceGenerator;
    private double[] x;
    private double[] means;
    private double[] sigma;

    public SkillNormalGenerator(org.apache.commons.math3.random.RandomGenerator rng)
    {
        //TODO rename arrays, add arrays/ csv to constractor
        x = new double[]{0, 1, 5, 10, 50};
        means = new double[]{0, 1,25, 30, 15};
        sigma = new double[]{0, 0.1, 2.5, 4, 7};


        normalExperienceGenerator = new NormalGenerator( rng, x, means, sigma);
    }

    public double getDouble(int value)
    {
        if(value <= x[0])
            return means[0];
        if(value > x[x.length-1])
            return normalExperienceGenerator.getRandomDouble(x[x.length-1]);

        return normalExperienceGenerator.getRandomDouble(value);
    }
}
