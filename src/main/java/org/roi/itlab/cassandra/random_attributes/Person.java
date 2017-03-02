package org.roi.itlab.cassandra.random_attributes;

import org.roi.itlab.cassandra.Poi;

/**
 * Created by Vadim on 01.03.2017.
 */
public class Person {
    private int age;
    private int workStart;
    private int workDuration;
    private int experience;
    private int workEnd;
    private Poi home;
    private Poi work;

    private int skill;
    private int rushFactor;

    public Person()
    {}

    public int getAge() {
        return age;
    }

    public int getWorkStart() {
        return workStart;
    }

    public int getWorkDuration() {
        return workDuration;
    }

    public int getExperience() {
        return experience;
    }

    public int getWorkEnd() {
        return workEnd;
    }

    public Poi getHome() {
        return home;
    }

    public Poi getWork() {
        return work;
    }

    public int getSkill() {
        return skill;
    }

    public int getRushFactor() {
        return rushFactor;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWorkStart(int workStart) {
        this.workStart = workStart;
    }

    public void setWorkDuration(int workDuration) {
        this.workDuration = workDuration;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setWorkEnd(int workEnd) {
        this.workEnd = workEnd;
    }

    public void setHome(Poi home) {
        this.home = home;
    }

    public void setWork(Poi work) {
        this.work = work;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public void setRushFactor(int rushFactor) {
        this.rushFactor = rushFactor;
    }
}
