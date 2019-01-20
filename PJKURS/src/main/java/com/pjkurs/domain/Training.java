package com.pjkurs.domain;

import java.sql.Date;
import java.util.List;

public class Training extends DBObject {

    public Integer id;
    public Integer status_id;
    public Integer course_id;
    private Course course;

    // Lista wykładowców
    private List<Teachers> teachersList;
    // data rozpoczecia
    public Date start_date;
    public Date end_date;

    // data spotkania organizacyjnego
    // status szkolenia (zakonczone, trwajaca, oczekuje na spotkanie organizacyjne)
    private TrainingStatus trainingStatus;

    //
    public TrainingStatus getTrainingStatus() {
        return trainingStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Teachers> getTeachersList() {
        return teachersList;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setTeachersList(List<Teachers> teachersList) {
        this.teachersList = teachersList;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public void setTrainingStatus(TrainingStatus trainingStatus) {
        this.trainingStatus = trainingStatus;
    }

    public Integer getTrainingStatusId() {
        return status_id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
