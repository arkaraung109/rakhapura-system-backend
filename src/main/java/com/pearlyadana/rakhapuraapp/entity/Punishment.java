package com.pearlyadana.rakhapuraapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "punishment")
public class Punishment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "punishment", length = 150)
    private String punishment;

    @Column(name = "description", length = 300)
    private String description;

    @Column(name = "event_date", length = 30)
    private String eventDate;

    @Column(name = "start_date", length = 300)
    private String startDate;

    @Column(name = "end_date", length = 300)
    private String endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    public Punishment() {
    }

    public Punishment(Long id, String punishment, String description, String eventDate, String startDate, String endDate, Student student) {
        this.id = id;
        this.punishment = punishment;
        this.description = description;
        this.eventDate = eventDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPunishment() {
        return punishment;
    }

    public void setPunishment(String punishment) {
        this.punishment = punishment;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}
