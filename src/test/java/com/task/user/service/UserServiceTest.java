package com.task.user.service;

import com.task.user.dto.CreateUserRequest;
import com.task.user.dto.UserResponse;
import com.task.user.exception.NotFoundException;
import com.task.user.model.User;
import com.task.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllUsers_returnsUserResponses() {
        when(repository.findAll()).thenReturn(List.of(new User(1L, "email@example.com", "John", "Doe", LocalDate.of(1997, 1, 1), "Address", "Phone")));
        List<UserResponse> responses = service.getAllUsers();

        assertEquals(1, responses.size());
        assertEquals("John", responses.get(0).firstName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void createUser_savesNewUser() {
        CreateUserRequest request = new CreateUserRequest("email@example.com", "John", "Doe", LocalDate.now(), "Address", "Phone");
        service.createUser(request);
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    void deleteUser_deletesUserById() {
        doNothing().when(repository).deleteById(anyLong());
        service.deleteUser(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void updateUser_updatesExistingUser() throws NotFoundException {
        when(repository.findById(1L)).thenReturn(Optional.of(new User(1L, "email@example.com", "John", "Doe", LocalDate.of(1994,1,1), "Address", "Phone")));
        service.updateUser(1L, Map.of("firstName", "Jane"));
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_throwsNotFoundException_whenUserNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.updateUser(1L, Map.of("firstName", "Jane")));
    }


    @Test
    void updateUserWithRequest_updatesExistingUser() throws NotFoundException {
        User existingUser = new User(1L, "email@example.com", "John", "Doe", LocalDate.of(1994,1,1), "Address", "Phone");
        when(repository.findById(1L)).thenReturn(Optional.of(existingUser));
        CreateUserRequest request = new CreateUserRequest("newemail@example.com", "Jane", "Doe", LocalDate.of(1994,1,1), "New Address", "New Phone");

        service.updateUser(1L, request);
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    void updateUserWithRequest_throwsNotFoundException_whenUserNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.updateUser(1L, new CreateUserRequest()));
    }

}