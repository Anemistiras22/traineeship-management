package com.cse.traineeship.service.strategy;

import com.cse.traineeship.domain.Student;
import com.cse.traineeship.domain.TraineeshipPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class InterestBasedStrategyTest {

    private InterestBasedStrategy strat;
    private Student student;
    private TraineeshipPosition match, nonMatch;

    @BeforeEach
    void setUp() {
        // threshold στο constructor
        strat = new InterestBasedStrategy(0.5);

        // φοιτητής με δύο ενδιαφέροντα
        student = new Student();
        student.setInterests(List.of("Java", "Spring"));

        // θέση με μόνον "Java" -> similarity = 1/2 = 0.5 => should match
        match = new TraineeshipPosition();
        match.setTopics(List.of("Java"));
        match.setAssignedStudent(null);

        // θέση με μόνον "Python" -> similarity = 0/3 = 0 => δεν matchάρει
        nonMatch = new TraineeshipPosition();
        nonMatch.setTopics(List.of("Python"));
        nonMatch.setAssignedStudent(null);
    }

    @Test
    void findsOnlyPositionsWithEnoughInterestOverlap() {
        var input = List.of(match, nonMatch);
        var out = strat.findMatching(student, input);

        assertThat(out)
                .hasSize(1)
                .containsExactly(match);
    }
}
