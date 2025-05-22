package com.cse.traineeship.service;

import com.cse.traineeship.domain.CompanyEvaluation;

public interface CompanyEvaluationService {

    CompanyEvaluation findByPositionId(Long positionId);

    CompanyEvaluation saveOrUpdate(Long positionId,
                                   int motivation,
                                   int effectiveness,
                                   int efficiency);
}
