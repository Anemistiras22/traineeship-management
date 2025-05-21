package com.cse.traineeship.service.impl;

import com.cse.traineeship.domain.Student;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.repository.PositionRepository;
import com.cse.traineeship.service.PositionService;
import com.cse.traineeship.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepo;
    private final StudentService studentService;

    public PositionServiceImpl(PositionRepository positionRepo,
                               StudentService studentService) {
        this.positionRepo = positionRepo;
        this.studentService = studentService;
    }

    @Override
    public List<TraineeshipPosition> findAll() {
        return positionRepo.findAll();
    }

    @Override
    public TraineeshipPosition findById(Long id) {
        return positionRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Position not found: " + id));
    }

    @Override
    public TraineeshipPosition save(TraineeshipPosition position) {
        return positionRepo.save(position);
    }

    @Override
    public void deleteById(Long id) {
        positionRepo.deleteById(id);
    }

    @Override
    public void apply(Long positionId, Long studentId) {
        TraineeshipPosition pos = findById(positionId);
        Student stud = studentService.findById(studentId);
        pos.setAssignedStudent(stud);
        positionRepo.save(pos);
    }

    @Override
    public TraineeshipPosition findByStudentId(Long studentId) {
        return positionRepo.findByAssignedStudentId(studentId)
                .orElseThrow(() -> new NoSuchElementException("No position assigned to student " + studentId));
    }

    @Override
    public List<TraineeshipPosition> findAllByStudentId(Long studentId) {
        return positionRepo.findAllByAssignedStudentId(studentId);
    }
}
