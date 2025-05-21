package com.cse.traineeship.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "positions")
public class TraineeshipPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(length = 500)
    private String description;
    
    

    @ElementCollection
    @CollectionTable(
            name = "position_skills",
            joinColumns = @JoinColumn(name = "position_id")
    )
    @Column(name = "skill")
    private List<String> requiredSkills = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "position_topics",
            joinColumns = @JoinColumn(name = "position_id")
    )
    @Column(name = "topic")
    private List<String> topics = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student assignedStudent;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor supervisingProfessor;

    @OneToMany(
            mappedBy = "position",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<LogbookEntry> logbookEntries = new ArrayList<>();

    @OneToOne(
            mappedBy = "position",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private CompanyEvaluation companyEvaluation;

    @OneToOne(
            mappedBy = "position",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private ProfessorEvaluation professorEvaluation;
    
    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications = new ArrayList<>();
    
    public List<Application> getApplications() { return applications; }
    public void addApplication(Application app) {
        applications.add(app);
        app.setPosition(this);
    }
    public void removeApplication(Application app) {
        applications.remove(app);
        app.setPosition(null);
    }
    

    /** Νέο πεδίο τελικής αξιολόγησης */
    @Enumerated(EnumType.STRING)
    @Column(name = "final_result")
    private FinalResult finalResult;

    public TraineeshipPosition() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getRequiredSkills() {
        return requiredSkills;
    }
    public void setRequiredSkills(List<String> requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public List<String> getTopics() {
        return topics;
    }
    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }

    public Student getAssignedStudent() {
        return assignedStudent;
    }
    public void setAssignedStudent(Student assignedStudent) {
        this.assignedStudent = assignedStudent;
    }

    public Professor getSupervisingProfessor() {
        return supervisingProfessor;
    }
    public void setSupervisingProfessor(Professor supervisingProfessor) {
        this.supervisingProfessor = supervisingProfessor;
    }

    public List<LogbookEntry> getLogbookEntries() {
        return logbookEntries;
    }
    public void addLogbookEntry(LogbookEntry entry) {
        logbookEntries.add(entry);
        entry.setPosition(this);
    }
    public void removeLogbookEntry(LogbookEntry entry) {
        logbookEntries.remove(entry);
        entry.setPosition(null);
    }

    public CompanyEvaluation getCompanyEvaluation() {
        return companyEvaluation;
    }
    public void setCompanyEvaluation(CompanyEvaluation companyEvaluation) {
        this.companyEvaluation = companyEvaluation;
        if (companyEvaluation.getPosition() != this) {
            companyEvaluation.setPosition(this);
        }
    }

    public ProfessorEvaluation getProfessorEvaluation() {
        return professorEvaluation;
    }
    public void setProfessorEvaluation(ProfessorEvaluation professorEvaluation) {
        this.professorEvaluation = professorEvaluation;
        if (professorEvaluation.getPosition() != this) {
            professorEvaluation.setPosition(this);
        }
    }

    public FinalResult getFinalResult() {
        return finalResult;
    }
    public void setFinalResult(FinalResult finalResult) {
        this.finalResult = finalResult;
    }

    @Transient
    public String getRequiredSkillsAsString() {
        return String.join(",", requiredSkills);
    }

    @Transient
    public String getTopicsAsString() {
        return String.join(",", topics);
    }
}
