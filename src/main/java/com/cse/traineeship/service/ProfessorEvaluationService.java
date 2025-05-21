package com.cse.traineeship.service;

import com.cse.traineeship.domain.ProfessorEvaluation;

public interface ProfessorEvaluationService {

    ProfessorEvaluation findByPositionId(Long positionId);

    ProfessorEvaluation saveOrUpdate(Long positionId,
                                     int motivation,
                                     int effectiveness,
                                     int efficiency,
                                     int facilities,
                                     int guidance);
}
