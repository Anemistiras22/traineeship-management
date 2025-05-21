package com.cse.traineeship.repository;

import com.cse.traineeship.domain.TraineeshipPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<TraineeshipPosition, Long> {

    /**
     * Βρίσκει τη θέση που έχει ανατεθεί σε έναν φοιτητή (μοναδική)
     */
    Optional<TraineeshipPosition> findByAssignedStudentId(Long studentId);

    /**
     * Βρίσκει όλες τις θέσεις που έχουν ανατεθεί σε έναν φοιτητή
     */
    List<TraineeshipPosition> findAllByAssignedStudentId(Long studentId);
}
