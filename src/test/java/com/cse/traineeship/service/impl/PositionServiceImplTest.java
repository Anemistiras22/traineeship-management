package com.cse.traineeship.service.impl;

import com.cse.traineeship.domain.Student;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.repository.PositionRepository;
import com.cse.traineeship.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PositionServiceImplTest {

    private PositionRepository repo;
    private StudentService studentService;
    private PositionServiceImpl service;

    @BeforeEach
    void setUp() {
        repo = mock(PositionRepository.class);
        studentService = mock(StudentService.class);
        service = new PositionServiceImpl(repo, studentService);
    }

    @Test
    void findAll_shouldReturnAllFromRepo() {
        TraineeshipPosition p1 = new TraineeshipPosition();
        TraineeshipPosition p2 = new TraineeshipPosition();
        when(repo.findAll()).thenReturn(List.of(p1, p2));

        List<TraineeshipPosition> all = service.findAll();
        assertThat(all).containsExactly(p1, p2);
    }

    @Test
    void findById_whenExists_shouldReturnIt() {
        TraineeshipPosition p = new TraineeshipPosition();
        when(repo.findById(42L)).thenReturn(Optional.of(p));

        TraineeshipPosition out = service.findById(42L);
        assertThat(out).isSameAs(p);
    }

    @Test
    void findById_whenMissing_shouldThrow() {
        when(repo.findById(5L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.findById(5L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Position not found");
    }

    @Test
    void save_shouldDelegateToRepo() {
        TraineeshipPosition p = new TraineeshipPosition();
        when(repo.save(p)).thenReturn(p);
        assertThat(service.save(p)).isSameAs(p);
        verify(repo).save(p);
    }

    @Test
    void deleteById_shouldCallRepo() {
        service.deleteById(7L);
        verify(repo).deleteById(7L);
    }

    @Test
    void apply_shouldAssignStudentAndSave() {
        TraineeshipPosition p = new TraineeshipPosition();
        Student s = new Student();
        when(repo.findById(1L)).thenReturn(Optional.of(p));
        when(studentService.findById(99L)).thenReturn(s);

        service.apply(1L, 99L);

        // βεβαιώνουμε ότι στον position μπήκε ο student
        assertThat(p.getAssignedStudent()).isSameAs(s);

        // και ότι σώθηκε
        ArgumentCaptor<TraineeshipPosition> cap = ArgumentCaptor.forClass(TraineeshipPosition.class);
        verify(repo).save(cap.capture());
        assertThat(cap.getValue().getAssignedStudent()).isSameAs(s);
    }
}
