package com.malinouski.multitenant_app.repository;

import com.malinouski.multitenant_app.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User user);

    void delete(User user);
}
