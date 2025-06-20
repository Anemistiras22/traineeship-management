package com.cse.traineeship.service.strategy;

import com.cse.traineeship.domain.Student;
import com.cse.traineeship.domain.TraineeshipPosition;

import java.util.List;

public class CombinedStrategy implements PositionSearchStrategy {

    private final InterestBasedStrategy interestStrategy;
    private final LocationBasedStrategy locationStrategy;

    public CombinedStrategy(double threshold) {
        this.interestStrategy = new InterestBasedStrategy(threshold);
        this.locationStrategy = new LocationBasedStrategy();
    }

    @Override
    public List<TraineeshipPosition> findMatching(Student student, List<TraineeshipPosition> positions) {

        List<TraineeshipPosition> byInterest = interestStrategy.findMatching(student, positions);
        return locationStrategy.findMatching(student, byInterest);
    }
}
