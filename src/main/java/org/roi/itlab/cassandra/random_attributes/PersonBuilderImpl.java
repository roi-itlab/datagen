package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.awt.*;
import java.time.LocalTime;
import java.util.*;
import java.util.List;

/**
 * Created by Vadim on 02.03.2017.
 */
public class PersonBuilderImpl implements PersonBuilder{
    private Person person;
    private Random r;
    private List<Point> pointList;
    private List<Point> pointList2;
    private List<Point> pointList3;
    private List<Point> pointList4;
    private final int LICENSE_AGE = 18;

    PolynomialSplineFunction psf1;
    PolynomialSplineFunction psf2;
    PolynomialSplineFunction psf3;
    PolynomialSplineFunction psf4;

    public PersonBuilderImpl()
    {
        r = new Random(System.currentTimeMillis());

        LinearInterpolator li = new LinearInterpolator();
        SplineInterpolator si = new SplineInterpolator();
        double[] x = {18.0,25.0,30.0,60.0,90.0};
        double[] y  = {1.0,2.0,3.0,1.0, 0.1};
        psf1 = li.interpolate(x, y);
        PolynomialSplineFunction psf_s = si.interpolate(x, y);
        /*
        System.out.println(2.3 < psf.value(27.0));
        System.out.println(1.1 < psf.value(20.0));
        System.out.println(1.3 < psf.value(57.0));

        System.out.println(2.3 < psf_s.value(27.0));
        System.out.println(1.1 < psf_s.value(20.0));
        System.out.println(1.8 < psf_s.value(57.0));
        */
        double[] x2 = {0.0,5.0,30.0,60.0};
        double[] y2  = {1.0,2.0,4.0,2.0};
        psf2 = li.interpolate(x2, y2);

        double[] x3 = {7.0,9.0,12.0};
        double[] y3  = {3.0,10.0,1.0};
        psf3 = li.interpolate(x3, y3);

        double[] x4 = {4.0,8.0,12.0};
        double[] y4  = {1.0,4.0,2.0};
        psf4 = li.interpolate(x4, y4);

    }

    public int rand(int a, int b, int c, PolynomialSplineFunction psf)
    {
        int m = -1;
        if(a + b >0) {
            while (m < 0) {
                m = distribution(r.nextInt(a) + b, c * r.nextDouble(), psf);
            }
        }
        else
        {
            m = distribution(a + b, c * r.nextDouble(), psf);
        }
        return m;
    }



    public int distribution(int a, double b, PolynomialSplineFunction psf) {

        if(a >= psf.getKnots()[0] && b < psf.value((double)a))
        {
            return a;
        }

        return -1;
    }

    @Override
    public void buildAttributes()
    {
        person = new Person();
        setAge(rand(75+LICENSE_AGE, -LICENSE_AGE,4,psf1));
        setExperience(rand(person.getAge()-LICENSE_AGE,0,4,psf2));
        setWorkStart(LocalTime.of(rand(19,-7,11,psf3),0));
        setWorkDuration(LocalTime.of(rand(13,-4,4,psf4),0));
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
