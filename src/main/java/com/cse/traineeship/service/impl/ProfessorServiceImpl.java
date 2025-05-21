package com.cse.traineeship.service.impl;

import com.cse.traineeship.domain.Professor;
import com.cse.traineeship.repository.ProfessorRepository;
import com.cse.traineeship.service.ProfessorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository repo;

    public ProfessorServiceImpl(ProfessorRepository repo) {
        this.repo = repo;
    }

    @Override
    public Professor findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Professor not found: " + id));
    }

    @Override
    public Professor findByUsername(String username) {
        return repo.findByUsername(username).orElse(null);
    }

    @Override
    public Professor createProfile(String username, String fullName, List<String> interests) {
        Professor p = new Professor();
        p.setUsername(username);
        p.setFullName(fullName);
        p.setInterests(interests);
        return repo.save(p);
    }

    @Override
    public void updateProfile(Long id, String fullName, List<String> interests) {
        Professor p = findById(id);
        p.setFullName(fullName);
        p.setInterests(interests);
        repo.save(p);
    }

    @Override
    public List<Professor> findAll() {
        return repo.findAll();
    }
}
