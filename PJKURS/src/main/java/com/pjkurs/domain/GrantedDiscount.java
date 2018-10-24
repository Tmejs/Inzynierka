package com.pjkurs.domain;


public class GrantedDiscount extends Discount {

    public Integer appusers_id;
    public Boolean isConfirmed ;
    public String userDescription;
    public String grantedDescription;
    public String email;

    public Integer getAppusers_id() {
        return appusers_id;
    }

    public Boolean getConfirmed() {
        return isConfirmed;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public String getGrantedDescription() {
        return grantedDescription;
    }

    public String getUserEmail() {
        return email;
    }
}
