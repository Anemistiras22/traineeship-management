package com.cse.traineeship.repository;

import com.cse.traineeship.domain.ProfessorEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorEvaluationRepository extends JpaRepository<ProfessorEvaluation, Long> {
    Optional<ProfessorEvaluation> findByPositionId(Long positionId);
}
