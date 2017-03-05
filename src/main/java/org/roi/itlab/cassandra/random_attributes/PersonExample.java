package org.roi.itlab.cassandra.random_attributes;

/**
 *  author Vadim
 * author Anush
 */
public class PersonExample {


    public static void main(String[] args) {

        PersonDirector director = new PersonDirector();
        PersonBuilder personBuilderImpl = new PersonBuilderImpl();
        director.setPersonBuilder(personBuilderImpl);
        director.constructPerson();
        System.out.println(personBuilderImpl.getPerson().toString());

    }
}
