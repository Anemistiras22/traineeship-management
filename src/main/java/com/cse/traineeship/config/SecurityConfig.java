package com.cse.traineeship.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // public
                        .requestMatchers("/", "/login", "/register", "/css/**", "/js/**")
                        .permitAll()
                        // φοιτητής
                        .requestMatchers("/positions/apply/**", "/logbook/**", "/search/**", "/students/me/**")
                        .hasRole("STUDENT")
                        // εταιρία
                        .requestMatchers("/company/**", "/positions/*/company-evaluation/**")
                        .hasRole("COMPANY")
                        // καθηγητής
                        .requestMatchers("/professors/**", "/professors/me/**")
                        .hasRole("PROFESSOR")
                        // επιτροπή
                        .requestMatchers("/committee/**")
                        .hasRole("COMMITTEE")
                        // όλα τα υπόλοιπα
                        .anyRequest()
                        .authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access-denied")
                )
                .authenticationProvider(authenticationProvider())
        ;
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }
}
