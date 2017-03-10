package org.roi.itlab.cassandra.random_attributes;

import org.roi.itlab.cassandra.Person;

import java.time.LocalTime;

/**
 * Created by Vadim on 02.03.2017.
 */
public class PersonBuilderImpl extends PersonBuilder{
    private AgeRandomGenerator ageGenerator;
    private WorkStartGenerator workStartGenerator;
    private WorkDurationGenerator workDurationGenerator;


    public PersonBuilderImpl()
    {
        person = new Person();
        ageGenerator = new AgeRandomGenerator();
        workStartGenerator = new WorkStartGenerator();
        workDurationGenerator = new WorkDurationGenerator();
    }

    @Override
    public void buildAttributes()
    {
        person = new Person();
        setAge(ageGenerator.getRandomValue());
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
