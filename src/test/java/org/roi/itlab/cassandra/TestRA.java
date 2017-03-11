package org.roi.itlab.cassandra;

import java.time.LocalTime;
import java.util.List;

import org.apache.commons.math3.stat.Frequency;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.roi.itlab.cassandra.random_attributes.*;
/**
 * author Vadim
 * author Anush
 */
public class TestRA {
    int count =1000000;
    int age_mean,workstart_mean,workduration_mean;
    Frequency age_freq,workstart_freq,workduration_freq;
    List<Comparable<?>> age_mode,workstart_mode,workduration_mode;

    @Before
    public  void init()
    {
        age_freq = new Frequency();
        workstart_freq = new Frequency();
        workduration_freq = new Frequency();

        RandomGeneratorDirector randomGeneratorDirector = new RandomGeneratorDirector();
        RandomGeneratorBuilder  ageRandomGenerator = new AgeRandomGenerator();
        randomGeneratorDirector.setRandomGeneratorBuilder(ageRandomGenerator);
        randomGeneratorDirector.constructRandomGenerator();
        RandomGenerator randGen = randomGeneratorDirector.getRandomGenerator();

        long mean = 0;
        int tmp;

        for(int j = 0 ; j < count;++j) {
           tmp = randGen.getRandomValue();
           mean += tmp;
           age_freq.addValue(tmp);
        }

        age_mean = (int)mean/count;
        age_mode = age_freq.getMode();


        RandomGeneratorBuilder  workStartRandomGenerator = new WorkStartRandomGenerator();
        randomGeneratorDirector.setRandomGeneratorBuilder(workStartRandomGenerator);
        randomGeneratorDirector.constructRandomGenerator();
        randGen = randomGeneratorDirector.getRandomGenerator();

        mean = 0;

        for(int j = 0 ; j < count;++j) {
            tmp = randGen.getRandomValue();
            mean += tmp;
            workstart_freq.addValue(tmp);
        }

        workstart_mean =  (int)mean/count;
        workstart_mode = workstart_freq.getMode();



        RandomGeneratorBuilder  workDurationRandomGenerator = new WorkDurationRandomGenerator();
        randomGeneratorDirector.setRandomGeneratorBuilder(workDurationRandomGenerator);
        randomGeneratorDirector.constructRandomGenerator();
        randGen = randomGeneratorDirector.getRandomGenerator();

        mean = 0;

        for(int j = 0 ; j < count;++j) {
            tmp = randGen.getRandomValue();
            mean += tmp;
            workduration_freq.addValue(tmp);
        }

        workduration_mean =  (int)mean/count;
        workduration_mode = workduration_freq.getMode();


    }

    @Test
    public void TestBuilder() throws Exception {
        Assert.assertEquals(age_mean, 35, 15);
        Assert.assertEquals(age_mode.get(0).toString(), "30");
        Assert.assertEquals(workstart_mean, 9, 1);
        Assert.assertEquals(workstart_mode.get(0).toString(), "9");
        Assert.assertEquals(workduration_mean, 8, 2);
        Assert.assertEquals(workduration_mode.get(0).toString(), "8");
    }
}
