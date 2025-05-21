package com.cse.traineeship.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logbook_entries")
public class LogbookEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** H θέση στην οποία ανήκει αυτή η καταχώρηση */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    private TraineeshipPosition position;

    /** Χρονική στιγμή καταγραφής */
    @Column(nullable = false)
    private LocalDateTime timestamp;

    /** Περιγραφή δραστηριότητας */
    @Column(length = 2000, nullable = false)
    private String description;

    public LogbookEntry() {
        // ορίζουμε timestamp κατά τη δημιουργία
        this.timestamp = LocalDateTime.now();
    }

    // getters & setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TraineeshipPosition getPosition() { return position; }
    public void setPosition(TraineeshipPosition position) { this.position = position; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
