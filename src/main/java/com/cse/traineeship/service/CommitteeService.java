package com.cse.traineeship.service;

import com.cse.traineeship.domain.FinalResult;
import com.cse.traineeship.domain.Professor;
import com.cse.traineeship.domain.Student;
import com.cse.traineeship.domain.TraineeshipPosition;

import java.util.List;

public interface CommitteeService {

    List<Student> getAllApplicants();

    List<TraineeshipPosition> searchPositions(Long studentId, String strategy);

    void assignPosition(Long positionId, Long studentId);

    Professor assignSupervisor(Long positionId, String strategy);

    List<TraineeshipPosition> getInProgressPositions();

    void finalizePosition(Long positionId, FinalResult result);

    List<TraineeshipPosition> getCompletedPositions();
}
