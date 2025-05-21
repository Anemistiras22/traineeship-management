package com.cse.traineeship.service;

import com.cse.traineeship.domain.Professor;

import java.util.List;

public interface ProfessorService {
    Professor findById(Long id);
    Professor findByUsername(String username);
    Professor createProfile(String username, String fullName, List<String> interests);
    void updateProfile(Long id, String fullName, List<String> interests);
    List<Professor> findAll();
}
