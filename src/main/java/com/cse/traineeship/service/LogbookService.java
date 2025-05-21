package com.cse.traineeship.service;

import com.cse.traineeship.domain.LogbookEntry;

import java.util.List;

public interface LogbookService {
    /** Επιστρέφει όλες τις καταχωρήσεις για μία θέση */
    List<LogbookEntry> findEntriesForPosition(Long positionId);

    /** Δημιουργεί νέα καταχώρηση */
    LogbookEntry addEntry(Long positionId, String description);
}
