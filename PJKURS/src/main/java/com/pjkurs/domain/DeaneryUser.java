package com.pjkurs.domain;

public class DeaneryUser extends DBObject{

    public Integer id;
    public String email;
    public String password;
    public Boolean admin_grant;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin_grant() {
        return admin_grant;
    }

    public void setAdmin_grant(Boolean admin_grant) {
        this.admin_grant = admin_grant;
    }
}
