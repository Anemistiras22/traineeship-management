package com.cse.traineeship.controller;

import com.cse.traineeship.domain.Company;
import com.cse.traineeship.domain.CompanyEvaluation;
import com.cse.traineeship.service.CompanyEvaluationService;
import com.cse.traineeship.service.CompanyService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/positions/{positionId}/company-evaluation")
public class CompanyEvaluationController {

    private final CompanyService companyService;
    private final CompanyEvaluationService evaluationService;

    public CompanyEvaluationController(CompanyService companyService,
                                       CompanyEvaluationService evaluationService) {
        this.companyService = companyService;
        this.evaluationService = evaluationService;
    }

    @GetMapping
    public String showForm(@PathVariable("positionId") Long positionId,
                           Authentication auth,
                           Model model) {

        Company c = companyService.findByUsername(auth.getName());
        if (c == null) {
            return "redirect:/company/me/edit";
        }

        CompanyEvaluation existing = evaluationService.findByPositionId(positionId);
        if (existing == null) {
            existing = new CompanyEvaluation();
        }
        model.addAttribute("evaluation", existing);
        model.addAttribute("positionId", positionId);
        return "company/company-evaluation-form";
    }

    @PostMapping
    public String submit(@PathVariable("positionId") Long positionId,
                         @RequestParam int motivation,
                         @RequestParam int effectiveness,
                         @RequestParam int efficiency,
                         Authentication auth) {

        Company c = companyService.findByUsername(auth.getName());
        if (c == null) {
            return "redirect:/company/me/edit";
        }

        evaluationService.saveOrUpdate(positionId, motivation, effectiveness, efficiency);
        return "redirect:/company/positions";
    }
}
