package com.example.exersize.dao;


import com.example.exersize.db.DatabaseService;
import com.example.exersize.db.DatabaseServiceFactory;
import com.example.exersize.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final DatabaseService databaseService;

    @Autowired
    public UserService(DatabaseServiceFactory databaseServiceFactory) {
        try {
            this.databaseService = databaseServiceFactory.getObject();
        } catch (Exception e) {
            logger.error("Error creating database service", e);
            throw new RuntimeException(e);
        }
    }


    public void createUser(User user) {
        try {
            databaseService.createUser(user);
        } catch (IOException e) {
            logger.error("Error creating user", e);
        }
    }

    public User getUser(String userId) {
        try {
            return databaseService.getUser(userId);
        } catch (IOException e) {
            logger.error("Error getting user", e);
        }
        return null;
    }

    public void updateUser(String id, User updatedUser) {
        try {
            databaseService.updateUser(id, updatedUser);
        } catch (IOException e) {
            logger.error("Error updating user", e);
        }
    }

    public void deleteUser(String id) {
        try {
            databaseService.deleteUser(id);
        } catch (IOException e) {
            logger.error("Error deleting user", e);
        }
    }
}
