package org.roi.itlab.cassandra.person;

import org.apache.commons.math3.random.MersenneTwister;
import org.mongodb.morphia.geo.Point;
import org.roi.itlab.cassandra.random_attributes.*;

import java.time.LocalTime;
import java.util.UUID;

/**
 * Created by Vadim on 02.03.2017.
 */
public class PersonBuilderImpl extends PersonBuilder{
    private RandomGenerator ageGenerator;
    private RandomGenerator experienceGenerator;
    private RandomGenerator workDurationGenerator;
    private RandomGenerator workStartGenerator;
    private HomeLocationGenerator homeLocationGenerator;
    private WorkLocationGenerator workLocationGenerator;
    private ExperienceNormalGenerator experienceNormalGenerator;
    private SkillNormalGenerator skillNormalGenerator;

    private final int LICENSE_AGE = 18;

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

        director.setRandomGeneratorBuilder(new ExperienceRandomGenerator());
        director.constructRandomGenerator();
        experienceGenerator = director.getRandomGenerator();

        experienceNormalGenerator = new ExperienceNormalGenerator(new MersenneTwister(1));
        skillNormalGenerator = new SkillNormalGenerator(new MersenneTwister(1));

        homeLocationGenerator = new HomeLocationGenerator();

        workLocationGenerator = new WorkLocationGenerator();

        //TODO normalRandom   exprience skill etc
        /*
        //задаем дискретные значения x-стаж, у-средний Skill, z-среднеквадратическое отклонение, g-стаж для которого ищем Skill
        double[] x = new double[]{0, 1, 5};
        double[] y = new double[]{0, 1, 25};
        double[] z = new double[]{0, 0.1, 2.5};
        //double g = 4;
        double[] age = new double[]{18, 25,  30, 60, 90};
        double[] y_age = new double[]{0, 4, 8, 35, 43};
        double[] z_age = new double[]{0, 0.2, 0.6, 5.0, 7.0};
        normalSkillGenerator = new NormalGenerator(new MersenneTwister(1), x, y, z);
        //normalExperienceGenerator = new NormalGenerator(new MersenneTwister(1), age, y_age, z_age);
        */
    }

    @Override
    public void buildAttributes()
    {
        person = new Person();
        setUUID(UUID.randomUUID());

        setAge(ageGenerator.getRandomInt());
        //TODO double experience ?
        //experienceGenerator.setMax(person.getAge()- LICENSE_AGE);
        //setExperience(experienceGenerator.getRandomInt());
        //System.out.println(person.getAge());
       /* if(person.getAge() != 18)
            setExperience((int)normalExperienceGenerator.getRandomDouble(person.getAge()));*/
         /*double d = person.getExperience();
        d = d > 5 ? 5: d;
        //System.out.println(d);
        if(d!= 0)
            setSkill(normalSkillGenerator.getRandomDouble(d));*/
        setExperience((int)experienceNormalGenerator.getDouble(person.getAge()));
        setSkill(skillNormalGenerator.getDouble(person.getExperience()));

        setWorkStart(LocalTime.of(workStartGenerator.getRandomInt(),0));
        setWorkDuration(LocalTime.of(workDurationGenerator.getRandomInt(),0));
        setWorkEnd(LocalTime.of((person.getWorkStart().getHour() + person.getWorkDuration().getHour())% 24,0));
        setHome(homeLocationGenerator.sample());
        setWork(workLocationGenerator.sample());
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
    public PersonBuilder setHome(Point point)
    {
        person.setHome(point);
        return this;
    }

    public PersonBuilder setWork(Point point)
    {
        person.setWork(point);
        return this;
    }
    public PersonBuilder setSkill(double skill)
    {
        person.setSkill(skill);
        return this;
    }
    public PersonBuilder setRushFactor(double rushFactor)
    {
        person.setRushFactor(rushFactor);
        return this;
    }

    @Override
    public Person getResult() {
        return person;
    }
}
