package com.cse.traineeship.service.impl;

import com.cse.traineeship.domain.Student;
import com.cse.traineeship.repository.StudentRepository;
import com.cse.traineeship.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepo;

    public StudentServiceImpl(StudentRepository studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public Student findById(Long id) {
        return studentRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student not found: " + id));
    }

    @Override
    public Student findByUsername(String username) {
        return studentRepo.findByUsername(username).orElse(null);
    }

    @Override
    public Student createProfile(String username,
                                 String fullName,
                                 String universityId,
                                 String preferredLocation,
                                 List<String> interests,
                                 List<String> skills) {
        Student student = new Student();
        student.setUsername(username);
        student.setFullName(fullName);
        student.setUniversityId(universityId);
        student.setPreferredLocation(preferredLocation);
        student.setInterests(interests);
        student.setSkills(skills);
        return studentRepo.save(student);
    }

    @Override
    public void updateProfile(Long id,
                              String fullName,
                              String universityId,
                              String preferredLocation,
                              List<String> interests,
                              List<String> skills) {
        Student student = findById(id);
        student.setFullName(fullName);
        student.setUniversityId(universityId);
        student.setPreferredLocation(preferredLocation);
        student.setInterests(interests);
        student.setSkills(skills);
        studentRepo.save(student);
    }

    @Override
    public List<Student> findAll() {
        return studentRepo.findAll();
    }
}
