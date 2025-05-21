package com.cse.traineeship.service.strategy;

import com.cse.traineeship.domain.Company;
import com.cse.traineeship.domain.Student;
import com.cse.traineeship.domain.TraineeshipPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CombinedStrategyTest {

    private CombinedStrategy strat;
    private Student student;
    private TraineeshipPosition bothMatch, onlyInterest, onlyLocation;

    @BeforeEach
    void setUp() {
        strat = new CombinedStrategy(0.5);

        student = new Student();
        student.setInterests(List.of("Java", "Spring"));
        student.setPreferredLocation("Athens");

        // θέση που καλύπτει και interest (Java) και location (Athens)
        bothMatch = new TraineeshipPosition();
        bothMatch.setTopics(List.of("Java"));
        Company c1 = new Company(); c1.setLocation("Athens");
        bothMatch.setCompany(c1);
        bothMatch.setAssignedStudent(null);

        // μόνο interest
        onlyInterest = new TraineeshipPosition();
        onlyInterest.setTopics(List.of("Java"));
        Company c2 = new Company(); c2.setLocation("Thessaloniki");
        onlyInterest.setCompany(c2);
        onlyInterest.setAssignedStudent(null);

        // μόνο location
        onlyLocation = new TraineeshipPosition();
        onlyLocation.setTopics(List.of("Python"));
        Company c3 = new Company(); c3.setLocation("Athens");
        onlyLocation.setCompany(c3);
        onlyLocation.setAssignedStudent(null);
    }

    @Test
    void findsOnlyPositionsMatchingBothInterestAndLocation() {
        List<TraineeshipPosition> input = List.of(bothMatch, onlyInterest, onlyLocation);
        List<TraineeshipPosition> out = strat.findMatching(student, input);

        // αφού CombinedStrategy κάνει intersection, περιμένουμε μόνο το bothMatch
        assertThat(out)
                .hasSize(1)
                .containsExactly(bothMatch);
    }
}
