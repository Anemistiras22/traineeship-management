package com.cse.traineeship.service.strategy;

import com.cse.traineeship.domain.Professor;
import com.cse.traineeship.domain.TraineeshipPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LoadBasedProfessorStrategyTest {

    private LoadBasedProfessorStrategy strat;
    private TraineeshipPosition pos;
    private Professor p1, p2;

    @BeforeEach
    void setUp() {
        strat = new LoadBasedProfessorStrategy();

        // δύο καθηγητές με διαφορετικό φόρτο
        p1 = new Professor();
        p1.setSupervisedPositions(List.of(new TraineeshipPosition(), new TraineeshipPosition())); // load = 2

        p2 = new Professor();
        p2.setSupervisedPositions(List.of(new TraineeshipPosition())); // load = 1

        pos = new TraineeshipPosition();
    }

    @Test
    void picksProfessorWithLeastLoad() {
        var selected = strat.selectSupervisor(pos, List.of(p1, p2));
        assertThat(selected).isEqualTo(p2);
    }
}
