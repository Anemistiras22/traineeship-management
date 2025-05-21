package com.cse.traineeship.service;

import com.cse.traineeship.domain.Company;

import java.util.List;

public interface CompanyService {
    Company findById(Long id);
    Company findByUsername(String username);
    Company createProfile(String username, String name, String location);
    void updateProfile(Long id, String name, String location);
    List<Company> findAll();
}
