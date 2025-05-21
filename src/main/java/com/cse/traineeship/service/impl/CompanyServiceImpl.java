package com.cse.traineeship.service.impl;

import com.cse.traineeship.domain.Company;
import com.cse.traineeship.repository.CompanyRepository;
import com.cse.traineeship.service.CompanyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository repo;

    public CompanyServiceImpl(CompanyRepository repo) {
        this.repo = repo;
    }

    @Override
    public Company findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Company not found: " + id));
    }

    @Override
    public Company findByUsername(String username) {
        return repo.findByUsername(username).orElse(null);
    }

    @Override
    public Company createProfile(String username, String name, String location) {
        Company c = new Company();
        c.setUsername(username);
        c.setName(name);
        c.setLocation(location);
        return repo.save(c);
    }

    @Override
    public void updateProfile(Long id, String name, String location) {
        Company c = findById(id);
        c.setName(name);
        c.setLocation(location);
        repo.save(c);
    }

    @Override
    public List<Company> findAll() {
        return repo.findAll();
    }
}
