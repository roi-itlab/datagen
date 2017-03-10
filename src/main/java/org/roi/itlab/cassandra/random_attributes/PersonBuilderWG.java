package org.roi.itlab.cassandra.random_attributes;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.roi.itlab.cassandra.Person;

import java.time.LocalTime;

/**
 * Created by Vadim
 */
public class PersonBuilderWG extends PersonBuilder{
    private RandomGenerator ageGenerator;
    private RandomGenerator workStartGenerator;
    private RandomGenerator workDurationGenerator;
    private final int LICENSE_AGE = 18;


    public PersonBuilderWG()
    {
        person = new Person();
        ageGenerator = new RandomGenerator();
        workStartGenerator = new RandomGenerator();
        workDurationGenerator = new RandomGenerator();

        LinearInterpolator li = new LinearInterpolator();
        //SplineInterpolator si = new SplineInterpolator();
        PolynomialSplineFunction psf;
        double[] x = {18.0,25.0,30.0,60.0,90.0};
        double[] y  = {1.0,2.0,3.0,1.0, 0.1};
        psf = li.interpolate(x, y);
        ageGenerator.initialize(LICENSE_AGE,73+LICENSE_AGE,4, psf);

        double[] x3 = {7.0,9.0,12.0};
        double[] y3  = {3.0,10.0,1.0};
        psf = li.interpolate(x3, y3);

        workStartGenerator.initialize(7,12,11, psf);

        double[] x4 = {4.0,8.0,12.0};
        double[] y4  = {1.0,4.0,2.0};
        psf = li.interpolate(x4, y4);

        workDurationGenerator.initialize(4,12,4, psf);
    }

    @Override
    public void buildAttributes()
    {
        person = new Person();



        //PolynomialSplineFunction psf_s = si.interpolate(x, y);


        setAge(ageGenerator.getRandomValue());
        /*
        double[] x2 = {0.0,5.0,30.0,60.0,80.0};
        double[] y2  = {1.0,2.0,4.0,2.0,1.0};
        psf = li.interpolate(x2, y2);

        workStartGenerator.initialize(0,person.getAge()-LICENSE_AGE,4,psf);
        setExperience(workStartGenerator.getRandomValue());
        */

        setWorkStart(LocalTime.of(workStartGenerator.getRandomValue(),0));


        setWorkDuration(LocalTime.of(workDurationGenerator.getRandomValue(),0));

        setWorkEnd(LocalTime.of((person.getWorkStart().getHour() + person.getWorkDuration().getHour())% 24,0));
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
