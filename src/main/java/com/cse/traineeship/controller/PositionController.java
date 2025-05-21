package com.cse.traineeship.controller;

import com.cse.traineeship.domain.Company;
import com.cse.traineeship.domain.Student;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.service.CompanyService;
import com.cse.traineeship.service.PositionService;
import com.cse.traineeship.service.StudentService;
import com.cse.traineeship.service.strategy.CombinedStrategy;
import com.cse.traineeship.service.strategy.InterestBasedStrategy;
import com.cse.traineeship.service.strategy.LocationBasedStrategy;
import com.cse.traineeship.service.strategy.PositionSearchStrategy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/positions")
public class PositionController {

    private final PositionService positionService;
    private final CompanyService companyService;
    private final StudentService studentService;
    private static final double DEFAULT_THRESHOLD = 0.5;

    public PositionController(PositionService positionService,
                              CompanyService companyService,
                              StudentService studentService) {
        this.positionService = positionService;
        this.companyService = companyService;
        this.studentService = studentService;
    }

    /** Λίστα όλων των θέσεων */
    @GetMapping
    public String list(Model model) {
        List<TraineeshipPosition> positions = positionService.findAll();
        model.addAttribute("positions", positions);
        return "positions";
    }

    /** Φόρμα αναζήτησης (student) */
    @GetMapping("/search")
    public String showSearchForm(Authentication auth, Model model) {
        Student student = studentService.findByUsername(auth.getName());
        // guard: αν δεν υπάρχει ή δεν είναι πλήρες το profile
        if (student == null
                || student.getFullName() == null || student.getFullName().isBlank()
                || student.getUniversityId() == null || student.getUniversityId().isBlank()) {
            return "redirect:/students/me/edit";
        }
        return "student/search-form";
    }

    /** Υποβολή αναζήτησης (student) */
    @PostMapping("/search")
    public String doSearch(@RequestParam String strategy,
                           Authentication auth,
                           Model model) {
        Student student = studentService.findByUsername(auth.getName());
        // guard πάλι για null ή ανολοκλήρωτο profile
        if (student == null
                || student.getFullName() == null || student.getFullName().isBlank()
                || student.getUniversityId() == null || student.getUniversityId().isBlank()) {
            return "redirect:/students/me/edit";
        }

        List<TraineeshipPosition> open = positionService.findAll().stream()
                .filter(p -> p.getAssignedStudent() == null)
                .toList();

        PositionSearchStrategy strat;
        switch (strategy) {
            case "interest":
                strat = new InterestBasedStrategy(DEFAULT_THRESHOLD);
                break;
            case "location":
                strat = new LocationBasedStrategy();
                break;
            case "combined":
            default:
                strat = new CombinedStrategy(DEFAULT_THRESHOLD);
                break;
        }

        List<TraineeshipPosition> results = strat.findMatching(student, open);

        model.addAttribute("positions", results);
        return "student/search-results";
    }

    /** Φόρμα δημιουργίας νέας θέσης (committee & company) */
    @GetMapping("/new")
    public String showNewForm(Model model) {
        model.addAttribute("position", new TraineeshipPosition());
        model.addAttribute("companies", companyService.findAll());
        return "company/position-form";
    }

    /** Επεξεργασία υπάρχουσας θέσης */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("position", positionService.findById(id));
        model.addAttribute("companies", companyService.findAll());
        return "company/position-form";
    }

    /** Αποθήκευση νέας ή επεξεργασμένης θέσης */
    @PostMapping("/save")
    public String save(@ModelAttribute TraineeshipPosition position,
                       @RequestParam("companyId") Long companyId,
                       @RequestParam(required = false) List<String> requiredSkills,
                       @RequestParam(required = false) List<String> topics) {
        Company company = companyService.findById(companyId);
        position.setCompany(company);
        position.setRequiredSkills(requiredSkills != null ? requiredSkills : List.of());
        position.setTopics(topics != null ? topics : List.of());
        positionService.save(position);
        return "redirect:/positions";
    }

    /** Διαγραφή θέσης */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        positionService.deleteById(id);
        return "redirect:/positions";
    }

    /** Φόρμα apply φοιτητή για θέση */
    @GetMapping("/apply/{id}")
    public String showApplyForm(@PathVariable Long id, Model model) {
        model.addAttribute("position", positionService.findById(id));
        model.addAttribute("students", studentService.findAll());
        return "student/apply-form";
    }

    /** Υποβολή apply φοιτητή για θέση */
    @PostMapping("/apply/{id}")
    public String submitApply(@PathVariable Long id,
                              @RequestParam("studentId") Long studentId) {
        positionService.apply(id, studentId);
        // μετά το apply θα μείνει ο φοιτητής στη σελίδα αναζητήσεων
        return "redirect:/positions/search";
    }
}
