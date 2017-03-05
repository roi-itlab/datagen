package org.roi.itlab.cassandra.random_attributes;

/**
 * author Vadim
 * author Anush
 */
public abstract class PersonBuilder {
    protected Person person;

    public Person getPerson() {
        return person;
    }

    public void createNewPerson(){
        person = new Person();
    }

    public abstract void buildAttributes();

}
