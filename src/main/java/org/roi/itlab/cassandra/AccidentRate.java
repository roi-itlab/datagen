package org.roi.itlab.cassandra;

import org.apache.commons.math3.random.RandomGenerator;
import org.roi.itlab.cassandra.person.Person;
import org.roi.itlab.cassandra.random_attributes.IntensityNormalGenerator;
import org.roi.itlab.cassandra.random_attributes.NormalGenerator;

import java.time.LocalTime;

/**
 * @author Anna Striganova
 */
public class AccidentRate {

    private IntensityMap intensityMap;
    private NormalGenerator normalGenerator;
    private final static double averageAccidentProbability = 1.0 / 80_000_000;
    private final static double onewwayFactor = 0.75;
    private RandomGenerator rng;

    public AccidentRate(IntensityMap intensityMap, RandomGenerator rng) {
        this.intensityMap = intensityMap;
        this.rng = rng;
        this.normalGenerator = new IntensityNormalGenerator(intensityMap.getMaxIntensity(), rng);
    }

    public double getProbability(Person person) {
        double normalizedDistance = getNormalizedDistance(person.getToWork(), person.getWorkStart()) + getNormalizedDistance(person.getToHome(), person.getWorkEnd());
        return averageAccidentProbability * normalizedDistance * person.getRushFactor() / person.getSkill();
    }

    public int calculateAccidents(Person person, int days) {
        int accidents = 0;
        double probability = getProbability(person);
        for (int i = 0; i < days; i++) {
            accidents += rng.nextDouble() < probability ? 1 : 0;
        }
        person.setProbability(1 - Math.pow(1 - probability, days));
        return accidents;
    }

    private double getNormalizedDistance(Route route, LocalTime startTime) {
        long time = startTime.toSecondOfDay() * 1000;
        double result = 0;
        Edge[] edges = route.getEdges();
        boolean[] directions = route.getDirections();
        for (int i = 0; i < edges.length; i++) {
            boolean direction = directions[i];
            Edge e = edges[i];
            if (e.isOneWay()) {
                result += e.getDistance() * normalGenerator.getRandomDouble(intensityMap.getIntensity(e, time, direction)) * onewwayFactor;
            } else {
                result += e.getDistance() * normalGenerator.getRandomDouble(intensityMap.getIntensity(e, time, direction));
            }
            time += e.getTime();
        }
        return result;
    }

    public int calculateAccidentsAlter(Person person, int days) {
        int accidents = 0;
        double probability = getProbability(person);
        for (int i = 0; i < days; i++) {
            double d = rng.nextDouble();
            if(d < probability){
                person.setEdgeWithAccidentc(getEdgeWithAccident(person));
                accidents++;
            }
        }
        person.setProbability(1 - Math.pow(1 - probability, days));
        return accidents;
    }

    private Edge getEdgeWithAccident(Person person){
        Edge[] edges = person.getToWork().getEdges();
        boolean direction = true;
        long time = person.getWorkStart().getHour()*1000;
        if(rng.nextDouble() > 0.5){
            edges = person.getToHome().getEdges();
            time = person.getWorkEnd().getHour()*1000;
            direction = false;
        }
        double[] x = new double[edges.length];
        double[] y = new double[edges.length];
        for(int i = 0 ; i< edges.length; ++i){
            x[i] = i;
            y[i] = intensityMap.getIntensity(edges[i], time, direction) + 1;
            time += edges[i].getTime();
        }
        org.roi.itlab.cassandra.random_attributes.RandomGenerator randomGenerator
                = new org.roi.itlab.cassandra.random_attributes.RandomGenerator( rng, x,y);
        return edges[randomGenerator.getRandomInt()];
    }
}
