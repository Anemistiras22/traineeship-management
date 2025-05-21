package com.cse.traineeship.service;

import com.cse.traineeship.domain.Student;

import java.util.List;

public interface StudentService {
    Student findById(Long id);
    Student findByUsername(String username);
    Student createProfile(String username,
                          String fullName,
                          String universityId,
                          String preferredLocation,
                          List<String> interests,
                          List<String> skills);
    void updateProfile(Long id,
                       String fullName,
                       String universityId,
                       String preferredLocation,
                       List<String> interests,
                       List<String> skills);
    List<Student> findAll();
}
