package org.roi.itlab.cassandra;

import org.apache.commons.math3.random.MersenneTwister;
import org.roi.itlab.cassandra.person.Person;
import org.roi.itlab.cassandra.random_attributes.NormalGenerator;

/**
 *  @author Anna Striganova
 */
public class AccidentRate {

    private IntensityMap iMap;
    private Person person;
    private Route routeToHome;
    private Route routeToWork;
    private long accidentRate;

    public AccidentRate(IntensityMap iMap, Person person, Route routeToHome, Route routeToWork) {
        this.iMap = iMap;
        this.person = person;
        this.routeToHome = routeToHome;
        this.routeToWork = routeToWork;
        accidentRate = 0;
    }

    public void setAccidentRate(long time, int someLength, double epselon) {
        calculateAccidentRate(time, someLength, epselon, routeToHome);
        calculateAccidentRate(time, someLength, epselon, routeToWork);
        person.setAccidentRate(accidentRate);
    }

    /**
     * @param time       the travel time along the route
     * @param someLength ex. 100m, the ratio for accidents routes with lots of short segments
     * @param epselon    the criterion for the addition of probabilities if segment consists of very small routes
     */

    public void calculateAccidentRate(long time, int someLength, double epselon, Route route) {
        double averageAccidentProbability = 1 / 36500;

        //firstly calculate accidentRate for routeToHome
        int maxIntensity = 0;
        int routeLengthFactor = 0;
        //search the maximum of intensity among all edges in routeToHome and calculate summary distance
        for (Edge e : route.getEdges()) {
            // System.out.println(iMap.getIntensity(e, time));
            if (iMap.getIntensity(e, time) > maxIntensity) {
                maxIntensity = iMap.getIntensity(e, time);
            }
            routeLengthFactor += (int) e.getDistance();
            //System.out.println(e.getDistance());
        }
        routeLengthFactor += someLength;
        System.out.println("maxIntensity:" + maxIntensity + " distance: " + routeLengthFactor);

        double[] x = new double[]{0, maxIntensity / 4, maxIntensity / 3, maxIntensity / 2, maxIntensity};//0, несколько средних значений, max intensity из всех возможных
        double[] y = new double[]{0, 1, 2, 2.5, 3};// 0, некий коэффициент, характеризующий количество аварий (например, поставили 3, аварийность увеличилась в 3 раза)
        //standard deviation
        double[] z = new double[]{0.01, 0.1, 0.2, 2.1, 2.5};
        NormalGenerator normalGenerator = new NormalGenerator(new MersenneTwister(1), x, y, z);
        double rushFactor = person.getRushFactor();
        double skillFactor = person.getSkill();
        //System.out.println(rushFactor + "  " + routeLengthFactor + " " + skillFactor + " ");
        double someN;
        double probability = 0.0;
        for (int k = 0; k < 365; k++) {
            someN = Math.random();
            if (routeLengthFactor < epselon) {
                //for each road segment in route calculate probability of accident and compare with a random value from 0 to 1
                for (Edge e : route.getEdges()) {
                    probability += averageAccidentProbability * skillFactor * routeLengthFactor *
                    /*rushFactor * */normalGenerator.getRandomDouble(iMap.getIntensity(e, time));
                }
                if (probability < someN) {
                    accidentRate++;
                }
            } else {
                for (Edge e : route.getEdges()) {
                    probability = averageAccidentProbability * skillFactor * routeLengthFactor *
                    /*rushFactor * */normalGenerator.getRandomDouble(iMap.getIntensity(e, time));
                    if (probability < someN) {
                        accidentRate++;
                    }
                }
            }
        }
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Route getRoute() {
        return routeToHome;
    }

    public void setRoute(Route route) {
        this.routeToHome = route;
    }
}
