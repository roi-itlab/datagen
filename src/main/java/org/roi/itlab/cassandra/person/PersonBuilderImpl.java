package org.roi.itlab.cassandra.person;

import org.apache.commons.math3.random.MersenneTwister;
import org.mongodb.morphia.geo.Point;
import org.roi.itlab.cassandra.random_attributes.*;

import java.time.LocalTime;
import java.util.UUID;

/**
 * author Vadim
 * author Anush
 */
public class PersonBuilderImpl extends PersonBuilder{



    public PersonBuilderImpl() {
        createNewPerson();
    }

    @Override
    public void buildAttributes(int seed)
    {
        //генерирование возраста
        RandomGeneratorDirector randomGeneratorDirector = new RandomGeneratorDirector();
        randomGeneratorDirector.setRandomGeneratorBuilder(new AgeRandomGenerator());
        randomGeneratorDirector.constructRandomGenerator(seed);
        RandomGenerator randGen = randomGeneratorDirector.getRandomGenerator();
        person.setAge(randGen.getRandomInt());

        //генерирование стажа
        /*ExperienceRandomGenerator experienceRandomGenerator = new ExperienceRandomGenerator();
        experienceRandomGenerator.setAge(person.getAge());
        randomGeneratorDirector.setRandomGeneratorBuilder(experienceRandomGenerator);
        randomGeneratorDirector.constructRandomGenerator(seed);
        randGen = randomGeneratorDirector.getRandomGenerator();
        person.setExperience(randGen.getRandomInt());*/

        //генерирование времени начала работы
        randomGeneratorDirector.setRandomGeneratorBuilder(new WorkStartRandomGenerator());
        randomGeneratorDirector.constructRandomGenerator(seed);
        randGen = randomGeneratorDirector.getRandomGenerator();
        person.setWorkStart(LocalTime.of(randGen.getRandomInt(),0));

        //генерирование продолжительности работы
        randomGeneratorDirector.setRandomGeneratorBuilder(new WorkDurationRandomGenerator());
        randomGeneratorDirector.constructRandomGenerator(seed);
        randGen = randomGeneratorDirector.getRandomGenerator();
        person.setWorkDuration(LocalTime.of(randGen.getRandomInt(),0));

        person.setWorkEnd(LocalTime.of((person.getWorkStart().getHour() + person.getWorkDuration().getHour())%24,0));

        person.setId(UUID.randomUUID());

        ExperienceNormalGenerator experienceNormalGenerator = new ExperienceNormalGenerator(new MersenneTwister(seed));
        person.setExperience((int)experienceNormalGenerator.getDouble(person.getAge()));

        SkillNormalGenerator skillNormalGenerator = new SkillNormalGenerator(new MersenneTwister(seed));
        person.setSkill(skillNormalGenerator.getDouble(person.getExperience()));

        RushFactorNormalGenerator rushFactorNormalGenerator = new RushFactorNormalGenerator(new MersenneTwister(seed));
        person.setRushFactor(rushFactorNormalGenerator.getDouble(person.getAge()));

        person.setHome((new HomeLocationGenerator()).sample());
        person.setWork((new WorkLocationGenerator()).sample());
    }

}
