package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;

/**
 * Created by mkuperman on 3/19/2017.
 */
public class IntensityNormalGenerator extends NormalGenerator {

    public IntensityNormalGenerator(int maxIntensity, org.apache.commons.math3.random.RandomGenerator rng) {
        super(rng);
        double[] x = new double[]{0, maxIntensity/10, maxIntensity/5, maxIntensity/2, maxIntensity};//0, несколько средних значений, max intensity из всех возможных
        double[] y = new double[]{1, 1, 1.5, 2.5, 3};// 0, некий коэффициент, характеризующий количество аварий (например, поставили 3, аварийность увеличилась в 3 раза)
        double[] z = new double[]{0.01, 0.1, 0.2, 0.2, 0.2};

        LinearInterpolator li = new LinearInterpolator();
        setMeanPsf(li.interpolate(x,y));
        setDevPsf(li.interpolate(x,z));

        setMin(getMeanPsf().getKnots()[0]);
        setMax(getMeanPsf().getKnots()[getMeanPsf().getKnots().length-1]);
    }
}
