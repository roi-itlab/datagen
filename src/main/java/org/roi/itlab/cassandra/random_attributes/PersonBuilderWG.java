package org.roi.itlab.cassandra.random_attributes;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Vadim on 02.03.2017.
 */
public class PersonBuilderWG implements PersonBuilder{
    private Person person;
    private RandGen generator;
    private List<Point> list;

    public PersonBuilderWG()
    {
        person = new Person();
        generator = new RandGen();
    }

    private void create()
    {
        person = new Person();
        //System.out.println(person);
        list = new ArrayList<>();
        list.add(new Point(18,1));
        list.add(new Point(25,2));
        list.add(new Point(30,3));
        list.add(new Point(60,1));

        generator.initialize(-18,75+18,4, list);
        setAge(generator.generate());
        //System.out.println(generator.generate());
        list.clear();
        list.add(new Point(0,0));
        list.add(new Point(4,2));
        list.add(new Point(10,3));
        list.add(new Point(20,3));

        generator.initialize(0,20,4,list);
        setExperience(generator.generate());
        list.clear();
        list.add(new Point(7,3));
        list.add(new Point(9,10));
        list.add(new Point(12,1));

        generator.initialize(-7,19,11, list);
        setWorkStart(generator.generate());

        list.clear();
        list.add(new Point(4,1));
        list.add(new Point(8,4));
        list.add(new Point(12,2));

        generator.initialize(-4,13,4,list);
        setWorkDuration(generator.generate());
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
    public PersonBuilder setWorkDuration(int workDuration) {
        person.setWorkDuration(workDuration);
        return this;
    }

    @Override
    public PersonBuilder setWorkStart(int workStart) {
        person.setWorkStart(workStart);
        return this;
    }

    @Override
    public Person build() {
        create();
        //System.out.println(person.getAge() + "   " + person.getExperience() + "   " + person.getWorkStart() + "    " + person.getWorkDuration());
        return person;
    }
}
