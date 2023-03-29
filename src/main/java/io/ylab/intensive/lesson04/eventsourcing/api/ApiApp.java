package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;

import javax.sql.DataSource;

public class ApiApp {
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = initMQ();

        DataSource dataSource = DbUtil.buildDataSource();
        PersonApi personApi = new PersonApiImpl(dataSource, connectionFactory);

        System.out.println(personApi.findAll());

        personApi.savePerson(1L, "ivan", "ivanovich", "ivanov");
        personApi.savePerson(1L, "ivan", "ivanovich", "ivanov");
        personApi.savePerson(2L, "petr", "petrovich", "petrov");
        personApi.savePerson(3L, "maria", "shubina", "");
        Thread.sleep(100);
        System.out.println(personApi.findAll());
        System.out.println(personApi.findPerson(1L));
        System.out.println(personApi.findPerson(4L));
        personApi.deletePerson(1L);
        personApi.deletePerson(5L);
        System.out.println(personApi.findAll());
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }
}
