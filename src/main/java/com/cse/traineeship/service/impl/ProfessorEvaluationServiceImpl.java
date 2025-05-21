package com.cse.traineeship.service.impl;

import com.cse.traineeship.domain.ProfessorEvaluation;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.repository.ProfessorEvaluationRepository;
import com.cse.traineeship.service.ProfessorEvaluationService;
import com.cse.traineeship.service.PositionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProfessorEvaluationServiceImpl implements ProfessorEvaluationService {

    private final ProfessorEvaluationRepository repo;
    private final PositionService positionService;

    public ProfessorEvaluationServiceImpl(ProfessorEvaluationRepository repo,
                                          PositionService positionService) {
        this.repo = repo;
        this.positionService = positionService;
    }

    @Override
    public ProfessorEvaluation findByPositionId(Long positionId) {
        return repo.findByPositionId(positionId).orElse(null);
    }

    @Override
    public ProfessorEvaluation saveOrUpdate(Long positionId,
                                            int motivation,
                                            int effectiveness,
                                            int efficiency,
                                            int facilities,
                                            int guidance) {
        TraineeshipPosition pos = positionService.findById(positionId);
        ProfessorEvaluation eval = repo.findByPositionId(positionId)
                .orElseGet(() -> {
                    ProfessorEvaluation ne = new ProfessorEvaluation();
                    ne.setPosition(pos);
                    return ne;
                });
        eval.setMotivation(motivation);
        eval.setEffectiveness(effectiveness);
        eval.setEfficiency(efficiency);
        eval.setFacilities(facilities);
        eval.setGuidance(guidance);
        return repo.save(eval);
    }
}
