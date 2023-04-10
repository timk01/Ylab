package io.ylab.intensive.lesson05.eventsourcing.api;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApiApp {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);

        PersonApi personApi = applicationContext.getBean(PersonApi.class);

        System.out.println(personApi.findAll());

        personApi.savePerson(1L, "ivan", "ivanovich", "ivanov");
        personApi.savePerson(1L, "ivan", "ivanovich", "ivanov");
        personApi.savePerson(2L, "petr", "petrovich", "petrov");
        personApi.savePerson(3L, "maria", "shubina", "");

        System.out.println(personApi.findAll());

        Thread.sleep(100);
        System.out.println(personApi.findPerson(1L));
        System.out.println(personApi.findPerson(4L));
        personApi.deletePerson(1L);
        personApi.deletePerson(5L);
        System.out.println(personApi.findAll());
    }
}

