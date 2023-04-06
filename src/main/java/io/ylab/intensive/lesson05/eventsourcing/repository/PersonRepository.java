package io.ylab.intensive.lesson05.eventsourcing.repository;

import io.ylab.intensive.lesson05.eventsourcing.Person;

public interface PersonRepository {
    void savePerson(Person person);

    void deletePerson(Long personId);
}
