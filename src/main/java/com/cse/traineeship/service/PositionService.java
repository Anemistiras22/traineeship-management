package com.cse.traineeship.service;

import com.cse.traineeship.domain.TraineeshipPosition;

import java.util.List;

public interface PositionService {
    List<TraineeshipPosition> findAll();
    TraineeshipPosition findById(Long id);
    TraineeshipPosition save(TraineeshipPosition position);
    void deleteById(Long id);
    void apply(Long positionId, Long studentId);

    /** παλιά: μοναδική θέση */
    TraineeshipPosition findByStudentId(Long studentId);

    /** Νέα: όλες οι θέσεις ενός φοιτητή */
    List<TraineeshipPosition> findAllByStudentId(Long studentId);
}
