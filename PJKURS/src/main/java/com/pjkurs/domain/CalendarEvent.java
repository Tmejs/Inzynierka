package com.pjkurs.domain;

import java.sql.Timestamp;

public class CalendarEvent extends DBObject {

    public Integer id;
    public Integer training_id;
    public String name;
    public String description;
    public Timestamp start_date;
    public Timestamp end_date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTraining_id() {
        return training_id;
    }

    public void setTraining_id(Integer training_id) {
        this.training_id = training_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStart_date() {
        return start_date;
    }

    public void setStart_date(Timestamp start_date) {
        this.start_date = start_date;
    }

    public Timestamp getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Timestamp end_date) {
        this.end_date = end_date;
    }
}
