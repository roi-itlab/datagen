package org.roi.itlab.cassandra.person;

import org.roi.itlab.cassandra.Poi;

import java.time.LocalTime;
import java.util.*;

/**
 * Created by Vadim on 01.03.2017.
 */
public class Person {
    private int age;
    private LocalTime workStart;
    private LocalTime workDuration;
    private int experience;
    private LocalTime workEnd;
    private Poi home;
    private Poi work;
    private UUID id;
    private String firstName = "";
    private String lastName = "";

    private int skill;
    private int rushFactor;

    public Person()
    {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public LocalTime getWorkStart() {
        return workStart;
    }

    public LocalTime getWorkDuration() {
        return workDuration;
    }

    public int getExperience() {
        return experience;
    }

    public LocalTime getWorkEnd() {
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

    public void setWorkStart(LocalTime workStart) {
        this.workStart = workStart;
    }

    public void setWorkDuration(LocalTime workDuration) {
        this.workDuration = workDuration;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setWorkEnd(LocalTime workEnd) {
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
