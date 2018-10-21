/*
 * Copyright (C) 2018 Tmejs
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.pjkurs.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tmejs
 */
public class Course extends DBObject {

    public Integer id;
    public String name;
    public String description;
    private String lecturer;
    public Long paricipants;
    public Integer statusId;
    private List<SubCategory> subcategoryList = new ArrayList<>();
    private CourseStatus courseStatus;
    public Integer minimumParticipants;
    public Double price;


    public Course() {
    }

    public Course(Course newCourse) {
        this.id=newCourse.id;
        this.name=newCourse.name;
        this.description=newCourse.description;
        this.lecturer=newCourse.lecturer;
        this.paricipants=newCourse.paricipants;
        this.statusId=newCourse.statusId;
        this.subcategoryList=newCourse.subcategoryList;
        this.courseStatus=newCourse.courseStatus;
        this.minimumParticipants=newCourse.minimumParticipants;
        this.price=newCourse.price;
    }

    public void setSubcategoryList(List<SubCategory> subcategoryList) {
        this.subcategoryList = subcategoryList;
    }

    public List<SubCategory> getSubcategoryList() {
        return subcategoryList;
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getLecturer() {
        return lecturer;
    }

    public Long getParicipants() {
        return paricipants;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public String getCourseStatusAsString() {
        return courseStatus != null ? courseStatus.name : "";
    }

    public CourseStatus getCourseStatus(){
        return courseStatus;
    }

    public void setCourseStatus(CourseStatus statusById) {
        courseStatus=statusById;
    }

    public Double getPrice() {
        return price;
    }
    }
