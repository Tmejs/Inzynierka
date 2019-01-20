package com.pjkurs.domain;


public class Discount extends  DBObject{

    public Integer id;
    public Integer appusers_id;
    public Boolean isConfirmed ;
    public String userDescription;
    public String grantedDescription;
    public Integer trening_id;
    public Integer value;
    public Boolean is_percentValue;

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
}
