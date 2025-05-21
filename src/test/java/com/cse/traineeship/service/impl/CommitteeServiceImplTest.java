package com.cse.traineeship.service.impl;

import com.cse.traineeship.domain.*;
import com.cse.traineeship.service.PositionService;
import com.cse.traineeship.service.ProfessorService;
import com.cse.traineeship.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class CommitteeServiceImplTest {

    @Mock StudentService studentService;
    @Mock PositionService positionService;
    @Mock ProfessorService professorService;
    @InjectMocks CommitteeServiceImpl committeeService;

    private Student student;
    private TraineeshipPosition pos1, pos2, pos3;
    private Professor prof1, prof2;

    @BeforeEach
    void setUp() {
        // φοιτητής
        student = new Student();
        student.setId(1L);
        student.setInterests(List.of("Java", "Spring"));
        student.setPreferredLocation("Athens");

        // θέση 1 (Java, Athens)
        pos1 = new TraineeshipPosition();
        pos1.setId(10L);
        pos1.setTopics(List.of("Java"));
        pos1.setAssignedStudent(null);
        Company c1 = new Company();
        c1.setLocation("Athens");
        pos1.setCompany(c1);

        // θέση 2 (Python, Thessaloniki)
        pos2 = new TraineeshipPosition();
        pos2.setId(20L);
        pos2.setTopics(List.of("Python"));
        pos2.setAssignedStudent(null);
        Company c2 = new Company();
        c2.setLocation("Thessaloniki");
        pos2.setCompany(c2);

        // θέση 3 (ήδη assigned)
        pos3 = new TraineeshipPosition();
        pos3.setId(30L);
        pos3.setAssignedStudent(student);
        pos3.setFinalResult(null);

        // καθηγητές
        prof1 = new Professor(); prof1.setId(100L); prof1.setInterests(List.of("Java"));
        prof2 = new Professor(); prof2.setId(200L); prof2.setInterests(List.of("C++"));
    }



    @Test
    void assignPosition_assignsAndSaves() {
        when(positionService.findById(10L)).thenReturn(pos1);
        when(studentService.findById(1L)).thenReturn(student);

        committeeService.assignPosition(10L, 1L);

        // επιβεβαιώνουμε ότι ο φοιτητής ανατέθηκε
        assertSame(student, pos1.getAssignedStudent());
        // και ότι έγινε save
        verify(positionService).save(pos1);
    }

    @Test
    void assignSupervisor_interestBased() {
        when(positionService.findById(10L)).thenReturn(pos1);
        when(professorService.findAll()).thenReturn(List.of(prof1, prof2));

        Professor selected = committeeService.assignSupervisor(10L, "interest");

        assertSame(prof1, selected);
        assertSame(prof1, pos1.getSupervisingProfessor());
        verify(positionService).save(pos1);
    }

    @Test
    void getInProgressPositions_filtersCorrectly() {
        pos1.setAssignedStudent(student); pos1.setFinalResult(null);
        pos2.setAssignedStudent(null);
        pos3.setFinalResult(FinalResult.PASS);
        when(positionService.findAll()).thenReturn(List.of(pos1, pos2, pos3));

        List<TraineeshipPosition> results = committeeService.getInProgressPositions();

        assertEquals(1, results.size());
        assertTrue(results.contains(pos1));
    }

    @Test
    void getCompletedPositions_filtersCorrectly() {
        pos1.setFinalResult(FinalResult.PASS);
        pos2.setFinalResult(null);
        when(positionService.findAll()).thenReturn(List.of(pos1, pos2));

        List<TraineeshipPosition> results = committeeService.getCompletedPositions();

        assertEquals(1, results.size());
        assertTrue(results.contains(pos1));
    }

    @Test
    void finalizePosition_setsFinalResultAndSaves() {
        when(positionService.findById(20L)).thenReturn(pos2);

        committeeService.finalizePosition(20L, FinalResult.FAIL);

        assertEquals(FinalResult.FAIL, pos2.getFinalResult());
        verify(positionService).save(pos2);
    }
}
