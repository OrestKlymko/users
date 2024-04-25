package com.task.user.repository;

import com.task.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByBirthdayGreaterThanAndBirthdayLessThan(LocalDate fromDate, LocalDate toDate);
}
