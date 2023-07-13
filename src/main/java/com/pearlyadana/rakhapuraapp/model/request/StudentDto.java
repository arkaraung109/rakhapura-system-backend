package com.pearlyadana.rakhapuraapp.model.request;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class StudentDto implements Serializable {

    private UUID id;

    private String regDate;

    private String name;

    private String dob;

    private String sex;

    private String nationality;

    private String nrc;

    private String fatherName;

    private String motherName;

    private String address;

    private String monasteryName;

    private String monasteryHeadmaster;

    private String monasteryTownship;

    private LocalDateTime createdTimestamp;

    private RegionDto region;

    public StudentDto() {
    }

    public StudentDto(UUID id, String regDate, String name, String dob, String sex, String nationality, String nrc, String fatherName, String motherName, String address, String monasteryName, String monasteryHeadmaster, String monasteryTownship, LocalDateTime createdTimestamp, RegionDto region) {
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

    public RegionDto getRegion() {
        return region;
    }

    public void setRegion(RegionDto region) {
        this.region = region;
    }

}
