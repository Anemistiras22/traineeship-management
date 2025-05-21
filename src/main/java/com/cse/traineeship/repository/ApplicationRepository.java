package com.cse.traineeship.repository;

import com.cse.traineeship.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findAllByPositionId(Long positionId);
    List<Application> findAllByStudentId(Long studentId);
    boolean existsByPositionIdAndStudentId(Long positionId, Long studentId);
}
