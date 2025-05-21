package com.cse.traineeship.service.strategy;

import com.cse.traineeship.domain.Company;
import com.cse.traineeship.domain.Student;
import com.cse.traineeship.domain.TraineeshipPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LocationBasedStrategyTest {

    private LocationBasedStrategy strat;
    private Student student;
    private TraineeshipPosition inLoc, outLoc;

    @BeforeEach
    void setUp() {
        strat = new LocationBasedStrategy();

        student = new Student();
        student.setPreferredLocation("Athens");

        inLoc = new TraineeshipPosition();
        Company c1 = new Company();
        c1.setLocation("Athens");
        inLoc.setCompany(c1);
        inLoc.setAssignedStudent(null);

        outLoc = new TraineeshipPosition();
        Company c2 = new Company();
        c2.setLocation("Thessaloniki");
        outLoc.setCompany(c2);
        outLoc.setAssignedStudent(null);
    }

    @Test
    void findsOnlyPositionsInStudentPreferredLocation() {
        var input = List.of(inLoc, outLoc);
        var out = strat.findMatching(student, input);

        assertThat(out)
                .hasSize(1)
                .containsExactly(inLoc);
    }
}
