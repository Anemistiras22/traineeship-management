package com.cse.traineeship.service.impl;

import com.cse.traineeship.domain.CompanyEvaluation;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.repository.CompanyEvaluationRepository;
import com.cse.traineeship.service.CompanyEvaluationService;
import com.cse.traineeship.service.PositionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CompanyEvaluationServiceImpl implements CompanyEvaluationService {

    private final CompanyEvaluationRepository repo;
    private final PositionService positionService;

    public CompanyEvaluationServiceImpl(CompanyEvaluationRepository repo,
                                        PositionService positionService) {
        this.repo = repo;
        this.positionService = positionService;
    }

    @Override
    public CompanyEvaluation findByPositionId(Long positionId) {
        return repo.findByPositionId(positionId).orElse(null);
    }

    @Override
    public CompanyEvaluation saveOrUpdate(Long positionId,
                                          int motivation,
                                          int effectiveness,
                                          int efficiency) {
        TraineeshipPosition pos = positionService.findById(positionId);
        CompanyEvaluation eval = repo.findByPositionId(positionId)
                .orElseGet(() -> {
                    CompanyEvaluation ne = new CompanyEvaluation();
                    ne.setPosition(pos);
                    return ne;
                });
        eval.setMotivation(motivation);
        eval.setEffectiveness(effectiveness);
        eval.setEfficiency(efficiency);
        return repo.save(eval);
    }
}
