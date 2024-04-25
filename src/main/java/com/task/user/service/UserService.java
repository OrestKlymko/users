package com.task.user.service;


import com.task.user.dto.CreateUserRequest;
import com.task.user.dto.UserResponse;
import com.task.user.exception.NotFoundException;
import com.task.user.mapper.LocaleDateMapper;
import com.task.user.mapper.ModelMapper;
import com.task.user.model.User;
import com.task.user.repository.UserRepository;
import jakarta.validation.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<UserResponse> getAllUsers() {
        return repository.findAll()
                .stream().map(ModelMapper::toUserResponse)
                .toList();
    }

    public List<UserResponse> findUsersByDate(LocalDate dateFrom, LocalDate dateTo) {
        if (dateFrom.isAfter(dateTo)) throw new ValidationException("From date should be less than To");
        return repository.findAllByBirthdayGreaterThanAndBirthdayLessThan(dateFrom, dateTo)
                .stream().map(ModelMapper::toUserResponse)
                .toList();
    }

    public void createUser(CreateUserRequest user) {
        User newUser = ModelMapper.toUser(user);
        repository.save(newUser);
    }

    public void deleteUser(long id) {
        repository.deleteById(id);
    }

    public void updateUser(long id, Map<String, Object> fields) throws NotFoundException {
        System.out.println(id);
        repository.findById(id).ifPresentOrElse(
                (user) -> {
                    CreateUserRequest userUpdate = new CreateUserRequest();
                    BeanUtils.copyProperties(user, userUpdate);

                    BeanWrapper beanWrapper = new BeanWrapperImpl(userUpdate);
                    beanWrapper.registerCustomEditor(LocalDate.class, new LocaleDateMapper());
                    fields.forEach(beanWrapper::setPropertyValue);

                    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                    Validator validator = factory.getValidator();
                    Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(userUpdate);
                    if (!violations.isEmpty()) {
                        throw new ValidationException(violations.toString());
                    }

                    BeanUtils.copyProperties(userUpdate, user);
                    repository.save(user);
                },
                () -> {
                    throw new NotFoundException(String.format("User with id %s not found", id));
                });
    }

    public void updateUser(long id, CreateUserRequest request) throws NotFoundException {
        repository.findById(id).ifPresentOrElse(user -> {
            repository.save(ModelMapper.updateUser(user.getId(), request));
        }, () -> {
            throw new NotFoundException(String.format("User with id %s not found", id));
        });

    }


}
