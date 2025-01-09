package com.malinouski.multitenant_app.repository;

import com.malinouski.multitenant_app.model.User;
import org.springframework.data.repository.CrudRepository;

public interface SpringDataUserRepository extends CrudRepository<User, Long> {
}
