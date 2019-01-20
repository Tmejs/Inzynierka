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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.pjkurs.domain.Appusers;
import com.pjkurs.domain.ArchiveCourse;
import com.pjkurs.domain.BigDecimalValue;
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
import com.pjkurs.domain.TrainingStatus;
import com.pjkurs.domain.Value;
import com.pjkurs.usables.Words;

/**
 * @author Tmejs
 */
public class DbDataProvider implements InterfacePjkursDataProvider {

    private final DBConnector dbConnector;

    @Override
    public List<Course> getAllCourses() {
        String buildedFunction
                = "SELECT * FROM pjkursdb.kursy_v";
        try {
            List<Course> list = dbConnector.getMappedArrayList(new Course(), buildedFunction);
            list.stream().forEach((t) -> {
                if (t.statusId != null) {
                    t.setCourseStatus(getStatusById(t.statusId));
                }
                t.setCategories(getCategoiresByCourseId(t.id));
            });
            return list;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy mapowaniu usera", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public CourseStatus getStatusById(Integer statusId) {
        String buildedFunction
                = "SELECT * FROM pjkursdb.courses_statuses where id =" + statusId;
        try {
            List<CourseStatus> list = dbConnector
                    .getMappedArrayList(new CourseStatus(), buildedFunction);
            return list.get(0);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getCourseStatuses", exception);
        }
        return null;
    }

    public DbDataProvider() {
        this.dbConnector = new DBConnector();
    }

    public DbDataProvider(String db, String login, String password) throws Exception {
        this.dbConnector = new DBConnector();
        connectToDB(db, login, password);
    }

    public final Boolean connectToDB(String db, String login, String password) throws Exception {
        return dbConnector.connect(db, login, password);
    }

    @Override
    public List<Category> getCategories() {
        String buildedFunction
                = "select * from pjkursdb.categorries";
        try {
            return dbConnector.getMappedArrayList(new Category(), buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy mapowaniu usera", exception);
        }
        return new ArrayList<>();

    }

    @Override
    public Boolean registerNewUser(Appusers user) {
        String buildedFunction
                =
                "insert into `pjkursdb`.`appusers` (email,password, name, surname, "
                        + "contact_number) values  ('" + user.email
                        + "','" + user.password
                        + "','" + user.name
                        + "','" + user.surname
                        + "','" + user.contact_number + "'"
                        + ")";
        try {
            dbConnector.executeUpdate(buildedFunction);
            return true;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad registerNewUser", exception);
        }
        return false;
    }

    @Override
    public List<Appusers> getUsers() {
        String buildedFunction
                = "select * from pjkursdb.appusers";
        try {
            return dbConnector.getMappedArrayList(new Appusers(), buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy mapowaniu usera", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public Appusers getUser(Integer id) {
        String buildedFunction
                = "select * from pjkursdb.appusers where id=" + id + "";
        try {
            return (Appusers) dbConnector.getMappedArrayList(new Appusers(), buildedFunction)
                    .get(0);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy mapowaniu usera", exception);
        }
        return null;
    }

    @Override
    public Appusers getUser(String email) {
        String buildedFunction
                = "select * from pjkursdb.appusers where email='" + email + "'";
        try {
            return (Appusers) dbConnector.getMappedArrayList(new Appusers(), buildedFunction)
                    .get(0);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy mapowaniu usera", exception);
        }
        return null;
    }

    @Override
    public Course getCourse(Integer courseId) {
        String buildedFunction
                = "SELECT * FROM pjkursdb.kursy_v where id=" + courseId;
        try {
            Course course = (Course) dbConnector.getMappedArrayList(new Course(), buildedFunction)
                    .get(0);
            if (course.getStatusId() != null) {
                course.setCourseStatus(getStatusById(course.getStatusId()));
            }
            return course;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy mapowaniu usera", exception);
        }
        return null;
    }

    @Override
    public Boolean isUserSignedToCourse(String email, Integer courseId) {
        String buildedFunction
                = "pjkursdb.czy_zapisany('" + email + "', " + courseId + ")";
        return dbConnector.getBooleanFunctionValue(buildedFunction);
    }

    @Override
    public Boolean updateClientInTraining(Client client, Training training) {
        String func =
                "update pjkursdb.appusers_trainigs set is_paid=" + client.getPaid() + ", "
                        + "is_contract_signed= " + client.getIs_contract_signed()
                        + " where "
                        + "training_id=" + training.getId() + " and appuser_id=" + client.getId();
        try {
            dbConnector.executeUpdate(func);
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.SEVERE, "updateCourse", e);
        }
        return true;

    }

    @Override
    public Boolean loginClient(String login, String password) {
        ResultSet resultSet = null;
        String buildedFunction
                = "pjkursdb.zaloguj_usera('" + login + "', '" + password + "')";
        return dbConnector.getBooleanFunctionValue(buildedFunction);
    }

    @Override
    public Boolean addClientToCourse(String clientName, Course course) {
        ResultSet resultSet = null;
        String buildedFunction
                = "pjkursdb.zapisz_do_kursu('" + clientName + "', '" + course.id + "')";
        return dbConnector.getBooleanFunctionValue(buildedFunction);
    }

    @Override
    public List<Course> getAvalibleCourses() {
        //query
        String sqlQuery = Words.SQL_SELECT_COURSES_QUERY;
        try {
            List<Course> list = dbConnector.getMappedArrayList(new Course(), sqlQuery);
            list.stream().forEach((course) -> {
                if (course.getStatusId() != null) {
                    course.setCourseStatus(getStatusById(course.getStatusId()));
                }
                course.setCategories(getCategoiresByCourseId(course.id));
            });
            return list;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.SEVERE, "Blad przy mapowaniu Kursu", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public List<MyCourse> getMyCourses(String userEmail) {
        String sqlQuery = Words.SQL_SELECT_MY_COURSES_QUERY + " where username='" + userEmail + "'";
        Logger.getGlobal().log(Level.SEVERE, sqlQuery);
        try {
            List<MyCourse> cousres = dbConnector.getMappedArrayList(new MyCourse(), sqlQuery);
            cousres.stream().forEach(((t) -> {
                if (t.getStatusId() != null) {
                    t.setCourseStatus(getStatusById(t.getStatusId()));
                }
                t.setCategories(getCategoiresByCourseId(t.id));
            }));
            return cousres;

        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.SEVERE, "Blad przy mapowaniu Kursu", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public List<ArchiveCourse> getArchiveCourses() {
        //query
        String sqlQuery = Words.SQL_SELECT_ARCHIVE_COURSES_QUERY;
        try {
            List<ArchiveCourse> list = dbConnector.getMappedArrayList(new ArchiveCourse(),
                    sqlQuery);

            list.forEach(course -> {
                course.setCategoryList(getCategoiresByCourseId(course.id));
            });
            return list;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.SEVERE, "Blad przy mapowaniu Kursu", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public Boolean addnewCourse(Course newCourse) {
        String query = "insert into pjkursdb.courses(name,description) " +
                "values ('" + newCourse.name + "','" + newCourse.description + "')";
        try {
            dbConnector.executeUpdate(query);
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.SEVERE, "Błąd przy addnewCourse", e);
        }
        return true;
    }

    @Override
    public Boolean updateCourse(Course course) {
        String func = "UPDATE pjkursdb.courses " +
                "SET " +
                "name = '" + course.name + "', " +
                "description = '" + course.description + "'," +
                "statusId = " + course.statusId + ", " +
                "price =" + course.getPrice() +
                ", discountPrice=" + course.discountPrice +
                " WHERE id = " + course.getId();
        try {
            dbConnector.executeUpdate(func);
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.SEVERE, "updateCourse", e);
        }
        return true;
    }

    @Override
    public Boolean checkDoEmailOcuppied(String login) {
        String buildedFunction
                = "pjkursdb.czy_zarejestrowany_email('" + login + "')";
        return dbConnector.getBooleanFunctionValue(buildedFunction);
    }

    @Override
    public void addNewCategory(String string, String string0) {
        String query =
                "insert into pjkursdb.categorries(name,description) values ('" + string + "','"
                        + string0 + "')";
        try {
            dbConnector.executeUpdate(query);
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.SEVERE, "Błąd przy insert kategori", e);
        }
    }

    @Override
    public void deleteCategory(Category selectedCategory) {
        String query = "delete from pjkursdb.categorries where id =" + selectedCategory.id;
        try {
            dbConnector.executeStatement(query);
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "Błąd przy deleteCategory", e);
        }

    }

    @Override
    public Category getCategoryById(Long id) {
        String buildedFunction
                = "select * from pjkursdb.categorries where id=" + id;
        try {
            List<Category> list = dbConnector.getMappedArrayList(new Category(), buildedFunction);
            if (!list.isEmpty()) {
                return list.get(0);
            }
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy mapowaniu usera", exception);
        }
        return null;
    }

    @Override
    public void updateAppuser(Appusers editedUser) {
        String buildedFunction =
                "UPDATE pjkursdb.appusers SET  id =" + editedUser.id
                        + ",  email  " + "='" + editedUser.email
                        + "',  password  ='" + editedUser.password
                        + "', name  = '" + editedUser.name
                        + "',  surname  ='" + editedUser.surname
                        + "',  birth_date  = " + toSqlDate(editedUser.birth_date)
                        + ",  contact_number  = '" + editedUser.contact_number
                        + "', place_of_birth ='" + editedUser.place_of_birth
                        +"', isActive =" + (editedUser.isActive != null ?
                        editedUser.isActive.toString() : "false")+ " " + "WHERE  id  =" + editedUser.id;
        try {
            dbConnector.executeStatement(buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy updateAppuser", exception);
        }
    }

    private static String toSqlDate(Date date) {
        if (date == null) {
            return "null";
        } else {
            return "'" + date + "' ";
        }
    }

    @Override
    public void deleteCientFromCourse(Appusers appUser, Course course) {
        String buildedFunction =
                "delete FROM pjkursdb.appusers_courses where user_id =" + appUser.id + " and " +
                        "course_id=" + course.id;
        try {
            dbConnector.executeStatement(buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "deleteCientFromCourse", exception);
        }
    }

    @Override
    public List<CourseStatus> getCourseStatuses() {
        String buildedFunction
                = "SELECT * FROM pjkursdb.courses_statuses";
        try {
            List<CourseStatus> list = dbConnector
                    .getMappedArrayList(new CourseStatus(), buildedFunction);
            return list;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getCourseStatuses", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public Boolean addAplicationForDiscount(Discount discount) {
        String buidledFunction = "INSERT INTO `pjkursdb`.`discounts` ("
                + "appusers_id, userDescription, trening_id) "
                + "values ("
                + discount.appusers_id + ",'"
                + discount.userDescription + "',"
                + discount.trening_id
                + ") ";
        try {
            dbConnector.executeStatement(buidledFunction);
            return true;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "addAplicationForDiscount", exception);
            return false;
        }
    }

    @Override
    public List<Discount> getAwaitingDiscounts() {
        String buildedFunction
                =
                "SELECT * from pjkursdb.discounts";
        try {
            List<Discount> list = dbConnector
                    .getMappedArrayList(new Discount(), buildedFunction);

            return list.stream().filter(d -> d.isConfirmed == null).collect(Collectors.toList());
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getCourseStatuses", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public Boolean createNewTrainingFromCourse(Course course) {
        String buildedFunction
                = "pjkursdb.new_training_from_course('" + course.id + "')";
        return dbConnector.getBooleanFunctionValue(buildedFunction);
    }

    @Override
    public void updateDiscount(Discount editedDiscount) {
        String buidledFunction =
                "UPDATE  pjkursdb . discounts SET isConfirmed  = "
                        + editedDiscount.isConfirmed
                        + ", grantedDescription  = '"
                        + editedDiscount.grantedDescription
                        + "', value  = "
                        + editedDiscount.value
                        + ", is_percentValue  = "
                        + editedDiscount.is_percentValue
                        + " WHERE  id  = " + editedDiscount.id;
        try {
            dbConnector.executeStatement(buidledFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "updateDiscount", exception);
        }
    }

    @Override
    public Training getTraining(Integer training_id) {
        String buildedFunction
                =
                "select * from pjkursdb.trainings where id = " + training_id;
        try {
            List<Training> list = dbConnector
                    .getMappedArrayList(new Training(), buildedFunction);

            if (!list.isEmpty()) {
                list.forEach(l -> {
                            l.setTeachersList(getTeachersForTraining(l));
                            if (l.getTrainingStatusId() != null) {
                                l.setTrainingStatus(getTrainingStatusFor(l.getTrainingStatusId()));
                            }
                            l.setCourse(getCourse(l.course_id));
                        }
                );
                return list.get(0);
            } else {
                return null;
            }

        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getCourseStatuses", exception);
        }
        return null;
    }

    @Override
    public List<Training> getOpenedTrainings() {
        String buildedFunction
                =
                "select * from pjkursdb.trainings where status_id=1";
        try {
            List<Training> list = dbConnector
                    .getMappedArrayList(new Training(), buildedFunction);

            if (!list.isEmpty()) {
                list.forEach(l -> {
                            l.setTeachersList(getTeachersForTraining(l));
                            if (l.getTrainingStatusId() != null) {
                                l.setTrainingStatus(getTrainingStatusFor(l.getTrainingStatusId()));
                            }
                            l.setCourse(getCourse(l.course_id));
                        }
                );
                return list;
            } else {
                return null;
            }

        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getCourseStatuses", exception);
        }
        return null;
    }

    @Override
    public Discount getDiscountFor(Appusers appuser, Integer trainignId) {
        String buidledFunction =
                "SELECT * from pjkursdb.discounts where appusers_id = " + appuser.getId()
                        + " and trening_id = " + trainignId;

        try {
            List<Discount> list = dbConnector
                    .getMappedArrayList(new Discount(), buidledFunction);
            if (!list.isEmpty()) {
                return list.get(0);
            }
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getCourseStatuses", exception);
        }
        return null;
    }

    @Override
    public List<? extends Training> getTrainingsForCourse(Course course) {
        String buildedFunction
                =
                "select * from pjkursdb.trainings where course_id = " + course.getId() + " and "
                        + "status_id=1";
        try {
            List<Training> list = dbConnector
                    .getMappedArrayList(new Training(), buildedFunction);

            list.forEach(l -> {
                        l.setTeachersList(getTeachersForTraining(l));
                        if (l.getTrainingStatusId() != null) {
                            l.setTrainingStatus(getTrainingStatusFor(l.getTrainingStatusId()));
                        }
                        l.setCourse(getCourse(l.course_id));
                    }
            );
            return list;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getCourseStatuses", exception);
        }
        return new ArrayList<>();
    }

    public TrainingStatus getTrainingStatus() {
        String buildedFunction = "select * from `pjkursdb`.`training_statuses`";
        try {
            List<TrainingStatus> list = dbConnector
                    .getMappedArrayList(new TrainingStatus(), buildedFunction);

            if (!list.isEmpty()) {
                return list.get(0);
            }
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getCourseStatuses", exception);
        }
        return null;
    }

    public TrainingStatus getTrainingStatusFor(Integer id) {
        String buildedFunction = "select * from `pjkursdb`.`training_statuses` where id = " + id;
        try {
            List<TrainingStatus> list = dbConnector
                    .getMappedArrayList(new TrainingStatus(), buildedFunction);

            if (!list.isEmpty()) {
                return list.get(0);
            }
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getCourseStatuses", exception);

        }
        return null;
    }

    @Override
    public List<Teachers> getTeachersForTraining(Training l) {
        String buildedFunction
                = "select * from `pjkursdb`.`teachers_trainings_v` "
                + "where training_id = " + l.getId();
        try {
            List<Teachers> list = dbConnector
                    .getMappedArrayList(new Teachers(), buildedFunction);
            return list;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getTeachersForTraining", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public List<CalendarEvent> getCalendarTermsFor(Training training) {
        String buildedFunction
                =
                "select * from `pjkursdb`.`training_terms` where training_id = " + training.getId();
        try {
            List<CalendarEvent> list = dbConnector
                    .getMappedArrayList(new CalendarEvent(), buildedFunction);

            return list;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getCourseStatuses", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public Boolean addNewCalendarEvent(Integer id, CalendarEvent calendarEvent) {
        String query =
                "insert into pjkursdb.training_terms(training_id, name, description, start_date, "
                        + "end_date) values ("
                        + id + ",'" + calendarEvent.getName() + "','" + calendarEvent
                        .getDescription() + "'," + toSqlDate(calendarEvent.getStart_date()) + ", "
                        + toSqlDate(calendarEvent.getEnd_date()) + ")";
        try {
            dbConnector.executeUpdate(query);
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.SEVERE, "Błąd przy insert kategori", e);
            return false;
        }
        return true;
    }

    @Override
    public Boolean deteteTrainingTerm(CalendarEvent c) {
        String query =
                "delete from pjkursdb.training_terms where id=" + c.getId();
        try {
            dbConnector.executeUpdate(query);
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.SEVERE, "Błąd przy delete training", e);
            return false;
        }
        return true;
    }

    @Override
    public List<Training> getUserTrainigs(Appusers loggedUser) {
        String buildedFunction
                =
                "select a.* from `pjkursdb`.`trainings` a join  "
                        + "pjkursdb.appusers_trainigs b on a.id = b.training_id where b"
                        + ".appuser_id =" + loggedUser.getId() + " and a.status_id = 1";
        try {
            List<Training> list = dbConnector
                    .getMappedArrayList(new Training(), buildedFunction);
            list.forEach(l -> {
                if (l.getTrainingStatusId() != null) {
                    l.setTrainingStatus(getTrainingStatusFor(l.getTrainingStatusId()));
                }
            });
            return list;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getUserTrainigs", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public Boolean deleteTeacherFromTraining(Teachers teacher, Training training) {
        String query =
                "delete from pjkursdb.teachers_trainings where training_id=" + training.getId() +
                        " and teacher_id=" + teacher.getTeacher_id();
        try {
            dbConnector.executeUpdate(query);
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.SEVERE, "Błąd przy delete teacher from training", e);
            return false;
        }
        return true;
    }

    @Override
    public Boolean addTeacherToTraining(Teachers teacher, Training training) {
        String query =
                "insert into pjkursdb.teachers_trainings(training_id, teacher_id ) values("
                        + training.getId() +
                        ", " + teacher.getTeacher_id() + ")";
        try {
            dbConnector.executeUpdate(query);
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.SEVERE, "Błąd przy insert teachers", e);
            return false;
        }
        return true;
    }

    @Override
    public List<Teachers> getTeachers() {
        String buildedFunction
                =
                "select a.*, t.id as teacher_id, t.typeOfWork from pjkursdb.appusers a right join"
                        + " pjkursdb"
                        + ".teachers t on a.id = t.appUserId";
        try {
            List<Teachers> list = dbConnector
                    .getMappedArrayList(new Teachers(), buildedFunction);

            return list;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getCourseStatuses", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Client> getClientsForTraining(Training training) {
        String buildedFunction
                = "select ap.*, at.is_paid, at.is_contract_signed from pjkursdb.appusers ap "
                + "right join pjkursdb.appusers_trainigs at on ap.id = at.appuser_id "
                + " where at.training_id =" + training.getId();
        try {
            return dbConnector.getMappedArrayList(new Client(), buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy mapowaniu cleinta", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteClientFromTraining(Client client, Training training) {
        String function = "delete from pjkursdb.appusers_trainigs where appuser_id="
                + client.getId() + " and training_id=" + training.getId();
        try {
            dbConnector.executeUpdate(function);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy deleteClientFromTraining", exception);
        }
    }

    @Override
    public Boolean isUserTeacher(Appusers p) {
        String buildedFunction
                =
                "select a.*, t.id as teacher_id, t.typeOfWork from pjkursdb.appusers a right "
                        + "join pjkursdb"
                        + ".teachers t on a.id = t.appUserId where a.id=" + p.getId();
        try {
            List<Teachers> list = dbConnector
                    .getMappedArrayList(new Teachers(), buildedFunction);

            return list.size() > 0;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getCourseStatuses", exception);
        }
        return false;
    }

    @Override
    public void setTeacherStatus(Appusers editedUser, Boolean value, String typeOfContract) {
        Boolean iosUserTeacher = isUserTeacher(editedUser);
        String function = null;
        if (value) {
            if (!iosUserTeacher) {
                function =
                        "insert into pjkursdb.teachers (appUserId, typeOfWork) values(" + editedUser
                                .getId() + ","
                                + "'" + typeOfContract + "'"
                                + " )";
            } else {
                function =
                        "update pjkursdb.teachers set typeOfWork = "
                                + "'" + typeOfContract + "'"
                                + " where appUserId =" + editedUser.getId();
            }
        }

        if (!value && iosUserTeacher) {
            function = "delete from pjkursdb.teachers where appUserId =" + editedUser.getId();
        }

        if (function != null) {
            try {
                dbConnector.executeUpdate(function);
            } catch (Exception exception) {
                Logger.getLogger(this.getClass().getCanonicalName())
                        .log(Level.ALL, "Blad przy deleteClientFromTraining", exception);
            }
        }
    }

    @Override
    public List<Training> getTrainingsForTeacher(Teachers selectedTeacher) {
        String buildedFunction
                =
                "select tr.* from pjkursdb.trainings tr right join pjkursdb.teachers_trainings tt"
                        + " on tt.training_id=tr.id "
                        + "where tt.teacher_id = " + selectedTeacher.getTeacher_id()
                +   " and tr.status_id=1";
        try {
            List<Training> list = dbConnector
                    .getMappedArrayList(new Training(), buildedFunction);

            list.forEach(l -> {
                        l.setTeachersList(getTeachersForTraining(l));
                        if (l.getTrainingStatusId() != null) {
                            l.setTrainingStatus(getTrainingStatusFor(l.getTrainingStatusId()));
                        }
                    }
            );
            return list;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getCourseStatuses", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public List<TrainingFile> getFilesForTraining(Training training) {
        String buildedFunction
                =
                "select * from pjkursdb.trainings_files where training_id=" + training.getId();
        try {
            List<TrainingFile> list = dbConnector
                    .getMappedArrayList(new TrainingFile(), buildedFunction);

            return list;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getCourseStatuses", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public void addTrainingFile(TrainingFile trainingFile) {
        String buildedFunction
                =
                "insert into pjkursdb.trainings_files(training_id, path, description, name, "
                        + "visibleForUsers) "
                        + "values (" + trainingFile.getTraining_id() + ", '" + trainingFile
                        .getPath() + "','" + trainingFile.getDescription() +
                        "','" + trainingFile.getName() + "', " + trainingFile.getVisibleForUsers()
                        + ")";
        try {
            dbConnector.executeUpdate(buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy deleteClientFromTraining", exception);
        }
    }

    @Override
    public void deleteFile(TrainingFile trainingFile) {
        String buildedFunction
                =
                "delete from pjkursdb.trainings_files where id =" + trainingFile.getId();
        try {
            dbConnector.executeUpdate(buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy deleteClientFromTraining", exception);
        }
    }

    @Override
    public void deleteCategoryFromCourse(Integer courseId, Long category_id) {
        String buildedFunction
                =
                "delete from pjkursdb.courses_categories where cours_id =" + courseId + " and "
                        + "category_id = " + category_id;
        try {
            dbConnector.executeUpdate(buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy deleteCategoryFromCourse", exception);
        }
    }

    @Override
    public void addCategoryToCourse(Integer courseId, Long categoryId) {
        String buildedFunction
                = "insert into pjkursdb.courses_categories (course_id, category_id) values ("
                + courseId + "," + categoryId + ")";
        try {
            dbConnector.executeUpdate(buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy addCategoryToCourse", exception);
        }
    }

    @Override
    public List<Category> getCategoiresByCourseId(Integer coursId) {
        String buildedFunction =
                "SELECT * FROM pjkursdb.categorries  c "
                        + "left join pjkursdb.courses_categories cc on c.id = cc.category_id"
                        + " where cc.course_id = " + coursId;
        try {
            List<Category> list = dbConnector
                    .getMappedArrayList(new Category(), buildedFunction);

            return list;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getCategoiresByCourseId", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Appusers> getCourseParticipants(Integer coursId) {
        String buildedFunction
                = "select a.* from pjkursdb.appusers a "
                + "right join pjkursdb.appusers_courses b on a.id = b.user_id where b.course_id = "
                + coursId;
        try {
            return dbConnector.getMappedArrayList(new Appusers(), buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy mapowaniu usera", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public String getTeacherBaseContrat(Appusers selectedUser) {
        String buildedFunction
                =
                "select t.typeOfWork from pjkursdb.appusers a right "
                        + "join pjkursdb"
                        + ".teachers t on a.id = t.appUserId where a.id=" + selectedUser.getId();
        try {
            List<Teachers> list = dbConnector
                    .getMappedArrayList(new Teachers(), buildedFunction);
            if (list.size() > 0) {
                return list.get(0).getTypeOfWork();
            }
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getCourseStatuses", exception);
        }
        return null;
    }

    @Override
    public Boolean loginAdmin(String login, String password) {
        String buildedFunction
                =
                "select * from pjkursdb.deanery_users where email = '" + login
                        + "' and password = '" + password + "'";
        try {
            List<DeaneryUser> list = dbConnector
                    .getMappedArrayList(new DeaneryUser(), buildedFunction);
            return list.size() > 0;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getCourseStatuses", exception);
        }
        return false;
    }

    @Override
    public DeaneryUser getDeaneryUser(String email) {
        String buildedFunction
                = "select * from pjkursdb.deanery_users where email = '" + email + "'";
        try {
            List<DeaneryUser> list = dbConnector
                    .getMappedArrayList(new DeaneryUser(), buildedFunction);
            if (list.size() > 0) {
                return list.get(0);
            }
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getCourseStatuses", exception);
        }
        return null;
    }

    @Override
    public List<DeaneryUser> getDeaneryUsers() {
        String buildedFunction
                = "select * from pjkursdb.deanery_users ";
        try {
            List<DeaneryUser> list = dbConnector
                    .getMappedArrayList(new DeaneryUser(), buildedFunction);

            return list;

        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getCourseStatuses", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public void setAdminGrant(DeaneryUser p, Boolean value) {
        String buildedFunction
                =
                "update pjkursdb.deanery_users set admin_grant = " + value + " where id = " + p.id;
        try {
            dbConnector.executeUpdate(buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy setAdminGrant", exception);
        }
    }

    @Override
    public void deleteDeaneryUser(DeaneryUser p) {
        String buildedFunction
                =
                "delete from pjkursdb.deanery_users where id = " + p.id;
        try {
            dbConnector.executeUpdate(buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy deleteDeaneryUser", exception);
        }
    }

    @Override
    public void addNewDeaneryEployee(String email, String password, Boolean adminGrant) {
        String buildedFunction
                =
                "insert into pjkursdb.deanery_users(email,password, admin_grant) values ('" + email
                        + "', "
                        + "'" + password + "', " + adminGrant + ")";
        try {
            dbConnector.executeUpdate(buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy addNewDeaneryEployee", exception);
        }
    }

    @Override
    public void updateDeaneryUser(DeaneryUser deaneryUser) {
        String buildedFunction
                = "update pjkursdb.deanery_users set email = '" + deaneryUser.email + "', "
                + "password = '" + deaneryUser.getPassword() + " ', admin_grant= " + deaneryUser
                .getAdmin_grant() + " where "
                + "id"
                + " =" + deaneryUser.getId();
        try {
            dbConnector.executeUpdate(buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy updateDeaneryUser", exception);
        }
    }

    @Override
    public void setDiscount(Appusers p, Course course, Integer intValue) {
        String buildedFunction
                = "update pjkursdb.appusers_courses set discount= " + intValue + " where user_id = "
                + p.id + " and course_id = " + course.id;
        try {
            dbConnector.executeUpdate(buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy updateDeaneryUser", exception);
        }
    }

    @Override
    public void setFileInCourse(String fileName, Course course) {
        if (fileName == null) {
            fileName = "NULL";
        } else {
            fileName = "'" + fileName + "'";
        }

        String buildedFunction
                = "update pjkursdb.courses set description_file = " + fileName
                + " where id = "
                + course.id;

        try {
            dbConnector.executeUpdate(buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy setFileInCourse", exception);
        }
    }

    @Override
    public Integer getTeacherTimeInTraining(Teachers selectedTeacher, Integer trainingId) {
        String buildedFunction
                = "SELECT numberOfHours as value FROM pjkursdb.teachers_trainings where "
                + "training_id = " + trainingId + " and teacher_id =" + selectedTeacher
                .getTeacher_id();
        try {
            List<Value> list = dbConnector
                    .getMappedArrayList(new Value(), buildedFunction);

            if (list.size() > 0) {
                return list.get(0).value == null ? 0: list.get(0).value;
            }
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getTeacherTimeInTraining", exception);
        }
        return 0;
    }

    @Override
    public void updateTimeOfTeacherInCourse(Integer number, Teachers selectedTeacher,
            Integer trainingId) {
        String buildedFunction
                = "UPDATE `pjkursdb`.`teachers_trainings` SET numberOfHours = " + number + " WHERE "
                + "training_id =" + trainingId + " AND teacher_id = " + selectedTeacher
                .getTeacher_id();
        try {
            dbConnector.executeUpdate(buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy updateTimeOfTeacherInCourse", exception);
        }
    }

    @Override
    public void addGraduation(Client c, Training training, String file) {
        if (file == null) {
            file = "";
        }
        String buildedFunction
                = "INSERT INTO `pjkursdb`.`courses_graduates`"
                + "(`course_id`,"
                + "`appuser_id`,"
                + "`certificateFile`,"
                + "training_id) "
                + "VALUES "
                + "(" + training.course_id + ","
                + c.getId() + ","
                + "'" + file + "', " + training.id + ")";
        try {
            dbConnector.executeUpdate(buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy addGraduation", exception);
        }
    }

    @Override
    public void endTraining(Training training) {
        String buildedFunction
                = "UPDATE `pjkursdb`.`trainings` "
                + "SET `status_id` = 2, end_date = now() "
                + "WHERE `id` = " + training.getId();
        try {
            dbConnector.executeUpdate(buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy endTraining", exception);
        }
    }

    @Override
    public List<GraduatedCourse> getGraduatedCourses(Appusers loggedUser) {
        String buildedFunction
                = "select c.*,g.certificateFile, g.createDate as endDate from pjkursdb.courses c "
                + "right join pjkursdb.courses_graduates g on c.id = g.course_id "
                + "where g.appuser_id = " +
                loggedUser.getId();
        try {
            List<GraduatedCourse> list = dbConnector
                    .getMappedArrayList(new GraduatedCourse(), buildedFunction);

            return list;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getTeacherTimeInTraining", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Integer> getTrainingsIds(String startDate, String endDate) {
        String buildedFunction
                = "select id as value from pjkursdb.trainings";

        if (startDate != null) {
            buildedFunction += " where start_date >= '" + startDate + "'";
        }
        if (endDate != null) {
            if (startDate != null) {
                buildedFunction += " and ";
            } else {
                buildedFunction += " where";
            }
            buildedFunction += " start_date <= '" + endDate + "'";
        }

        try {
            List<Value> list = dbConnector
                    .getMappedArrayList(new Value(), buildedFunction);

            if (list.size() > 0) {
                return list.stream().mapToInt(v -> v.value).boxed().collect(Collectors.toList());
            }
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getTrainingsIds", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Integer> getUsersIdsCreated(String startDate, String endDate) {
        String buildedFunction
                = "select id as value from pjkursdb.appusers";

        if (startDate != null) {
            buildedFunction += " where create_date >= '" + startDate + "'";
        }
        if (endDate != null) {
            if (startDate != null) {
                buildedFunction += " and ";
            } else {
                buildedFunction += " where";
            }
            buildedFunction += " create_date <= '" + endDate + "'";
        }
        try {
            List<Value> list = dbConnector.getMappedArrayList(new Value(), buildedFunction);

            if (list.size() > 0) {

            }
            return list.stream().mapToInt(v -> v.value).boxed().collect(Collectors.toList());
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy mapowaniu usera", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public Integer getIncomeForTraining(Training t) {
        String buildedFunction
                =
                "SELECT coalesce(floor(sum(finalPrice)),0) as value FROM pjkursdb"
                        + ".appusers_trainigs "
                        + "where "
                        + "training_id"
                        + " = "
                        + t.id;
        try {
            List<BigDecimalValue> list = dbConnector
                    .getMappedArrayList(new BigDecimalValue(), buildedFunction);

            if (list.size() > 0) {
                return list.get(0).value!=null ? list.get(0).value.intValue() : 0;
            }
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getTeacherTimeInTraining", exception);
        }
        return 0;
    }

    @Override
    public Integer getCountOfParticipantsForCourse(Training t) {
        String buildedFunction
                =
                "SELECT coalesce(floor(count(appuser_id)),0) as value FROM pjkursdb"
                        + ".appusers_trainigs "
                        + "where "
                        + "training_id"
                        + " = "
                        + t.id;
        try {
            List<BigDecimalValue> list = dbConnector
                    .getMappedArrayList(new BigDecimalValue(), buildedFunction);

            if (list.size() > 0) {
                return list.get(0).value!=null ? list.get(0).value.intValue() : 0;
            }
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getTeacherTimeInTraining", exception);
        }
        return 0;
    }

    @Override
    public Integer getCountOfGraduates(Training t) {
        String buildedFunction
                =
                "SELECT coalesce(floor(count(appuser_id)),0) as value FROM pjkursdb"
                        + ".courses_graduates "
                        + "where "
                        + "training_id"
                        + " = "
                        + t.id;
        try {
            List<BigDecimalValue> list = dbConnector
                    .getMappedArrayList(new BigDecimalValue(), buildedFunction);

            if (list.size() > 0) {
                return list.get(0).value!=null ? list.get(0).value.intValue() : 0;
            }
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getTeacherTimeInTraining", exception);
        }
        return 0;
    }

    @Override
    public void deleteUser(Appusers p) {
        String[] function = {
                "delete from pjkursdb.appusers where id=" + p.id,
                "delete from pjkursdb.appusers_courses where user_id=" + p.id,
                "delete from pjkursdb.appusers_trainigs where appuser_id=" + p.id,
                "delete from pjkursdb.courses_graduates where appuser_id=" + p.id,
                "delete from pjkursdb.discounts where appusers_id=" + p.id,
                "delete from pjkursdb.teachers where appUserId=" + p.id
        };
        try {
            for (String s:function) {
                dbConnector.executeUpdate(s);
            }
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getTeacherTimeInTraining", exception);
        }
    }

    @Override
    public List<Training> getEndedTrainingsForTeacher(Teachers teacher) {
        String buildedFunction
                ="select tr.* from pjkursdb.trainings tr right join pjkursdb.teachers_trainings tt"
                + " on tt.training_id=tr.id "
                + "where tt.teacher_id = " + teacher.getTeacher_id()
                +   " and tr.status_id=2";
        try {
            List<Training> list = dbConnector
                    .getMappedArrayList(new Training(), buildedFunction);

            list.forEach(l -> {
                        l.setTeachersList(getTeachersForTraining(l));
                        if (l.getTrainingStatusId() != null) {
                            l.setTrainingStatus(getTrainingStatusFor(l.getTrainingStatusId()));
                        }
                    }
            );
            return list;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getCourseStatuses", exception);
        }
        return new ArrayList<>();
    }
}
