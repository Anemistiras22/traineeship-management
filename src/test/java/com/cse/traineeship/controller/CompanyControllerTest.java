package com.cse.traineeship.controller;

import com.cse.traineeship.domain.Company;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.service.CompanyService;
import com.cse.traineeship.service.PositionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CompanyController.class)
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @MockBean
    private PositionService positionService;

    @Test
    @DisplayName("GET /company/me/edit: όταν δεν υπάρχει προφίλ, φορτώνει κενό Company")
    @WithMockUser(username = "comp1", roles = {"COMPANY"})
    void editMyProfile_whenNoProfile() throws Exception {
        when(companyService.findByUsername("comp1")).thenReturn(null);

        mockMvc.perform(get("/company/me/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("company/company-profile-form"))
                .andExpect(model().attributeExists("company"));
    }


}
