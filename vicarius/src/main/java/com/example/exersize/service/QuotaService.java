package com.example.exersize.service;


import com.example.exersize.model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//interface for all one of them
@Service
public class QuotaService {

    private final RateLimit rateLimiter;
    private final HashOperations<String, String,List<UserRequest>> hashOperations;


    @Autowired
    public QuotaService(RateLimit rateLimiter, HashOperations<String, String, List<UserRequest>> hashOperations) {
        this.rateLimiter = rateLimiter;
        this.hashOperations = hashOperations;
    }


    public String consumeQuota(String userId) {
        List<UserRequest> userRequests = hashOperations.get("users", userId);
        if (rateLimiter.allowRequest(userRequests)) {
            return "Quota consumed for user: " + userId;
        } else {
            return "User is blocked due to quota limit";
        }
    }

    public Map<String, String> getUsersQuota()  {
        Map<String, String> quotaStatus = new HashMap<>();
        Map<String, List<UserRequest>> users = hashOperations.entries("users");
        for (Map.Entry<String, List<UserRequest>> user : users.entrySet()){
            if (rateLimiter.allowRequest(user.getValue())) {
                quotaStatus.put(user.getKey(), "Quota available");
            } else {
                quotaStatus.put(user.getKey(), "Blocked due to quota limit");
            }
        }
        return quotaStatus;
    }
}
