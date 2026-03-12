package com.example.librarymanagement.service;

import com.example.librarymanagement.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();
    private Long idCounter = 1L;

    public void register(User user) {
        user.setId(idCounter++);
        users.add(user);
    }

    public Optional<User> login(String email, String password) {
        return users.stream()
                .filter(u -> u.getEmail().equals(email)
                        && u.getPassword().equals(password))
                .findFirst();
    }
}
