package com.cse.traineeship.service.impl;

import com.cse.traineeship.domain.LogbookEntry;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.repository.LogbookEntryRepository;
import com.cse.traineeship.service.LogbookService;
import com.cse.traineeship.service.PositionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LogbookServiceImpl implements LogbookService {

    private final LogbookEntryRepository entryRepo;
    private final PositionService positionService;

    public LogbookServiceImpl(LogbookEntryRepository entryRepo,
                              PositionService positionService) {
        this.entryRepo = entryRepo;
        this.positionService = positionService;
    }

    @Override
    public List<LogbookEntry> findEntriesForPosition(Long positionId) {
        return entryRepo.findByPositionIdOrderByTimestampDesc(positionId);
    }

    @Override
    public LogbookEntry addEntry(Long positionId, String description) {
        TraineeshipPosition pos = positionService.findById(positionId);
        LogbookEntry entry = new LogbookEntry();
        entry.setPosition(pos);
        entry.setDescription(description);
        return entryRepo.save(entry);
    }
}
