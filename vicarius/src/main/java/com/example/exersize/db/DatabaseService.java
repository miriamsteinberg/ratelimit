package com.example.exersize.db;

import com.example.exersize.model.User;

import java.io.IOException;

public interface DatabaseService {

    void createUser(User user) throws IOException;

    User getUser(String id) throws IOException;

    String updateUser(String id, User value) throws IOException;

    void deleteUser(String id) throws IOException;
}
