package com.cse.traineeship.service.impl;

import com.cse.traineeship.domain.CompanyEvaluation;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.repository.CompanyEvaluationRepository;
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
class CompanyEvaluationServiceImplTest {

    @Mock CompanyEvaluationRepository repo;
    @Mock PositionService positionService;
    @InjectMocks CompanyEvaluationServiceImpl service;

    private TraineeshipPosition pos;

    @BeforeEach
    void setUp() {
        pos = new TraineeshipPosition();
        pos.setId(7L);
    }

    @Test
    void findByPositionId_returnsEvalOrNull() {
        CompanyEvaluation eval = new CompanyEvaluation();
        when(repo.findByPositionId(7L)).thenReturn(Optional.of(eval));
        assertSame(eval, service.findByPositionId(7L));

        when(repo.findByPositionId(8L)).thenReturn(Optional.empty());
        assertNull(service.findByPositionId(8L));
    }


}
