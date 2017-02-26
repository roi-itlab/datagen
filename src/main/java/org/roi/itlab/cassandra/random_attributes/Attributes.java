package org.roi.itlab.cassandra.random_attributes;

/**
 * Created by Vadim on 26.02.2017.
 */
public class Attributes {
    private int age;
    private int experience;
    private int agression;

    public Attributes()
    {}

    public int getAge() {
        return age;
    }

    public int getExpirience() {
        return experience;
    }

    public int getAgression() {
        return agression;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setExpirience(int expirience) {
        this.experience = expirience;
    }

    public void setAgression(int agression) {
        this.agression = agression;
    }
}
