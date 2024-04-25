package com.task.user.dto;

import com.task.user.validator.annotation.MinAge;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateUserRequest {
    @Valid

    @NotBlank(message = "email should not be empty")
    @NotNull(message = "email should not be empty")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    private String email;
    @NotBlank(message = "firstName should not be empty")
    @NotNull(message = "firstName should not be empty")
    private String firstName;
    @NotBlank(message = "lastName should not be empty")
    @NotNull(message = "lastName should not be empty")
    private String lastName;
    @NotNull(message = "birthday should not be empty")
    @MinAge
    @Past
    private LocalDate birthday;
    private String address;
    private String phoneNumber;
};
