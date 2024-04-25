package com.task.user.controller;


import com.task.user.dto.CreateUserRequest;
import com.task.user.dto.UserResponse;
import com.task.user.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/user")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{dateFrom}/{dateTo}")
    public List<UserResponse> getAllUsers(@PathVariable LocalDate dateFrom,@PathVariable LocalDate dateTo) {
        return userService.findUsersByDate(dateFrom,dateTo);
    }

    @PostMapping("/user")
    public void createUser(@Valid @RequestBody CreateUserRequest user) {
        userService.createUser(user);
    }

    @PutMapping("/user/{userId}")
    public void updateUser(@PathVariable long userId, @Valid @RequestBody CreateUserRequest user) {
        userService.updateUser(userId, user);
    }

    @PatchMapping("/user/{userId}")
    public void updateUser(@PathVariable long userId, @RequestBody Map<String, Object> fields) {
        userService.updateUser(userId, fields);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }






    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
