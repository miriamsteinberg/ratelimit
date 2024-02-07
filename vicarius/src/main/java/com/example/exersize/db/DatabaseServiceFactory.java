package com.example.exersize.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class DatabaseServiceFactory implements FactoryBean<DatabaseService> {

    private final UserRepository userRepository;

    private final RestHighLevelClient client;
    private final ObjectMapper objectMapper;

    public DatabaseServiceFactory(UserRepository userRepository, RestHighLevelClient client, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.client = client;
        this.objectMapper = objectMapper;
    }

    private static boolean isDaytime(LocalTime time) {
        return time.isAfter(LocalTime.of(9, 0)) && time.isBefore(LocalTime.of(17, 0));
    }

    @Override
    public DatabaseService getObject()  {
        LocalTime now = LocalTime.now();
        if (isDaytime(now)) {
            return new MySQLService(userRepository);
        } else {
            return new ElasticService(client, objectMapper);
        }
    }

    @Override
    public Class<?> getObjectType() {
        return DatabaseService.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
