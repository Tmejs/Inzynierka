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
package com.pjkurs.db;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.pjkurs.domain.Appusers;
import com.pjkurs.domain.ArchiveCourse;
import com.pjkurs.domain.CalendarEvent;
import com.pjkurs.domain.Category;
import com.pjkurs.domain.Client;
import com.pjkurs.domain.Course;
import com.pjkurs.domain.CourseStatus;
import com.pjkurs.domain.DeaneryUser;
import com.pjkurs.domain.Discount;
import com.pjkurs.domain.GraduatedCourse;
import com.pjkurs.domain.MyCourse;
import com.pjkurs.domain.Teachers;
import com.pjkurs.domain.Training;
import com.pjkurs.domain.TrainingFile;

/**
 *
 * @author Tmejs
 */
public interface InterfacePjkursDataProvider {

    /*
    Operacje z klientem
     */
    Boolean registerNewUser(Appusers client);

    Boolean updateClientInTraining(Client client, Training training);

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

    public Appusers getUser(Integer email);

    CourseStatus getStatusById(Integer statusId);

    public List<Category> getCategories();

    public Boolean isUserSignedToCourse(String email, Integer courseId);

    public List<Course> getAllCourses();


    public void addNewCategory(String string, String string0);

    public void deleteCategory(Category selectedCategory);

    public void deleteCategoryFromCourse(Integer courseId, Long category_id);
    
    public void addCategoryToCourse(Integer courseId, Long categoryId);

    Category getCategoryById(Long id);

    void updateAppuser(Appusers editedUser);

    void deleteCientFromCourse(Appusers appUser, Course course);

    List<CourseStatus> getCourseStatuses();

    void updateDiscount(Discount editedDiscount);

    List<Training> getOpenedTrainings();

    Discount getDiscountFor(Appusers appuser, Integer trainignId);

    Boolean addAplicationForDiscount(Discount discount);

    List<Discount> getAwaitingDiscounts();

    List<? extends Training> getTrainingsForCourse(Course course);

    Boolean createNewTrainingFromCourse(Course course);

    List<Teachers> getTeachersForTraining(Training l);

    List<CalendarEvent> getCalendarTermsFor(Training training);

    Boolean addNewCalendarEvent(Integer id, CalendarEvent calendarEvent);

    Boolean deteteTrainingTerm(CalendarEvent c);

    List<Training> getUserTrainigs(Appusers loggedUser);

    Boolean deleteTeacherFromTraining(Teachers teacher, Training training);

    Boolean addTeacherToTraining(Teachers teacher, Training training);

    List<Teachers> getTeachers();

    List<Client> getClientsForTraining(Training training);

    void deleteClientFromTraining(Client client, Training training);

    Boolean isUserTeacher(Appusers p);

    void setTeacherStatus(Appusers editedUser, Boolean value, String typeOfContract);

    List<Training> getTrainingsForTeacher(Teachers selectedTeacher);

    List<TrainingFile> getFilesForTraining(Training training);

    void addTrainingFile(TrainingFile trainingFile);

    void deleteFile(TrainingFile trainingFile);

    List<Category> getCategoiresByCourseId(Integer coursId);

    Training getTraining(Integer training_id);

    List<Appusers> getCourseParticipants(Integer coursId);

    String getTeacherBaseContrat(Appusers selectedUser);

    Boolean loginAdmin(String login, String password);

    DeaneryUser getDeaneryUser(String email);

    List<DeaneryUser> getDeaneryUsers();

    void setAdminGrant(DeaneryUser p, Boolean value);

    void deleteDeaneryUser(DeaneryUser p);

    void addNewDeaneryEployee(String email, String password, Boolean adminGrant);

    void updateDeaneryUser(DeaneryUser deaneryUser);

    void setDiscount(Appusers p, Course course, Integer intValue);

    void setFileInCourse(String fileName, Course course);

    Integer getTeacherTimeInTraining(Teachers selectedTeacher, Integer trainingId);

    void updateTimeOfTeacherInCourse(Integer number , Teachers selectedTeacher, Integer trainingId);

    void addGraduation(Client c, Training training, String file);

    void endTraining(Training training);

    List<GraduatedCourse> getGraduatedCourses(Appusers loggedUser);

    List<Integer> getTrainingsIds(String startDate, String endDate);

    List<Integer> getUsersIdsCreated(String strinEndDate, String strinEndDate1);

    Integer getIncomeForTraining(Training t);

    Integer getCountOfParticipantsForCourse(Training t);

    Integer getCountOfGraduates(Training t);

    void deleteUser(Appusers p);
}
