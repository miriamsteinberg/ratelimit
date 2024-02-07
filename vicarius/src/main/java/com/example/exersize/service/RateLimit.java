package com.example.exersize.service;

import com.example.exersize.model.UserRequest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RateLimit {

    private final HashOperations<String, String,List<UserRequest>> hashOperations;

    public RateLimit(HashOperations<String, String, List<UserRequest>> hashOperations) {
        this.hashOperations = hashOperations;
    }


    //cache evict - by the value
    // if impl - no need to ask about the 60 seconds - condition redundant

    public synchronized boolean shouldRequest(String userId) { // TODO synchronized
        //transactional

        List<UserRequest> userRequests = hashOperations.get("users", userId);
        if (userRequests == null) {
            userRequests = List.of(new UserRequest(userId));
            hashOperations.put("users", userId, userRequests);
            return true;
        }
        if (userRequests.size() < 5) {
            userRequests.add(new UserRequest(userId));
            hashOperations.put("users", userId, userRequests);
            return true;
        } else {
            UserRequest firstRequest = userRequests.getFirst();
            if (firstRequest.getRequestTimeUtc().isBefore(LocalDateTime.now().minusSeconds(60))) {// TODO - config val
                userRequests.removeFirst();
                userRequests.add(new UserRequest(userId));
                hashOperations.put("users", userId, userRequests);
                return true;
            } else {
                return false;
            }
        }
    }


    public synchronized boolean allowRequest(List<UserRequest> userRequests) { // TODO synchronized
        if (userRequests == null || userRequests.size() < 5) {
            return true;
        } else {
            UserRequest firstRequest = userRequests.getFirst();
            // TODO - config val
            return firstRequest.getRequestTimeUtc().isBefore(LocalDateTime.now().minusSeconds(60));
        }
    }

}