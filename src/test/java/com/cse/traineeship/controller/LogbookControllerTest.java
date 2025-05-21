package com.cse.traineeship.controller;

import com.cse.traineeship.domain.LogbookEntry;
import com.cse.traineeship.domain.Student;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.service.LogbookService;
import com.cse.traineeship.service.PositionService;
import com.cse.traineeship.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LogbookController.class)
class LogbookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private LogbookService  logbookService;
    @MockBean private StudentService  studentService;
    @MockBean private PositionService positionService;

    private Student               stud;
    private TraineeshipPosition   pos;
    private LogbookEntry          entry;

    @BeforeEach
    void setUp() {
        stud = new Student();
        stud.setId(99L);
        stud.setUsername("studX");
        stud.setFullName("Stud X");
        stud.setUniversityId("U999");

        pos = new TraineeshipPosition();
        pos.setId(123L);
        pos.setDescription("Pos Desc");

        entry = new LogbookEntry();
        entry.setId(1001L);
        entry.setPosition(pos);
        entry.setDescription("Did something");
    }

    @WithMockUser(roles = "STUDENT", username = "studX")
    @Test
    void GET_list_positions_shows_logbook_page() throws Exception {
        when(studentService.findByUsername("studX")).thenReturn(stud);
        when(positionService.findAllByStudentId(99L)).thenReturn(List.of(pos));

        mockMvc.perform(get("/logbook"))
                .andExpect(status().isOk())
                .andExpect(view().name("student/logbook"))
                .andExpect(model().attribute("positions", List.of(pos)));
    }

}
