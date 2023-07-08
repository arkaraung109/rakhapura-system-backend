package com.pearlyadana.rakhapuraapp.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid-hibernate-generator")
    private UUID id;

    @Column(name = "reg_date", length = 30)
    private String regDate;

    @Column(name = "name", length = 300)
    private String name;

    @Column(name = "dob", length = 30)
    private String dob;

    @Column(name = "sex", length = 6)
    private String sex;

    @Column(name = "nationality", length = 300)
    private String nationality;

    @Column(name = "nrc", length = 300)
    private String nrc;

    @Column(name = "father_name", length = 300)
    private String fatherName;

    @Column(name = "mother_name", length = 300)
    private String motherName;

    @Column(name = "address", length = 300)
    private String address;

    @Column(name = "monastery_name", length = 300)
    private String monasteryName;

    @Column(name = "monastery_headmaster", length = 300)
    private String monasteryHeadmaster;

    @Column(name = "monastery_township", length = 300)
    private String monasteryTownship;

    @Column(name = "createdTimestamp")
    @CreationTimestamp
    private LocalDateTime createdTimestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    public Student() {
    }

    public Student(UUID id, String regDate, String name, String dob, String sex, String nationality, String nrc, String fatherName, String motherName, String address, String monasteryName, String monasteryHeadmaster, String monasteryTownship, LocalDateTime createdTimestamp, Region region) {
        this.id = id;
        this.regDate = regDate;
        this.name = name;
        this.dob = dob;
        this.sex = sex;
        this.nationality = nationality;
        this.nrc = nrc;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.address = address;
        this.monasteryName = monasteryName;
        this.monasteryHeadmaster = monasteryHeadmaster;
        this.monasteryTownship = monasteryTownship;
        this.createdTimestamp = createdTimestamp;
        this.region = region;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMonasteryName() {
        return monasteryName;
    }

    public void setMonasteryName(String monasteryName) {
        this.monasteryName = monasteryName;
    }

    public String getMonasteryHeadmaster() {
        return monasteryHeadmaster;
    }

    public void setMonasteryHeadmaster(String monasteryHeadmaster) {
        this.monasteryHeadmaster = monasteryHeadmaster;
    }

    public String getMonasteryTownship() {
        return monasteryTownship;
    }

    public void setMonasteryTownship(String monasteryTownship) {
        this.monasteryTownship = monasteryTownship;
    }

    public LocalDateTime getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

}
