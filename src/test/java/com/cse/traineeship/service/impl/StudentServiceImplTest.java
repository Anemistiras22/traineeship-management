package com.cse.traineeship.service.impl;

import com.cse.traineeship.domain.Student;
import com.cse.traineeship.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class StudentServiceImplTest {

    @Mock StudentRepository studentRepository;
    @InjectMocks StudentServiceImpl studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(5L);
        student.setFullName("Nikos");
        student.setUniversityId("U123");
        student.setPreferredLocation("Thessaloniki");
        student.setInterests(List.of("Java", "Spring"));
        student.setSkills(List.of("Git", "SQL"));
    }

    @Test
    void findAll_delegatesToRepository() {
        when(studentRepository.findAll()).thenReturn(List.of(student));
        List<Student> result = studentService.findAll();
        assertEquals(1, result.size());
        assertSame(student, result.get(0));
        verify(studentRepository).findAll();
    }

    @Test
    void findById_existing_returnsStudent() {
        when(studentRepository.findById(5L)).thenReturn(Optional.of(student));
        Student result = studentService.findById(5L);
        assertSame(student, result);
        verify(studentRepository).findById(5L);
    }

    @Test
    void findById_notFound_throws() {
        when(studentRepository.findById(5L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> studentService.findById(5L));
    }

    @Test
    void updateProfile_modifiesAndSaves() {
        when(studentRepository.findById(5L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenAnswer(i -> i.getArgument(0));

        studentService.updateProfile(
                5L,
                "Nikolaos",
                "U999",
                "Athens",
                List.of("Python"),
                List.of("Docker")
        );

        //  student
        assertEquals("Nikolaos", student.getFullName());
        assertEquals("U999", student.getUniversityId());
        assertEquals("Athens", student.getPreferredLocation());
        assertEquals(List.of("Python"), student.getInterests());
        assertEquals(List.of("Docker"), student.getSkills());
        verify(studentRepository).save(student);
    }


}
