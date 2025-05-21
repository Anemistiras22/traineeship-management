package com.cse.traineeship.repository;

import com.cse.traineeship.domain.LogbookEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogbookEntryRepository extends JpaRepository<LogbookEntry, Long> {
    /** Βρίσκει όλες τις καταχωρήσεις για μια δεδομένη θέση, κατά timestamp φθίνουσα */
    List<LogbookEntry> findByPositionIdOrderByTimestampDesc(Long positionId);
}
