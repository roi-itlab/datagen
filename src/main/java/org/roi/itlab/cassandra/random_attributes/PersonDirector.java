package org.roi.itlab.cassandra.random_attributes;

/**
 * author Vadim
 * author Anush
 */
public class PersonDirector {
    private PersonBuilder personBuilder;

    public PersonBuilder getPersonBuilder() {
        return personBuilder;
    }

    public void setPersonBuilder(PersonBuilder personBuilder) {
        this.personBuilder = personBuilder;
    }

    public void constructPerson() {

        personBuilder.buildAttributes();
    }
}
