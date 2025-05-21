package com.cse.traineeship.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Συνδεδεμένο username από users.username */
    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "university_id", unique = true)
    private String universityId;

    @Column(name = "preferred_location")
    private String preferredLocation;

    @ElementCollection
    @CollectionTable(name = "student_interests", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "interest")
    private List<String> interests;

    @ElementCollection
    @CollectionTable(name = "student_skills", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "skill")
    private List<String> skills;
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications = new ArrayList<>();

    public List<Application> getApplications() { return applications; }
    public void addApplication(Application app) {
        applications.add(app);
        app.setStudent(this);
    }
    public void removeApplication(Application app) {
        applications.remove(app);
        app.setStudent(null);
    }


    // Getters & Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getUniversityId() { return universityId; }
    public void setUniversityId(String universityId) { this.universityId = universityId; }

    public String getPreferredLocation() { return preferredLocation; }
    public void setPreferredLocation(String preferredLocation) { this.preferredLocation = preferredLocation; }

    public List<String> getInterests() { return interests; }
    public void setInterests(List<String> interests) { this.interests = interests; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }
}
