package com.pearlyadana.rakhapuraapp.model.response;


import java.io.Serializable;

public class ApplicationUserDto implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private String loginUserName;

    private UserRoleDto role;

    public ApplicationUserDto() {
    }

    public ApplicationUserDto(Long id, String firstName, String lastName, String loginUserName, UserRoleDto role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.loginUserName = loginUserName;
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

    public UserRoleDto getRole() {
        return role;
    }

    public void setRole(UserRoleDto role) {
        this.role = role;
    }

}
