package com.cse.traineeship.service.strategy;

import com.cse.traineeship.domain.Student;
import com.cse.traineeship.domain.TraineeshipPosition;

import java.util.List;
import java.util.stream.Collectors;

public class LocationBasedStrategy implements PositionSearchStrategy {

    @Override
    public List<TraineeshipPosition> findMatching(Student student, List<TraineeshipPosition> positions) {
        String preferred = student.getPreferredLocation();
        if (preferred == null || preferred.isBlank()) {
            return List.of();
        }
        return positions.stream()
                .filter(p -> p.getCompany().getLocation().equalsIgnoreCase(preferred))
                .collect(Collectors.toList());
    }
}
