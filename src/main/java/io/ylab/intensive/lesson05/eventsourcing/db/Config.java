package io.ylab.intensive.lesson05.eventsourcing.db;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.DbUtil;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@ComponentScan(basePackages = {
        "io.ylab.intensive.lesson05.eventsourcing.db",
        "io.ylab.intensive.lesson05.eventsourcing.repository"
})
public class Config {

    @Bean
    public DataSource dataSource() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName("localhost");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");
        dataSource.setDatabaseName("postgres");
        dataSource.setPortNumber(5432);

        String ddl = """
                drop table if exists person;
                create table if not exists person (
                person_id bigint primary key,
                first_name varchar,
                last_name varchar,
                middle_name varchar)
                """;

        DbUtil.applyDdl(ddl, dataSource);

        return dataSource;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

}
