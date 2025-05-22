package com.cse.traineeship.controller;

import com.cse.traineeship.domain.LogbookEntry;
import com.cse.traineeship.domain.Student;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.service.LogbookService;
import com.cse.traineeship.service.PositionService;
import com.cse.traineeship.service.StudentService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/logbook")
public class LogbookController {

    private final LogbookService logbookService;
    private final StudentService studentService;
    private final PositionService positionService;

    public LogbookController(LogbookService logbookService,
                             StudentService studentService,
                             PositionService positionService) {
        this.logbookService = logbookService;
        this.studentService = studentService;
        this.positionService = positionService;
    }

    @GetMapping
    public String listPositions(Model model, Authentication auth) {
        Student student = studentService.findByUsername(auth.getName());
        if (student == null
                || student.getFullName() == null || student.getFullName().isBlank()
                || student.getUniversityId() == null || student.getUniversityId().isBlank()) {
            return "redirect:/students/me/edit";
        }

        List<TraineeshipPosition> positions =
                positionService.findAllByStudentId(student.getId());
        model.addAttribute("positions", positions);
        return "student/logbook";
    }

    @GetMapping("/{positionId}")
    public String showEntries(@PathVariable Long positionId,
                              Model model,
                              Authentication auth) {
        Student student = studentService.findByUsername(auth.getName());
        if (student == null
                || student.getFullName() == null || student.getFullName().isBlank()
                || student.getUniversityId() == null || student.getUniversityId().isBlank()) {
            return "redirect:/students/me/edit";
        }

        TraineeshipPosition pos = positionService.findById(positionId);
        List<LogbookEntry> entries =
                logbookService.findEntriesForPosition(positionId);
        model.addAttribute("position", pos);
        model.addAttribute("entries", entries);
        return "student/logbook-entries";
    }

    @PostMapping("/add")
    public String addEntry(@RequestParam Long positionId,
                           @RequestParam String description,
                           Authentication auth) {
        Student student = studentService.findByUsername(auth.getName());
        if (student == null
                || student.getFullName() == null || student.getFullName().isBlank()
                || student.getUniversityId() == null || student.getUniversityId().isBlank()) {
            return "redirect:/students/me/edit";
        }
        logbookService.addEntry(positionId, description);
        return "redirect:/logbook/" + positionId;
    }


}
