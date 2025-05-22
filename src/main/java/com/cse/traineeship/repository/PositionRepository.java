package com.cse.traineeship.repository;

import com.cse.traineeship.domain.TraineeshipPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<TraineeshipPosition, Long> {


    Optional<TraineeshipPosition> findByAssignedStudentId(Long studentId);


    List<TraineeshipPosition> findAllByAssignedStudentId(Long studentId);
}
