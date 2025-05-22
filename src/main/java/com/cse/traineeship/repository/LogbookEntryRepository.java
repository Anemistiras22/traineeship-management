package com.cse.traineeship.repository;

import com.cse.traineeship.domain.LogbookEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogbookEntryRepository extends JpaRepository<LogbookEntry, Long> {
    List<LogbookEntry> findByPositionIdOrderByTimestampDesc(Long positionId);
}
