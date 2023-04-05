package io.ylab.intensive.lesson05.eventsourcing.repository;

import io.ylab.intensive.lesson05.eventsourcing.Person;

public interface PersonRepository {
    public void savePerson(Person person);

    public void deletePerson(Long personId);
}
