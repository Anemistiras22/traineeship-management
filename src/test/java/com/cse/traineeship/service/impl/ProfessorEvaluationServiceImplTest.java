package com.cse.traineeship.service.impl;

import com.cse.traineeship.domain.ProfessorEvaluation;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.repository.ProfessorEvaluationRepository;
import com.cse.traineeship.service.PositionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfessorEvaluationServiceImplTest {

    @Mock ProfessorEvaluationRepository repo;
    @Mock PositionService positionService;
    @InjectMocks ProfessorEvaluationServiceImpl service;

    private TraineeshipPosition pos;

    @BeforeEach
    void setUp() {
        pos = new TraineeshipPosition();
        pos.setId(13L);
    }

    @Test
    void findByPositionId_returnsEvalOrNull() {
        ProfessorEvaluation eval = new ProfessorEvaluation();
        when(repo.findByPositionId(13L)).thenReturn(Optional.of(eval));
        assertSame(eval, service.findByPositionId(13L));

        when(repo.findByPositionId(14L)).thenReturn(Optional.empty());
        assertNull(service.findByPositionId(14L));
    }


}
