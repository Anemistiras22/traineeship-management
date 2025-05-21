package com.cse.traineeship.service.strategy;

import com.cse.traineeship.domain.Professor;
import com.cse.traineeship.domain.TraineeshipPosition;

import java.util.List;

public interface SupervisorAssignmentStrategy {

    /**
     * Επιλέγει τον κατάλληλο καθηγητή για επιβλέποντα μιας θέσης.
     *
     * @param position   η θέση προς ανάθεση
     * @param professors λίστα διαθέσιμων καθηγητών
     * @return ο καθηγητής που επιλέγεται ή null αν δεν βρέθηκε κατάλληλος
     */
    Professor selectSupervisor(TraineeshipPosition position, List<Professor> professors);
}
