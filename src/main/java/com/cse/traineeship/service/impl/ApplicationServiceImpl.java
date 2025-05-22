package com.cse.traineeship.service.impl;

import com.cse.traineeship.domain.Application;
import com.cse.traineeship.domain.Student;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.repository.ApplicationRepository;
import com.cse.traineeship.service.ApplicationService;
import com.cse.traineeship.service.PositionService;
import com.cse.traineeship.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository repo;
    private final PositionService positionService;
    private final StudentService studentService;

    public ApplicationServiceImpl(ApplicationRepository repo,
                                  PositionService positionService,
                                  StudentService studentService) {
        this.repo = repo;
        this.positionService = positionService;
        this.studentService = studentService;
    }

    @Override
    public void apply(Long positionId, Long studentId) {
        if (repo.existsByPositionIdAndStudentId(positionId, studentId)) {
            return;
        }
        TraineeshipPosition pos = positionService.findById(positionId);
        if (pos.getAssignedStudent() != null) {
            throw new IllegalStateException("Η θέση είναι ήδη κατειλημμένη");
        }
        Student stu = studentService.findById(studentId);
        Application app = new Application();
        app.setPosition(pos);
        app.setStudent(stu);
        repo.save(app);
    }

    @Override
    public List<Application> findByPosition(Long positionId) {
        return repo.findAllByPositionId(positionId);
    }

    @Override
    public List<Application> findByStudent(Long studentId) {
        return repo.findAllByStudentId(studentId);
    }
}
