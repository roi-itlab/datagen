package org.roi.itlab.cassandra.random_attributes;

/**
 * author Vadim
 * author Anush
 */
public class PersonDirector {
    private PersonBuilder personBuilder;

    public Person getPerson() {
        return personBuilder.getPerson();
    }

    public void setPersonBuilder(PersonBuilder personBuilder) {
        this.personBuilder = personBuilder;
    }

    public void constructPerson() {

        personBuilder.buildAttributes();
    }
}
