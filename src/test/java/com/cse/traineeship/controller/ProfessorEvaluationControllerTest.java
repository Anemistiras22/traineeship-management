package com.cse.traineeship.controller;

import com.cse.traineeship.domain.Professor;
import com.cse.traineeship.domain.ProfessorEvaluation;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.service.ProfessorEvaluationService;
import com.cse.traineeship.service.ProfessorService;
import com.cse.traineeship.service.PositionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfessorEvaluationControllerTest {

    @Mock ProfessorService professorService;
    @Mock ProfessorEvaluationService evaluationService;
    @Mock PositionService positionService;
    @InjectMocks ProfessorEvaluationController controller;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void showForm_newEvaluation_setsBlankEval() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("profB");
        var sup = new Professor(); sup.setId(55L);
        var pos = new TraineeshipPosition(); pos.setSupervisingProfessor(sup);
        when(positionService.findById(9L)).thenReturn(pos);
        when(professorService.findByUsername("profB")).thenReturn(sup);
        when(evaluationService.findByPositionId(9L)).thenReturn(null);

        Model model = new BindingAwareModelMap();
        String view = controller.showForm(9L, auth, model);

        assertThat(view).isEqualTo("professor/professor-evaluation-form");
        assertThat(model.getAttribute("evaluation")).isInstanceOf(ProfessorEvaluation.class);
        assertThat(model.getAttribute("positionId")).isEqualTo(9L);
    }

    @Test
    void submit_forbiddenWhenNotSupervisor() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("X");
        var sup = new Professor(); sup.setId(77L);
        var pos = new TraineeshipPosition(); pos.setSupervisingProfessor(sup);
        when(positionService.findById(8L)).thenReturn(pos);
        when(professorService.findByUsername("X")).thenReturn(new Professor());

        String view = controller.submit(8L, 1,2,3,4,5, auth);
        assertThat(view).isEqualTo("redirect:/access-denied");
    }

    @Test
    void submit_valid_savesAndRedirects() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("profC");
        var sup = new Professor(); sup.setId(88L);
        var pos = new TraineeshipPosition(); pos.setSupervisingProfessor(sup);
        when(positionService.findById(11L)).thenReturn(pos);
        when(professorService.findByUsername("profC")).thenReturn(sup);

        String view = controller.submit(11L, 5,5,5,5,5, auth);
        verify(evaluationService).saveOrUpdate(11L, 5,5,5,5,5);
        assertThat(view).isEqualTo("redirect:/professors/positions");
    }
}
