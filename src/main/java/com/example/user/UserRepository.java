package com.example.user;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final static List<User> USERS = new ArrayList<>();

    public User findByEmail(String email) {
        return USERS
            .stream()
            .filter(user -> user.getEmail().equals(email))
            .findFirst()
            .orElse(null);
    }

    public void save(User user) {
        USERS.add(user);
    }
}