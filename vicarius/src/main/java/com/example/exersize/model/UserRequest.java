package com.example.exersize.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserRequest implements Serializable {
    private String userId;
    private LocalDateTime requestTimeUtc;

    public UserRequest(String userId) {
        this.userId = userId;
        this.requestTimeUtc = LocalDateTime.now();
    }
}
