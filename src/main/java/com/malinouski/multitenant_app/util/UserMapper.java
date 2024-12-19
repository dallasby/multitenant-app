package com.malinouski.multitenant_app.util;

import com.malinouski.multitenant_app.dto.UserRequest;
import com.malinouski.multitenant_app.dto.UserResponse;
import com.malinouski.multitenant_app.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public User toUser(UserRequest request) {
        return User.builder()
                .name(request.name())
                .email(request.email())
                .build();
    }

    public UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
