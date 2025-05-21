package com.cse.traineeship.service.impl;

import com.cse.traineeship.domain.Application;
import com.cse.traineeship.domain.FinalResult;
import com.cse.traineeship.domain.Professor;
import com.cse.traineeship.domain.Student;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.service.CommitteeService;
import com.cse.traineeship.service.PositionService;
import com.cse.traineeship.service.ProfessorService;
import com.cse.traineeship.service.StudentService;
import com.cse.traineeship.service.strategy.CombinedStrategy;
import com.cse.traineeship.service.strategy.InterestBasedStrategy;
import com.cse.traineeship.service.strategy.LocationBasedStrategy;
import com.cse.traineeship.service.strategy.PositionSearchStrategy;
import com.cse.traineeship.service.strategy.InterestBasedProfessorStrategy;
import com.cse.traineeship.service.strategy.LoadBasedProfessorStrategy;
import com.cse.traineeship.service.strategy.SupervisorAssignmentStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommitteeServiceImpl implements CommitteeService {

    private final StudentService studentService;
    private final PositionService positionService;
    private final ProfessorService professorService;

    // threshold για τις στρατηγικές βασισμένες στο Jaccard
    private static final double DEFAULT_THRESHOLD = 0.5;

    public CommitteeServiceImpl(StudentService studentService,
                                PositionService positionService,
                                ProfessorService professorService) {
        this.studentService = studentService;
        this.positionService = positionService;
        this.professorService = professorService;
    }

    /**
     * US16: Επιστρέφει όλους τους φοιτητές που έχουν κάνει αίτηση (distinct).
     */
    @Override
    public List<Student> getAllApplicants() {
        return positionService.findAll().stream()
            // παίρνουμε όλες τις αιτήσεις από κάθε θέση
            .flatMap(pos -> pos.getApplications().stream())
            .map(Application::getStudent)   // τον φοιτητή κάθε αίτησης
            .distinct()                     // χωρίς διπλότυπα
            .collect(Collectors.toList());
    }

    /**
     * US17: Αναζητάμε, μεταξύ των θέσεων στις οποίες ο φοιτητής έχει κάνει αίτηση
     * (και που δεν είναι ήδη κατειλημμένες), τις ταιριαστές με τη στρατηγική.
     */
    @Override
    public List<TraineeshipPosition> searchPositions(Long studentId, String strategy) {
        // φιλτράρουμε τις θέσεις όπου υπάρχει αίτηση από αυτόν τον φοιτητή
        List<TraineeshipPosition> applied = positionService.findAll().stream()
            .filter(p ->
                p.getAssignedStudent() == null &&               // ακόμα ανοιχτή
                p.getApplications().stream()
                 .anyMatch(app -> app.getStudent().getId().equals(studentId))
            )
            .collect(Collectors.toList());

        // επιλέγουμε στρατηγική
        PositionSearchStrategy strat;
        switch (strategy) {
            case "interest":
                strat = new InterestBasedStrategy(DEFAULT_THRESHOLD);
                break;
            case "location":
                strat = new LocationBasedStrategy();
                break;
            case "combined":
            default:
                strat = new CombinedStrategy(DEFAULT_THRESHOLD);
                break;
        }

        // επιστρέφουμε τα αποτελέσματα
        return strat.findMatching(studentService.findById(studentId), applied);
    }

    /**
     * US18: Ο committee επιλέγει τελικά ποιος φοιτητής αναλαμβάνει τη θέση.
     */
    @Override
    public void assignPosition(Long positionId, Long studentId) {
        TraineeshipPosition pos = positionService.findById(positionId);
        Student stud = studentService.findById(studentId);
        pos.setAssignedStudent(stud);
        positionService.save(pos);
    }

    /**
     * US19: Ανάθεση supervisor με στρατηγική.
     */
    @Override
    public Professor assignSupervisor(Long positionId, String strategy) {
        TraineeshipPosition position = positionService.findById(positionId);
        List<Professor> allProfessors = professorService.findAll();
        SupervisorAssignmentStrategy strat =
            "interest".equals(strategy)
                ? new InterestBasedProfessorStrategy(DEFAULT_THRESHOLD)
                : new LoadBasedProfessorStrategy();
        Professor selected = strat.selectSupervisor(position, allProfessors);
        if (selected != null) {
            position.setSupervisingProfessor(selected);
            positionService.save(position);
        }
        return selected;
    }

    /**
     * US21: Οριστική απόφαση PASS/FAIL.
     */
    @Override
    public void finalizePosition(Long positionId, FinalResult result) {
        TraineeshipPosition pos = positionService.findById(positionId);
        pos.setFinalResult(result);
        positionService.save(pos);
    }

    /**
     * US20: Λίστα των in-progress θέσεων (assigned αλλά όχι finalized).
     */
    @Override
    public List<TraineeshipPosition> getInProgressPositions() {
        return positionService.findAll().stream()
            .filter(p -> p.getAssignedStudent() != null && p.getFinalResult() == null)
            .collect(Collectors.toList());
    }

    /**
     * (Πρόσθετο) Λίστα των ολοκληρωμένων (finalized) θέσεων.
     */
    @Override
    public List<TraineeshipPosition> getCompletedPositions() {
        return positionService.findAll().stream()
            .filter(p -> p.getFinalResult() != null)
            .collect(Collectors.toList());
    }
}
