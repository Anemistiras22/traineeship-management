package com.cse.traineeship.service.impl;

import com.cse.traineeship.domain.User;
import com.cse.traineeship.domain.Role;
import com.cse.traineeship.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock UserRepository repo;
    @Mock PasswordEncoder encoder;
    @InjectMocks com.cse.traineeship.service.UserServiceImpl service;

    @Test
    void register_encodesAndSaves() {
        User input = new User("alice","rawpw", Role.STUDENT);
        when(encoder.encode("rawpw")).thenReturn("ENCODED");
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        User out = service.register(input);

        verify(encoder).encode("rawpw");
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(repo).save(captor.capture());
        User saved = captor.getValue();
        assertEquals("ENCODED", saved.getPassword());
        assertSame(saved, out);
    }

    @Test
    void loadUserByUsername_found() {
        User u = new User("bob","pw", Role.COMPANY);
        when(repo.findByUsername("bob")).thenReturn(Optional.of(u));
        User out = service.loadUserByUsername("bob");
        assertSame(u, out);
    }

    @Test
    void loadUserByUsername_notFound_throws() {
        when(repo.findByUsername("nope")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("nope"));
    }
}
