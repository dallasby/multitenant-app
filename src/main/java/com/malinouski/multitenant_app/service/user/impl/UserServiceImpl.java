package com.malinouski.multitenant_app.service.user.impl;

import com.malinouski.multitenant_app.dto.UserRequest;
import com.malinouski.multitenant_app.dto.UserResponse;
import com.malinouski.multitenant_app.repository.UserRepositoryPort;
import com.malinouski.multitenant_app.service.user.UserService;
import com.malinouski.multitenant_app.util.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepositoryPort userRepositoryPort;
    private final UserMapper userMapper;

    @Override
    public List<UserResponse> getAllUsers() {
        log.info("Getting all users");
        List<UserResponse> userResponses = new ArrayList<>();
        userRepositoryPort.findAll().forEach(user -> userResponses.add(userMapper.toUserResponse(user)));
        return userResponses;
    }

    @Override
    public UserResponse getUserById(Long id) {
        log.info("Get user by id: {}", id);
        return userRepositoryPort.findById(id)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        log.info("Create user: {}", request);
        var user = userMapper.toUser(request);
        var savedUser = userRepositoryPort.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        log.info("Update user: {}", request);
        var user = userRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        user.setName(request.name());
        user.setEmail(request.email());
        var updatedUser = userRepositoryPort.save(user);
        return userMapper.toUserResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        var user = userRepositoryPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        userRepositoryPort.delete(user);
    }
}
