package com.example.exersize.db;

import com.example.exersize.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends CrudRepository<User, String> {
    // You can define custom query methods here if needed
}
