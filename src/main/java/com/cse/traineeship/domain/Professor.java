package com.cse.traineeship.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "professors")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String fullName;

    @ElementCollection
    @CollectionTable(name = "professor_interests",
            joinColumns = @JoinColumn(name = "professor_id"))
    @Column(name = "interest")
    private List<String> interests;

    @OneToMany(mappedBy = "supervisingProfessor")
    private List<TraineeshipPosition> supervisedPositions;

    // getters & setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public List<String> getInterests() { return interests; }
    public void setInterests(List<String> interests) { this.interests = interests; }

    public List<TraineeshipPosition> getSupervisedPositions() {
        return supervisedPositions;
    }
    public void setSupervisedPositions(List<TraineeshipPosition> supervisedPositions) {
        this.supervisedPositions = supervisedPositions;
    }
}
