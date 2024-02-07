package com.example.exersize.db;

import com.example.exersize.model.User;

public class MySQLService implements DatabaseService {

    private final UserRepository userRepository;

    public MySQLService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUser(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public String updateUser(String id, User value) {
        userRepository.save(value);// clone
        return id;
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
