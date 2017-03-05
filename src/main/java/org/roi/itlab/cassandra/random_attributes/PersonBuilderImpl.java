package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;


import java.time.LocalTime;
import java.util.Random;

/**
 * author Vadim
 * author Anush
 */
public class PersonBuilderImpl extends PersonBuilder {
    private RandGen generator;
    private PolynomialSplineFunction psf[];
    private final int LICENSE_AGE = 18;
    public PersonBuilderImpl() {
        createNewPerson();
        generator = new RandGen();
        psf = new PolynomialSplineFunction[4];
        LinearInterpolator li = new LinearInterpolator();
        double[] x = {18.0,25.0,30.0,60.0,90.0};
        double[] y  = {1.0,2.0,3.0,1.0, 0.1};
        psf[0] = li.interpolate(x, y);

        double[] x2 = {0.0,5.0,30.0,60.0};
        double[] y2  = {1.0,2.0,4.0,2.0};
        psf[1] = li.interpolate(x2, y2);

        double[] x3 = {7.0,9.0,12.0,19.0};
        double[] y3  = {3.0,10.0,1.0,0.1};
        psf[2] = li.interpolate(x3, y3);

        double[] x4 = {4.0,8.0,12.0};
        double[] y4  = {1.0,4.0,2.0};
        psf[3] = li.interpolate(x4, y4);
    }

    @Override
    public void buildAttributes() {

        generator.initialize(LICENSE_AGE, 75,4,psf[0]);
        person.setAge(generator.generate());
        generator.initialize(person.getAge()-LICENSE_AGE,0,4,psf[1]);
        person.setExperience(generator.generate());
        generator.initialize(7,19,11,psf[2]);
        person.setWorkStart(LocalTime.of(generator.generate(),0));
        generator.initialize(4,12,4,psf[3]);
        person.setWorkDuration(LocalTime.of(generator.generate(),0));
        person.setWorkEnd(LocalTime.of(person.getWorkStart().getHour() + person.getWorkDuration().getHour(),0));
    }
}
