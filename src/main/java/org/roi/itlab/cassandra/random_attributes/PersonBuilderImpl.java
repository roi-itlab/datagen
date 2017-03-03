package org.roi.itlab.cassandra.random_attributes;

import java.awt.*;
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

    public PersonBuilderImpl()
    {
        //person = new Person();
        r = new Random(System.currentTimeMillis());

        pointList = new ArrayList<>();
        pointList.add(new Point(18,1));
        pointList.add(new Point(25,2));
        pointList.add(new Point(30,3));
        pointList.add(new Point(60,1));


        pointList2 = new ArrayList<>();
        pointList2.add(new Point(0,1));
        pointList2.add(new Point(5,2));
        pointList2.add(new Point(30,4));
        pointList2.add(new Point(60,2));

        pointList3 = new ArrayList<>();
        pointList3.add(new Point(7,3));
        pointList3.add(new Point(9,10));
        pointList3.add(new Point(12,1));

        pointList4 = new ArrayList<>();
        pointList4.add(new Point(4,1));
        pointList4.add(new Point(8,4));
        pointList4.add(new Point(12,2));

    }

    public int rand(int a, int b, int c, List<Point> list)
    {
        int m = 0;
        while(m <=0)
        {
            m = distribution(r.nextInt(a)+b, c*r.nextDouble(), list);
        }
        return m;
    }



    public int distribution(int a, double b, List<Point> list)
    {

        for(int i=1; i < list.size();++i)
        {
            if(a <= list.get(i).x && a > list.get(i-1).x)
            {
                if(b < (list.get(i).y-list.get(i-1).y)/(list.get(i).x-list.get(i-1).x)*(a-list.get(i-1).x)+list.get(i-1).y)
                {
                    return a;
                }
            }
        }
        return 0;
    }
    public void buildAttributes()
    {
        person = new Person();
        setAge(rand(75+LICENSE_AGE, -LICENSE_AGE,4,pointList));
        setExperience(rand(person.getAge()-LICENSE_AGE,0,4,pointList2));
        setWorkStart(rand(19,-7,11,pointList3));
        setWorkDuration(rand(13,-4,4,pointList4));
        setWorkEnd(person.getWorkStart() + person.getWorkDuration());
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
    public PersonBuilder setWorkEnd(int workEnd) {
        person.setWorkEnd(workEnd);
        return this;
    }

    @Override
    public Person getResult() {
        return person;
    }
}
