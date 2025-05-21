package com.cse.traineeship.repository;

import com.cse.traineeship.domain.CompanyEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyEvaluationRepository extends JpaRepository<CompanyEvaluation, Long> {
    /** Βρίσκει την αξιολόγηση για μια θέση, αν υπάρχει */
    Optional<CompanyEvaluation> findByPositionId(Long positionId);
}
