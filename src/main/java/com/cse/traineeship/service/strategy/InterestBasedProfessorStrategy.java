package com.cse.traineeship.service.strategy;

import com.cse.traineeship.domain.Professor;
import com.cse.traineeship.domain.TraineeshipPosition;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InterestBasedProfessorStrategy implements SupervisorAssignmentStrategy {

    private final double threshold;

    /**
     * @param threshold η ελάχιστη Jaccard similarity (π.χ. 0.5)
     */
    public InterestBasedProfessorStrategy(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public Professor selectSupervisor(TraineeshipPosition position, List<Professor> professors) {
        Set<String> topics = new HashSet<>(position.getTopics());
        Professor bestProf = null;
        double bestSim = 0.0;

        for (Professor prof : professors) {
            Set<String> interests = new HashSet<>(prof.getInterests());
            Set<String> intersection = new HashSet<>(interests);
            intersection.retainAll(topics);
            if (intersection.isEmpty()) continue;

            Set<String> union = new HashSet<>(interests);
            union.addAll(topics);
            double sim = (double) intersection.size() / union.size();

            if (sim > bestSim) {
                bestSim = sim;
                bestProf = prof;
            }
        }

        return (bestProf != null && bestSim >= threshold) ? bestProf : null;
    }
}
