package com.cse.traineeship.service.impl;

import com.cse.traineeship.domain.Professor;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.domain.ProfessorEvaluation;
import com.cse.traineeship.repository.ProfessorRepository;
import com.cse.traineeship.repository.ProfessorEvaluationRepository;
import com.cse.traineeship.service.PositionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class ProfessorServiceImplTest {

    @Mock
    private ProfessorRepository professorRepository;

    @Mock
    private ProfessorEvaluationRepository evaluationRepository;

    @Mock
    private PositionService positionService;

    @InjectMocks
    private ProfessorServiceImpl professorService;

    private Professor prof;
    private TraineeshipPosition position;

    @BeforeEach
    void setUp() {
        prof = new Professor();
        prof.setId(1L);
        prof.setFullName("John Doe");
        prof.setInterests(List.of("Java", "Spring"));

        when(professorRepository.findById(1L))
                .thenReturn(Optional.of(prof));

        position = new TraineeshipPosition();
        position.setId(100L);
    }

    @Test
    void findById_existing() {
        Professor result = professorService.findById(1L);
        assertThat(result).isSameAs(prof);
    }

    @Test
    void findById_notFound() {
        when(professorRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () ->
                professorService.findById(1L)
        );
    }


    @Test
    void updateProfile_updatesFullNameAndInterests() {

        when(professorRepository.findById(1L))
                .thenReturn(Optional.of(prof));

        professorService.updateProfile(
                1L,
                "Jane Smith",
                List.of("Python", "Docker")
        );


        ArgumentCaptor<Professor> captor =
                ArgumentCaptor.forClass(Professor.class);
        verify(professorRepository).save(captor.capture());

        Professor saved = captor.getValue();
        assertEquals(1L, saved.getId());
        assertEquals("Jane Smith", saved.getFullName());
        assertEquals(List.of("Python", "Docker"), saved.getInterests());
    }

}
