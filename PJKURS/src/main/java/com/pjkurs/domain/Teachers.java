package com.pjkurs.domain;

public class Teachers extends Appusers{

    public Integer teacher_id;
    public String typeOfWork;

    @Override
    public final String getPassword() {
        throw new UnsupportedOperationException();
    }

    public Integer getTeacher_id() {
        return teacher_id;
    }

    public String getTypeOfWork() {
        return typeOfWork;
    }
}
