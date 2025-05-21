package com.cse.traineeship.service.impl;

import com.cse.traineeship.domain.LogbookEntry;
import com.cse.traineeship.domain.TraineeshipPosition;
import com.cse.traineeship.repository.LogbookEntryRepository;
import com.cse.traineeship.service.PositionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class LogbookServiceImplTest {

    @Mock LogbookEntryRepository entryRepository;
    @Mock PositionService positionService;
    @InjectMocks LogbookServiceImpl logbookService;

    private TraineeshipPosition position;
    private LogbookEntry entry;

    @BeforeEach
    void setUp() {
        position = new TraineeshipPosition();
        position.setId(1L);

        entry = new LogbookEntry();
        entry.setId(10L);
        entry.setPosition(position);
        entry.setDescription("Test entry");
        entry.setTimestamp(LocalDateTime.now());
    }

    @Test
    void addEntry_savesNewEntry() {
        when(positionService.findById(1L)).thenReturn(position);
        when(entryRepository.save(any(LogbookEntry.class))).thenAnswer(i -> i.getArgument(0));

        LogbookEntry saved = logbookService.addEntry(1L, "New entry");
        assertEquals("New entry", saved.getDescription());
        assertSame(position, saved.getPosition());
        assertNotNull(saved.getTimestamp());
        verify(entryRepository).save(any(LogbookEntry.class));
    }
}
