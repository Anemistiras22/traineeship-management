package com.cse.traineeship.service.strategy;

import com.cse.traineeship.domain.Student;
import com.cse.traineeship.domain.TraineeshipPosition;

import java.util.List;

public interface PositionSearchStrategy {
    /**
     * Επιστρέφει τη λίστα διαθέσιμων θέσεων που «ταιριάζουν» στον φοιτητή.
     * @param student  ο φοιτητής
     * @param positions όλες οι διαθέσιμες θέσεις
     */
    List<TraineeshipPosition> findMatching(Student student, List<TraineeshipPosition> positions);
}
