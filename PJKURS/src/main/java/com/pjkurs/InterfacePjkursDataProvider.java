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
package com.pjkurs;

import com.pjkurs.domain.*;

import java.util.List;

/**
 *
 * @author Tmejs
 */
public interface InterfacePjkursDataProvider {

    /*
    Operacje z klientem
     */
    Boolean registerNewClient(Client client);

    Boolean updateClient(Client client);

    Boolean loginClient(String login, String password);

    Boolean checkDoEmailOcuppied(String login);

    /*
    Operacje związane z przypisaniem klienta do kursu
     */
    Boolean addClientToCourse(String clientName, Course course);

    /*
    Operacje zwiazane z kursami
     */
    List<Course> getAvalibleCourses();

    List<MyCourse> getMyCourses(String userEmail);

    List<ArchiveCourse> getArchiveCourses();

    Boolean addnewCourse(Course newCourse);

    Boolean updateCourse(Course course);

    Course getCourse(Integer courseId);

    public List<Appusers> getUsers();

    public Appusers getUser(String email);

    CourseStatus getStatusById(Integer statusId);

    public List<Category> getCategories();

    public Boolean isUserSignedToCourse(String email, Integer courseId);

    public List<Course> getAllCourses();

    public List<SubCategory> getSubCategories();

    public void addNewCategory(String string, String string0);

    public void deleteCategory(Category selectedCategory);

    public void deleteSubCategory(SubCategory selectedSubCategory);

    public void addNewSubCategory(String string, String string0);

    public void deleteSubCategoryFromCourse(Integer courseId, Long category_id);
    
     public List<SubCategory> getSubCategorysByCourseId(Integer courseId);

    public Boolean addSubCategoryToCourse(Integer id, Long id0);

    void addSubCategoryToCategory(SubCategory subCategory, Category category);

    Category getCategoryById(Long id);

    void deleteSubcategoryFromCategory(SubCategory subCategory, Category category);

    SubCategory getSubCategory(Long id);

    void updateAppuser(Appusers editedUser);

    void deleteCientFromCourse(Appusers appUser, Course course);

    List<CourseStatus> getCourseStatuses();

    List<Discount> getDicsounts();

    void updateDiscount(Discount editedDiscount);

    void addDiscount(Discount discount);

}
