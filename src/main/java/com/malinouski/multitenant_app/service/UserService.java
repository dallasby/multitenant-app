package com.malinouski.multitenant_app.service;

import com.malinouski.multitenant_app.dto.UserRequest;
import com.malinouski.multitenant_app.dto.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse createUser(UserRequest request);

    UserResponse updateUser(Long id, UserRequest request);

    void deleteUser(Long id);
}
