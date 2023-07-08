package com.pearlyadana.rakhapuraapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "exam_title")
public class ExamTitle {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "authorized_status")
    private boolean authorizedStatus;

    @Column(name = "authorized_user_id")
    private Long authorizedUserId;

    public ExamTitle() {
    }

    public ExamTitle(Long id, String name, boolean authorizedStatus, Long authorizedUserId) {
        this.id = id;
        this.name = name;
        this.authorizedStatus = authorizedStatus;
        this.authorizedUserId = authorizedUserId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAuthorizedStatus() {
        return authorizedStatus;
    }

    public void setAuthorizedStatus(boolean authorizedStatus) {
        this.authorizedStatus = authorizedStatus;
    }

    public Long getAuthorizedUserId() {
        return authorizedUserId;
    }

    public void setAuthorizedUserId(Long authorizedUserId) {
        this.authorizedUserId = authorizedUserId;
    }

}
