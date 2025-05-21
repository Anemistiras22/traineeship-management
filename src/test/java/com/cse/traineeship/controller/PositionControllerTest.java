package com.cse.traineeship.controller;

import com.cse.traineeship.domain.Company;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.domain.Student;
import com.cse.traineeship.service.CompanyService;
import com.cse.traineeship.service.PositionService;
import com.cse.traineeship.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

class PositionControllerTest {

    @Mock PositionService positionService;
    @Mock CompanyService companyService;
    @Mock StudentService studentService;
    @InjectMocks PositionController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    void showNewForm_populatesForm() {
        when(companyService.findAll()).thenReturn(List.of(new Company()));
        Model model = new BindingAwareModelMap();

        String view = controller.showNewForm(model);

        assertThat(view).isEqualTo("company/position-form");
        assertThat(model.getAttribute("position")).isInstanceOf(TraineeshipPosition.class);
        assertThat(model.getAttribute("companies")).isNotNull();
    }

    @Test
    void save_redirectsAfterSave() {
        var pos = new TraineeshipPosition();
        var comp = new Company(); comp.setId(99L);
        when(companyService.findById(99L)).thenReturn(comp);

        String view = controller.save(pos, 99L, List.of("Java"), List.of("Spring"));

        verify(positionService).save(pos);
        assertThat(pos.getCompany()).isSameAs(comp);
        assertThat(view).isEqualTo("redirect:/positions");
    }

    @Test
    void showApplyForm_listsStudentsAndPosition() {
        var pos = new TraineeshipPosition(); pos.setId(7L);
        when(positionService.findById(7L)).thenReturn(pos);
        when(studentService.findAll()).thenReturn(List.of(new Student()));

        Model model = new BindingAwareModelMap();
        String view = controller.showApplyForm(7L, model);

        assertThat(view).isEqualTo("student/apply-form");
        assertThat(model.getAttribute("position")).isSameAs(pos);
        assertThat(model.getAttribute("students")).isNotNull();
    }

    @Test
    void submitApply_invokesServiceAndRedirects() {
        String view = controller.submitApply(5L, 42L);
        verify(positionService).apply(5L, 42L);
        assertThat(view).isEqualTo("redirect:/positions/search");
    }
}
