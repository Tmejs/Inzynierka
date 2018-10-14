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
 *
 * @author Tmejs
 */
public class Course extends DBObject {
    
    public enum CourseStatus {
        NOWY(2),
        TRWAJACY(3),
        ARCHIWALNY(1);
        
        Integer value;
        
        private CourseStatus(Integer value) {
            this.value = value;
        }
        
        public static String getNameById(Integer id) {
            for (CourseStatus status : CourseStatus.values()) {
                if (status.value.equals(id)) {
                    return status.name();
                }
            }
            return null;
        }
        
        public static CourseStatus getCourseById(Integer id) {
            for (CourseStatus status : CourseStatus.values()) {
                if (status.value.equals(id)) {
                    return status;
                }
            }
            return null;
        }
    }
    
    public Integer id;
    public String name;
    public String description;
    private String lecturer;
    public Long paricipants;
    public Integer statusId;
    private List<CourseSubCategory> subcategoryList = new ArrayList<>();
    private CourseStatus courseStatus;
    public Integer minimumParticipants;

    public void setSubcategoryList(List<CourseSubCategory> subcategoryList) {
        this.subcategoryList = subcategoryList;
    }

    public List<CourseSubCategory> getSubcategoryList() {
        return subcategoryList;
    }
    
    
    public CourseStatus getCourseStatus() {
        return CourseStatus.getCourseById(statusId);
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
    
    public String getCurseStatusAsString() {
        return CourseStatus.getNameById(getStatusId());
    }
    
}
