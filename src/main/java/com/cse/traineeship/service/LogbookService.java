package com.cse.traineeship.service;

import com.cse.traineeship.domain.LogbookEntry;

import java.util.List;

public interface LogbookService {
    List<LogbookEntry> findEntriesForPosition(Long positionId);

    LogbookEntry addEntry(Long positionId, String description);
}
