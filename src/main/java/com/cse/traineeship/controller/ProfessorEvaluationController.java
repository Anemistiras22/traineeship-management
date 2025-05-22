package com.cse.traineeship.controller;

import com.cse.traineeship.domain.Professor;
import com.cse.traineeship.domain.ProfessorEvaluation;
import com.cse.traineeship.service.ProfessorEvaluationService;
import com.cse.traineeship.service.ProfessorService;
import com.cse.traineeship.service.PositionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/positions/{positionId}/professor-evaluation")
public class ProfessorEvaluationController {

    private final ProfessorService professorService;
    private final ProfessorEvaluationService evaluationService;
    private final PositionService positionService;

    public ProfessorEvaluationController(ProfessorService professorService,
                                         ProfessorEvaluationService evaluationService,
                                         PositionService positionService) {
        this.professorService = professorService;
        this.evaluationService = evaluationService;
        this.positionService = positionService;
    }

    @GetMapping
    public String showForm(@PathVariable Long positionId,
                           Authentication auth,
                           Model model) {

        String username = auth.getName();
        Professor p = professorService.findByUsername(username);
        if (!positionService.findById(positionId)
                .getSupervisingProfessor().getId().equals(p.getId())) {
            return "redirect:/access-denied";
        }

        ProfessorEvaluation existing = evaluationService.findByPositionId(positionId);
        if (existing == null) {
            existing = new ProfessorEvaluation();
        }
        model.addAttribute("evaluation", existing);
        model.addAttribute("positionId", positionId);
        return "professor/professor-evaluation-form";
    }

    @PostMapping
    public String submit(@PathVariable Long positionId,
                         @RequestParam int motivation,
                         @RequestParam int effectiveness,
                         @RequestParam int efficiency,
                         @RequestParam int facilities,
                         @RequestParam int guidance,
                         Authentication auth) {

        String username = auth.getName();
        Professor p = professorService.findByUsername(username);
        if (!positionService.findById(positionId)
                .getSupervisingProfessor().getId().equals(p.getId())) {
            return "redirect:/access-denied";
        }

        evaluationService.saveOrUpdate(
                positionId, motivation, effectiveness,
                efficiency, facilities, guidance
        );
        return "redirect:/professors/positions";
    }
}
