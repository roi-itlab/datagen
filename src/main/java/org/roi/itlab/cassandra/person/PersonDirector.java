package org.roi.itlab.cassandra.person;


/**
 * Created by Vadim on 02.03.2017.
 */
public class PersonDirector {
    private PersonBuilder personBuilder;

    public Person getPerson() {
        return personBuilder.getPerson();
    }

    public void setPersonBuilder(PersonBuilder personBuilder) {
        this.personBuilder = personBuilder;
    }

    public void constructPerson(int seed) {

        personBuilder.buildAttributes(seed);
    }



}
