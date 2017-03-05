package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.awt.*;
import java.time.LocalTime;
import java.util.*;
import java.util.List;

/**
 * Created by Vadim on 02.03.2017.
 */
public class PersonBuilderWG implements PersonBuilder{
    private Person person;
    private RandGen generator;
    private final int LICENSE_AGE = 18;


    public PersonBuilderWG()
    {
        person = new Person();
        generator = new RandGen();
    }

    @Override
    public void buildAttributes()
    {
        person = new Person();

        LinearInterpolator li = new LinearInterpolator();
        //SplineInterpolator si = new SplineInterpolator();
        PolynomialSplineFunction psf;
        double[] x = {18.0,25.0,30.0,60.0,90.0};
        double[] y  = {1.0,2.0,3.0,1.0, 0.1};
        psf = li.interpolate(x, y);

        //PolynomialSplineFunction psf_s = si.interpolate(x, y);

        generator.initialize(-LICENSE_AGE,75+LICENSE_AGE,4, psf);
        setAge(generator.generate());

        double[] x2 = {0.0,5.0,30.0,60.0};
        double[] y2  = {1.0,2.0,4.0,2.0};
        psf = li.interpolate(x2, y2);

        generator.initialize(0,person.getAge()-LICENSE_AGE,4,psf);
        setExperience(generator.generate());

        double[] x3 = {7.0,9.0,12.0};
        double[] y3  = {3.0,10.0,1.0};
        psf = li.interpolate(x3, y3);

        generator.initialize(-7,19,11, psf);
        setWorkStart(LocalTime.of(generator.generate(),0));

        double[] x4 = {4.0,8.0,12.0};
        double[] y4  = {1.0,4.0,2.0};
        psf = li.interpolate(x4, y4);

        generator.initialize(-4,13,4, psf);
        setWorkDuration(LocalTime.of(generator.generate(),0));

        setWorkEnd(LocalTime.of(person.getWorkStart().getHour() + person.getWorkDuration().getHour(),0));
    }

    @Override
    public PersonBuilder setAge(int age) {
        person.setAge(age);
        return this;
    }

    @Override
    public PersonBuilder setExperience(int experience) {
        person.setExperience(experience);
        return this;
    }

    @Override
    public PersonBuilder setWorkDuration(LocalTime workDuration) {
        person.setWorkDuration(workDuration);
        return this;
    }

    @Override
    public PersonBuilder setWorkStart(LocalTime workStart) {
        person.setWorkStart(workStart);
        return this;
    }

    @Override
    public PersonBuilder setWorkEnd(LocalTime workEnd) {
        person.setWorkEnd(workEnd);
        return this;
    }

    @Override
    public Person getResult() {
        return person;
    }
}
