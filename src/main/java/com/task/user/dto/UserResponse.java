package com.task.user.dto;

import java.time.LocalDate;

public record UserResponse (
        Long id,
        String email,
        String firstName,
        String lastName,
        LocalDate birthday,
        String address,
        String phoneNumber
){
}
