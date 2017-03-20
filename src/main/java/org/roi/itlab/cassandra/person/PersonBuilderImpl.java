package org.roi.itlab.cassandra.person;

import org.apache.commons.math3.random.MersenneTwister;
import org.mongodb.morphia.geo.GeoJson;
import org.roi.itlab.cassandra.Location;
import org.roi.itlab.cassandra.Poi;
import org.roi.itlab.cassandra.random_attributes.*;

import java.time.LocalTime;
import java.util.Arrays;
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
        ExperienceRandomGenerator experienceRandomGenerator = new ExperienceRandomGenerator();
        experienceRandomGenerator.setAge(person.getAge());
        randomGeneratorDirector.setRandomGeneratorBuilder(experienceRandomGenerator);
        randomGeneratorDirector.constructRandomGenerator(seed);
        randGen = randomGeneratorDirector.getRandomGenerator();
        person.setExperience(randGen.getRandomInt());

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

        //генерирование местоположений
        org.apache.commons.math3.random.RandomGenerator rng = new MersenneTwister(1);
        Location big = new Location(rng, GeoJson.point(60, 40), 500, 2);
        Location small = new Location(rng, GeoJson.point(61, 41), 100, 1);
        LocationGenerator generator = new LocationGenerator(rng, Arrays.asList(new Location[] {big, small}));
        Poi home = new Poi();
        home.setLoc(generator.sample());
        person.setHome(home);

        Poi work = new Poi();
        work.setLoc(generator.sample());
        person.setWork(work);

        //генерирование Skill
        double[] x = new double[]{0, 1, 5, 10, 60};
        double[] y = new double[]{0, 1, 25, 30, 32};
        double[] z = new double[]{0, 0.1, 2.5, 3.0, 3.2};
        NormalGenerator normalGenerator = new NormalGenerator(new MersenneTwister(seed), x, y, z);
        person.setSkill(normalGenerator.getRandomDouble(person.getExperience()+0.01));

    }

}
