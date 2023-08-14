package com.pearlyadana.rakhapuraapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "award")
public class Award {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "award", length = 150)
    private String award;

    @Column(name = "description", length = 300)
    private String description;

    @Column(name = "event_date", length = 30)
    private String eventDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    public Award() {
    }

    public Award(Long id, String award, String description, String eventDate, Student student) {
        this.id = id;
        this.award = award;
        this.description = description;
        this.eventDate = eventDate;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}
