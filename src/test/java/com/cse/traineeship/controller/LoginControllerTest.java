package com.cse.traineeship.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import static org.junit.jupiter.api.Assertions.*;

class LoginControllerTest {

    @Test
    void loginReturnsLoginView() {
        LoginController ctrl = new LoginController();
        String view = ctrl.login();
        assertEquals("dashboard/login", view);
    }
}
