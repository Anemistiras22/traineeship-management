package com.cse.traineeship.domain;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "company_evaluations")
public class CompanyEvaluation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false, unique = true)
    private TraineeshipPosition position;

    @Column(nullable = false)
    private int motivation;

    @Column(nullable = false)
    private int effectiveness;

    @Column(nullable = false)
    private int efficiency;

    // getters & setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TraineeshipPosition getPosition() { return position; }
    public void setPosition(TraineeshipPosition position) { this.position = position; }

    public int getMotivation() { return motivation; }
    public void setMotivation(int motivation) { this.motivation = motivation; }

    public int getEffectiveness() { return effectiveness; }
    public void setEffectiveness(int effectiveness) { this.effectiveness = effectiveness; }

    public int getEfficiency() { return efficiency; }
    public void setEfficiency(int efficiency) { this.efficiency = efficiency; }
}
