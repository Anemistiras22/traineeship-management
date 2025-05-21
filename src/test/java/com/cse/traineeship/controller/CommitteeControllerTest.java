package com.cse.traineeship.controller;

import com.cse.traineeship.domain.Student;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.service.CommitteeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommitteeController.class)
@WithMockUser(username="committee1", roles={"COMMITTEE"})
class CommitteeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommitteeService committeeService;

    @Test
    @DisplayName("GET /committee/applicants: επιστρέφει λίστα applicants")
    void listApplicants() throws Exception {
        Student s1 = new Student(); s1.setId(1L);
        Student s2 = new Student(); s2.setId(2L);
        when(committeeService.getAllApplicants()).thenReturn(List.of(s1, s2));

        mockMvc.perform(get("/committee/applicants"))
                .andExpect(status().isOk())
                .andExpect(view().name("committee/committee-applicants"))
                .andExpect(model().attribute("applicants", List.of(s1, s2)));
    }

    @Test
    @DisplayName("POST /committee/search: επεξεργασία αναζήτησης")
    void processSearch() throws Exception {
        TraineeshipPosition p1 = new TraineeshipPosition(); p1.setId(10L);
        when(committeeService.searchPositions(1L, "interest"))
                .thenReturn(List.of(p1));

        mockMvc.perform(post("/committee/search")
                        .param("studentId", "1")
                        .param("strategy", "interest").with(csrf()))

                .andExpect(status().isOk())
                .andExpect(view().name("committee/committee-search-results"))
                .andExpect(model().attribute("positions", List.of(p1)))
                .andExpect(model().attribute("studentId", 1L));
    }

    @Test
    @DisplayName("POST /committee/assign/{id}: ανάθεση θέσης")
    void assignPosition() throws Exception {
        mockMvc.perform(post("/committee/assign/10")
                        .param("studentId", "5").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/committee/applicants"));
    }
}
