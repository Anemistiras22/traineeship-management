package com.cse.traineeship.service;

import com.cse.traineeship.domain.CompanyEvaluation;

public interface CompanyEvaluationService {

    /** Επιστρέφει την υπάρχουσα αξιολόγηση ή null */
    CompanyEvaluation findByPositionId(Long positionId);

    /** Δημιουργεί ή ενημερώνει αξιολόγηση */
    CompanyEvaluation saveOrUpdate(Long positionId,
                                   int motivation,
                                   int effectiveness,
                                   int efficiency);
}
