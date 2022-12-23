package com.questApp.questapp.service;

import com.questApp.questapp.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User createUser(User userRequest);

    User getOneUser(Long userId);

    User updateUser(Long userId, User userRequest);

    void deleteUser(Long userId);
}
