package com.cse.traineeship.controller;

import com.cse.traineeship.domain.FinalResult;
import com.cse.traineeship.domain.Student;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.service.CommitteeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/committee")
public class CommitteeController {

    private final CommitteeService committeeService;

    public CommitteeController(CommitteeService committeeService) {
        this.committeeService = committeeService;
    }

    // US16
    @GetMapping("/applicants")
    public String listApplicants(Model model) {
        List<Student> applicants = committeeService.getAllApplicants();
        model.addAttribute("applicants", applicants);
        return "committee/committee-applicants";
    }

    // US17
    @GetMapping("/search")
    public String showSearchForm(Model model) {
        List<Student> students = committeeService.getAllApplicants();
        model.addAttribute("students", students);
        return "committee/committee-search-form";
    }
    @PostMapping("/search")
    public String processSearch(@RequestParam Long studentId,
                                @RequestParam String strategy,
                                Model model) {
        List<TraineeshipPosition> positions =
                committeeService.searchPositions(studentId, strategy);
        model.addAttribute("positions", positions);
        model.addAttribute("studentId", studentId);
        return "committee/committee-search-results";
    }

    // US18
    @PostMapping("/assign/{positionId}")
    public String assignPosition(@PathVariable Long positionId,
                                 @RequestParam Long studentId) {
        committeeService.assignPosition(positionId, studentId);
        return "redirect:/committee/applicants";
    }

    // US19
    @GetMapping("/assign-supervisor/{positionId}")
    public String showAssignSupervisorForm(@PathVariable Long positionId,
                                           Model model) {
        model.addAttribute("positionId", positionId);
        return "committee/committee-assign-supervisor-form";
    }
    @PostMapping("/assign-supervisor/{positionId}")
    public String assignSupervisor(@PathVariable Long positionId,
                                   @RequestParam String strategy) {
        committeeService.assignSupervisor(positionId, strategy);
        return "redirect:/committee/positions/in-progress";
    }

    // US20: In-Progress
    @GetMapping("/positions/in-progress")
    public String listInProgress(Model model) {
        List<TraineeshipPosition> inProgress = committeeService.getInProgressPositions();
        model.addAttribute("positions", inProgress);
        return "committee/committee-in-progress";
    }

    // **US22** (νέο): Completed
    @GetMapping("/positions/completed")
    public String listCompleted(Model model) {
        List<TraineeshipPosition> completed = committeeService.getCompletedPositions();
        model.addAttribute("positions", completed);
        return "committee/committee-completed";
    }

    // US21: Finalize (όπως πριν)
    @GetMapping("/finalize/{positionId}")
    public String showFinalizeForm(@PathVariable Long positionId, Model model) {
        model.addAttribute("positionId", positionId);
        return "committee/committee-finalize-form";
    }
    @PostMapping("/finalize/{positionId}")
    public String finalizePosition(@PathVariable Long positionId,
                                   @RequestParam String result) {
        committeeService.finalizePosition(positionId, FinalResult.valueOf(result));
        return "redirect:/committee/positions/in-progress";
    }
}
