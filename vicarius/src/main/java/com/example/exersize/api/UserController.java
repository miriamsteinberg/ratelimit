package com.example.exersize.api;

import com.example.exersize.dao.UserService;
import com.example.exersize.model.User;
import com.example.exersize.service.RateLimit;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


// status code!!!
@RestController("user")
public class UserController {

    private final UserService userService;

    private final RateLimit rateLimit;

    public UserController(UserService userService, RateLimit rateLimit) {
        this.userService = userService;
        this.rateLimit = rateLimit;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody com.example.exersize.model.User user) {
        if(rateLimit.shouldRequest(user.getId())) {
            userService.createUser(user);
        }
    }


    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        if(rateLimit.shouldRequest(id)) {
            return userService.getUser(id);
        }
        return null;
    }


    @PutMapping("/{id}")
    public void updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        if(rateLimit.shouldRequest(id)) {
            userService.updateUser(id, updatedUser);
        }
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String id) {
        if(rateLimit.shouldRequest(id)) {
            userService.deleteUser(id);
        }
    }

}
