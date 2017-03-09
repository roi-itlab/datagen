package org.roi.itlab.cassandra.random_attributes;

import java.time.LocalTime;

/**
 * author Anush
 */
public class PersonBuilderImpl extends PersonBuilder {

    public PersonBuilderImpl() {
        createNewPerson();
    }

    @Override
    public void buildAttributes() {

        RandomGeneratorDirector randomGeneratorDirector = new RandomGeneratorDirector();
        RandomGeneratorBuilder  ageRandomGenerator = new AgeRandomGenerator();
        randomGeneratorDirector.setRandomGeneratorBuilder(ageRandomGenerator);
        randomGeneratorDirector.constructRandomGenerator();
        RandomGenerator randGen = randomGeneratorDirector.getRandomGenerator();
        person.setAge(randGen.getRandomValue());

        ExperienceRandomGenerator experienceRandomGenerator = new ExperienceRandomGenerator();
        experienceRandomGenerator.setAge(person.getAge());
        randomGeneratorDirector.setRandomGeneratorBuilder(experienceRandomGenerator);
        randomGeneratorDirector.constructRandomGenerator();
        randGen = randomGeneratorDirector.getRandomGenerator();
        person.setExperience(randGen.getRandomValue());

        RandomGeneratorBuilder  workStartRandomGenerator = new WorkStartRandomGenerator();
        randomGeneratorDirector.setRandomGeneratorBuilder(workStartRandomGenerator);
        randomGeneratorDirector.constructRandomGenerator();
        randGen = randomGeneratorDirector.getRandomGenerator();
        person.setWorkStart(LocalTime.of(randGen.getRandomValue(),0));

        RandomGeneratorBuilder  workDurationRandomGenerator = new WorkDurationRandomGenerator();
        randomGeneratorDirector.setRandomGeneratorBuilder(workDurationRandomGenerator);
        randomGeneratorDirector.constructRandomGenerator();
        randGen = randomGeneratorDirector.getRandomGenerator();
        person.setWorkDuration(LocalTime.of(randGen.getRandomValue(),0));

        person.setWorkEnd(LocalTime.of((person.getWorkStart().getHour() + person.getWorkDuration().getHour())%24,0));

    }
}
