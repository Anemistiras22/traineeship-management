package com.cse.traineeship.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Map;

@Configuration
public class TestViewConfig {

    @Bean
    public ViewResolver stubViewResolver() {
        return new ViewResolver() {
            @Override
            public View resolveViewName(String viewName, Locale locale) {
                return new View() {
                    @Override
                    public String getContentType() {
                        return "text/html";
                    }

                    @Override
                    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) {
                        // nothing:   rendering
                    }
                };
            }
        };
    }
}
