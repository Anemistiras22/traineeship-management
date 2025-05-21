package com.cse.traineeship.controller;

import com.cse.traineeship.domain.Role;
import com.cse.traineeship.domain.User;
import com.cse.traineeship.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "dashboard/register";    // templates/register.html
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Role role
    ) {
        User user = new User(username, password, role);
        userService.register(user);
        return "redirect:/login?registered";
    }
}
