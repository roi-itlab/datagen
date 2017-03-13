package org.roi.itlab.cassandra.person;

import org.roi.itlab.cassandra.random_attributes.*;

import java.time.LocalTime;
import java.util.UUID;

/**
 * Created by Vadim on 02.03.2017.
 */
public class PersonBuilderImpl extends PersonBuilder{
    private RandomGenerator ageGenerator;
    private RandomGenerator workDurationGenerator;
    private RandomGenerator workStartGenerator;


    public PersonBuilderImpl()
    {
        RandomGeneratorDirector director = new RandomGeneratorDirector();
        director.setRandomGeneratorBuilder(new AgeRandomGenerator());
        director.constructRandomGenerator();
        ageGenerator = director.getRandomGenerator();

        director.setRandomGeneratorBuilder(new WorkStartRandomGenerator());
        director.constructRandomGenerator();
        workStartGenerator = director.getRandomGenerator();

        director.setRandomGeneratorBuilder(new WorkDurationRandomGenerator());
        director.constructRandomGenerator();
        workDurationGenerator = director.getRandomGenerator();
    }

    @Override
    public void buildAttributes()
    {
        person = new Person();
        setUUID(UUID.randomUUID());

        setAge(ageGenerator.getRandomInt());
        setWorkStart(LocalTime.of(workStartGenerator.getRandomInt(),0));
        setWorkDuration(LocalTime.of(workDurationGenerator.getRandomInt(),0));
        setWorkEnd(LocalTime.of((person.getWorkStart().getHour() + person.getWorkDuration().getHour())% 24,0));
    }

    public PersonBuilder setUUID(UUID uuid)
    {
        person.setId(uuid);
        return this;
    }

    public PersonBuilder setAge(int age) {
        person.setAge(age);
        return this;
    }

    public PersonBuilder setExperience(int experience) {
        person.setExperience(experience);
        return this;
    }

    public PersonBuilder setWorkDuration(LocalTime workDuration) {
        person.setWorkDuration(workDuration);
        return this;
    }

    public PersonBuilder setWorkStart(LocalTime workStart) {
        person.setWorkStart(workStart);
        return this;
    }

    public PersonBuilder setWorkEnd(LocalTime workEnd) {
        person.setWorkEnd(workEnd);
        return this;
    }

    @Override
    public Person getResult() {
        return person;
    }
}
