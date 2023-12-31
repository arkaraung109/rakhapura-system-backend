package com.pearlyadana.rakhapuraapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_table")
public class ApplicationUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name", length = 300)
    private String firstName;

    @Column(name = "last_name", length = 300)
    private String lastName;

    @Column(name = "user_name", length = 100)
    private String loginUserName;

    @Column(name = "password", length = 300)
    private String password;

    @JoinColumn(name = "role_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserRole role;

    public ApplicationUser() {
    }

    public ApplicationUser(Long id, String firstName, String lastName, String loginUserName, String password, UserRole role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.loginUserName = loginUserName;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

}
