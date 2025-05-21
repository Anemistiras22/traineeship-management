package com.cse.traineeship.service;

import com.cse.traineeship.domain.Application;

import java.util.List;

public interface ApplicationService {
    void apply(Long positionId, Long studentId);
    List<Application> findByPosition(Long positionId);
    List<Application> findByStudent(Long studentId);
}
