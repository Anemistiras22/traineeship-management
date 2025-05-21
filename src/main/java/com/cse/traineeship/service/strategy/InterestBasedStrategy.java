package com.cse.traineeship.service.strategy;

import com.cse.traineeship.domain.Student;
import com.cse.traineeship.domain.TraineeshipPosition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InterestBasedStrategy implements PositionSearchStrategy {

    private final double threshold;

    public InterestBasedStrategy(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public List<TraineeshipPosition> findMatching(Student student, List<TraineeshipPosition> positions) {
        List<TraineeshipPosition> result = new ArrayList<>();
        Set<String> interests = new HashSet<>(student.getInterests());
        for (TraineeshipPosition pos : positions) {
            Set<String> topics = new HashSet<>(pos.getTopics());
            // Jaccard = |I ∩ T| / |I ∪ T|
            Set<String> intersection = new HashSet<>(interests);
            intersection.retainAll(topics);
            if (intersection.isEmpty()) continue;
            Set<String> union = new HashSet<>(interests);
            union.addAll(topics);
            double jaccard = (double) intersection.size() / union.size();
            if (jaccard >= threshold) {
                result.add(pos);
            }
        }
        return result;
    }
}
