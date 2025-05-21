package com.cse.traineeship.service.impl;

import com.cse.traineeship.domain.Application;
import com.cse.traineeship.domain.Student;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.repository.ApplicationRepository;
import com.cse.traineeship.service.PositionService;
import com.cse.traineeship.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceImplTest {

    @Mock ApplicationRepository repo;
    @Mock PositionService positionService;
    @Mock StudentService studentService;
    @InjectMocks ApplicationServiceImpl service;

    private TraineeshipPosition pos;
    private Student stu;

    @BeforeEach
    void setUp() {
        pos = new TraineeshipPosition();
        pos.setId(42L);
        stu = new Student();
        stu.setId(99L);
    }

    @Test
    void apply_newApplication_saves() {
        when(repo.existsByPositionIdAndStudentId(42L, 99L)).thenReturn(false);
        when(positionService.findById(42L)).thenReturn(pos);
        when(studentService.findById(99L)).thenReturn(stu);

        service.apply(42L, 99L);

        ArgumentCaptor<Application> captor = ArgumentCaptor.forClass(Application.class);
        verify(repo).save(captor.capture());
        Application saved = captor.getValue();
        assertSame(pos, saved.getPosition());
        assertSame(stu, saved.getStudent());
    }

    @Test
    void apply_duplicateDoesNothing() {
        when(repo.existsByPositionIdAndStudentId(1L,2L)).thenReturn(true);
        service.apply(1L,2L);
        verify(repo, never()).save(any());
    }


    @Test
    void findByPosition_delegatesToRepo() {
        List<Application> apps = List.of(new Application());
        when(repo.findAllByPositionId(42L)).thenReturn(apps);
        assertSame(apps, service.findByPosition(42L));
    }

    @Test
    void findByStudent_delegatesToRepo() {
        List<Application> apps = List.of(new Application());
        when(repo.findAllByStudentId(99L)).thenReturn(apps);
        assertSame(apps, service.findByStudent(99L));
    }
}
