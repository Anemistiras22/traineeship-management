package com.cse.traineeship.controller;

import com.cse.traineeship.domain.Professor;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.service.ProfessorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfessorControllerTest {

    @Mock ProfessorService professorService;
    @InjectMocks ProfessorController controller;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void editMyProfile_noExisting_createsBlank() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("profX");
        when(professorService.findByUsername("profX")).thenReturn(null);

        Model model = new BindingAwareModelMap();
        String view = controller.editMyProfile(auth, model);

        assertThat(view).isEqualTo("professor/professor-profile-form");
        Professor saved = (Professor) model.getAttribute("professor");
        assertThat(saved.getUsername()).isEqualTo("profX");
    }

    @Test
    void saveMyProfile_createsOrUpdates_thenRedirects() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("profY");
        when(professorService.findByUsername("profY")).thenReturn(null);

        String view = controller.saveMyProfile(auth, "Jane", "Go, Kotlin");
        verify(professorService).createProfile("profY", "Jane", Arrays.asList("Go","Kotlin"));
        assertThat(view).isEqualTo("redirect:/professors/positions");

        // now stub existing:
        reset(professorService);
        var existing = new Professor(); existing.setId(5L);
        when(professorService.findByUsername("profY")).thenReturn(existing);

        view = controller.saveMyProfile(auth, "Jane2", "Scala");
        verify(professorService).updateProfile(5L, "Jane2", Arrays.asList("Scala"));
        assertThat(view).isEqualTo("redirect:/professors/positions");
    }

    @Test
    void listMyPositions_noProfile_redirects() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("profZ");
        when(professorService.findByUsername("profZ")).thenReturn(null);

        String view = controller.listMyPositions(auth, mock(Model.class));
        assertThat(view).isEqualTo("redirect:/professors/me/edit");
    }

    @Test
    void listMyPositions_withProfile_showsPositions() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("profA");
        Professor p = new Professor();
        p.setSupervisedPositions(Arrays.asList(new TraineeshipPosition(), new TraineeshipPosition()));
        when(professorService.findByUsername("profA")).thenReturn(p);

        Model model = new BindingAwareModelMap();
        String view = controller.listMyPositions(auth, model);

        assertThat(view).isEqualTo("professor/professor-positions");
        assertThat(model.getAttribute("positions")).isEqualTo(p.getSupervisedPositions());
    }
}
