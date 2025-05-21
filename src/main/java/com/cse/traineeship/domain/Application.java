package com.cse.traineeship.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "position_id", nullable = false)
    private TraineeshipPosition position;

    // getters / setters
    public Long getId() { return id; }
    public Student getStudent() { return student; }
    public void setStudent(Student s) { this.student = s; }
    public TraineeshipPosition getPosition() { return position; }
    public void setPosition(TraineeshipPosition p) { this.position = p; }
}
