package com.cse.traineeship.service;

import com.cse.traineeship.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(User user);
}
