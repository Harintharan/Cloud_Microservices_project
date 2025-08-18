package com.ownproject.userservice.service;

import com.ownproject.userservice.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();

    String deleteUser(Long id);
    User updateUser(Long id, User user);

    User getUserByFromJwt(String jwt) throws Exception;
}
