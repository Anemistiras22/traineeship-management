package com.cse.traineeship.controller;

import com.cse.traineeship.domain.Company;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.domain.Student;
import com.cse.traineeship.service.CompanyService;
import com.cse.traineeship.service.PositionService;
import com.cse.traineeship.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PositionController.class)
class PositionControllerTestSec {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private PositionService positionService;
    @MockBean private CompanyService  companyService;
    @MockBean private StudentService  studentService;

    private TraineeshipPosition pos;
    private Company               comp;
    private Student               stud;

    @BeforeEach
    void setUp() {
        comp = new Company();
        comp.setId(5L);
        comp.setName("DemoCo");
        comp.setLocation("Athens");

        pos = new TraineeshipPosition();
        pos.setId(42L);
        pos.setDescription("Demo position");
        pos.setCompany(comp);

        stud = new Student();
        stud.setId(7L);
        stud.setUsername("student1");
        stud.setFullName("S. One");
        stud.setUniversityId("U123");
    }


    @WithMockUser(roles = "STUDENT", username = "student1")
    @Test
    void GET_search_form_guards_on_incomplete_profile() throws Exception {
        // αν ο φοιτητής δεν έχει profile, τον στέλνουμε στο edit
        when(studentService.findByUsername("student1")).thenReturn(new Student());

        mockMvc.perform(get("/positions/search"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/students/me/edit"));
    }


}
