package com.cse.traineeship.controller;

import com.cse.traineeship.domain.Role;
import com.cse.traineeship.domain.User;
import com.cse.traineeship.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrationControllerTest {

    @Mock UserService userService;
    @InjectMocks RegistrationController ctrl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void showRegistrationFormReturnsRegisterView() {
        String view = ctrl.showRegistrationForm();
        assertEquals("dashboard/register", view);
    }

    @Test
    void registerUserCallsServiceAndRedirects() {
        String view = ctrl.registerUser("u", "p", Role.STUDENT);
        // ελέγχω ότι καλείται το userService.register
        verify(userService).register(argThat((User u) ->
                u.getUsername().equals("u") &&
                        u.getPassword().equals("p") &&
                        u.getRole() == Role.STUDENT
        ));
        assertEquals("redirect:/login?registered", view);
    }
}
