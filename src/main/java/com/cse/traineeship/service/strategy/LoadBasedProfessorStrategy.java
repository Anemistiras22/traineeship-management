package com.cse.traineeship.service.strategy;

import com.cse.traineeship.domain.Professor;
import com.cse.traineeship.domain.TraineeshipPosition;

import java.util.List;

public class LoadBasedProfessorStrategy implements SupervisorAssignmentStrategy {

    @Override
    public Professor selectSupervisor(TraineeshipPosition position, List<Professor> professors) {
        Professor minProf = null;
        int minLoad = Integer.MAX_VALUE;

        for (Professor prof : professors) {
            int load = prof.getSupervisedPositions().size();
            if (load < minLoad) {
                minLoad = load;
                minProf = prof;
            }
        }

        return minProf;
    }
}
