package com.task.user.mapper;

import com.task.user.dto.CreateUserRequest;
import com.task.user.dto.UserResponse;
import com.task.user.model.User;

public class ModelMapper {

    public static User toUser(CreateUserRequest request) {
        return User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .address(request.getAddress())
                .birthday(request.getBirthday())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

    public static User updateUser(long id,CreateUserRequest request) {
        return User.builder()
                .id(id)
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .address(request.getAddress())
                .birthday(request.getBirthday())
                .phoneNumber(request.getPhoneNumber())
                .build();
    }

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthday(),
                user.getAddress(),
                user.getPhoneNumber()
        );
    }
}
