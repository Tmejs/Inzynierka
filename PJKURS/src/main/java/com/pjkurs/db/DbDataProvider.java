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

import com.pjkurs.InterfacePjkursDataProvider;
import com.pjkurs.domain.*;
import com.pjkurs.usables.Words;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tmejs
 */
public class DbDataProvider implements InterfacePjkursDataProvider {

    private final DBConnector dbConnector;

    @Override
    public void deleteSubCategoryFromCourse(Integer courseId, Long category_id) {
        String function = "delete from pjkursdb.courses_sub_categories where course_id="
                + courseId
                + " and sub_category_id=" + category_id;
        try {
            dbConnector.executeUpdate(function);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy deleteSubCategoryFromCourse", exception);
        }
    }

    @Override
    public Boolean addSubCategoryToCourse(Integer id, Long id0) {
        String query =
                "insert into pjkursdb.courses_sub_categories(course_id,sub_category_id) values ("
                        + id + "," + id0 + ")";
        try {
            dbConnector.executeUpdate(query);
        } catch (SQLException e) {
            Logger.getGlobal().log(Level.SEVERE, "Błąd przy insert kategori", e);
            return false;
        }
        return true;
    }

    @Override
    public List<Course> getAllCourses() {
        String buildedFunction
                = "SELECT * FROM pjkursdb.kursy_v";
        try {
            List<Course> list = dbConnector.getMappedArrayList(new Course(), buildedFunction);
            list.stream().forEach((t) -> {
                t.setSubcategoryList(getSubCategorysByCourseId(t.id));
                if (t.statusId != null) {
                    t.setCourseStatus(getStatusById(t.statusId));
                }

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
    public Boolean registerNewClient(Client client) {
        String buildedFunction
                =
                "insert into `pjkursdb`.`appusers` (email,password) values  ('" + client.email
                        + "','" + client.password + "')";
        try {
            dbConnector.executeUpdate(buildedFunction);
            return true;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad registerNewClient", exception);
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

            course.setSubcategoryList(getSubCategorysByCourseId(courseId));
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
    public Boolean updateClient(Client client) {
        throw new UnsupportedOperationException(
                "Not supported yet."); //To change body of generated methods, choose Tools |
        // Templates.
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
                course.setSubcategoryList(getSubCategorysByCourseId(course.id));
                if (course.getStatusId() != null) {
                    course.setCourseStatus(getStatusById(course.getStatusId()));
                }
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
                t.setSubcategoryList(getSubCategorysByCourseId(t.id));
                if (t.getStatusId() != null) {
                    t.setCourseStatus(getStatusById(t.getStatusId()));
                }
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
            return dbConnector.getMappedArrayList(new ArchiveCourse(), sqlQuery);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.SEVERE, "Blad przy mapowaniu Kursu", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public Boolean addnewCourse(Course newCourse) {
        String query = "insert into pjkursdb.courses(name,description,minimumParticipants) " +
                "values ('" + newCourse.name + "','" + newCourse.description + "',"
                + newCourse.minimumParticipants + ")";
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
                "minimumParticipants = " + course.minimumParticipants + ", " +
                "price =" + course.getPrice() +
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
    public List<SubCategory> getSubCategories() {
        String buildedFunction
                = "select * from pjkursdb.sub_categorries";
        try {
            List<SubCategory> list = dbConnector
                    .getMappedArrayList(new SubCategory(), buildedFunction);

            list.stream().forEach((t) -> {
                t.setCategories(getCategoryListBySubcategoryId(t.id));
            });
            return list;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy mapowaniu kategorii", exception);
        }
        return new ArrayList<>();
    }

    private List<Category> getCategoryListBySubcategoryId(Long id) {
        String buildedFunction
                = "select ct.* from pjkursdb.categorries ct" +
                " join pjkursdb.subcategories_categories sb where sb.category_id = ct.id and sb"
                + ".subcategory_id="
                + id;
        try {
            List<Category> list = dbConnector.getMappedArrayList(new Category(), buildedFunction);
            return list;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy mapowaniu kategorii", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public List<SubCategory> getSubCategorysByCourseId(Integer courseId) {
        String buildedFunction
                = "select sb.* from pjkursdb.sub_categorries sb join " +
                "pjkursdb.courses_sub_categories sbc on sb.id = sbc.sub_category_id where sbc"
                + ".course_id ="
                + courseId;
        try {
            List<SubCategory> lsit = dbConnector
                    .getMappedArrayList(new SubCategory(), buildedFunction);
            lsit.stream().forEach((t) -> {
                t.setCategories(getCategoryListBySubcategoryId(t.id));
            });
            return lsit;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy mapowaniu kategorii", exception);
        }
        return new ArrayList<>();
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
    public void addNewSubCategory(String string, String string0) {
        String query =
                "insert into pjkursdb.sub_categorries(name,description) values ('" + string + "','"
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
    public void deleteSubCategory(SubCategory selectedSubCategory) {
        String query = "delete from pjkursdb.sub_categorries where id =" + selectedSubCategory.id;
        try {
            dbConnector.executeStatement(query);
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "Błąd przy deleteSubCategory", e);
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
    public SubCategory getSubCategory(Long id) {
        String buildedFunction
                = "select * from pjkursdb.sub_categorries where id=" + id;
        try {
            List<SubCategory> list = dbConnector
                    .getMappedArrayList(new SubCategory(), buildedFunction);
            if (!list.isEmpty()) {
                list.get(0).setCategories(getCategoryListBySubcategoryId(id));
                return list.get(0);
            }
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy mapowaniu usera", exception);
        }
        return null;
    }

    @Override
    public void deleteSubcategoryFromCategory(SubCategory subCategory, Category category) {
        String buildedFunction =
                "delete from pjkursdb.subcategories_categories where subcategory_id=" +
                        subCategory.id + " and category_id=" + category.id;
        try {
            dbConnector.executeStatement(buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy addSubCategoryToCategory", exception);
        }
    }

    @Override
    public void addSubCategoryToCategory(SubCategory subCategory, Category category) {
        String buildedFunction = "insert into pjkursdb.subcategories_categories(subcategory_id," +
                "category_id) values ( " + subCategory.id + "," + category.id + ")";
        try {
            dbConnector.executeStatement(buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy deleteSubcategoryFromCategory", exception);
        }

    }

    @Override
    public void updateAppuser(Appusers editedUser) {
        String buildedFunction =
                "UPDATE pjkursdb.appusers SET  id =" + editedUser.id + ",  email  " +
                        "='" + editedUser.email + "',  password  ='" + editedUser.password
                        + "', name  = '" + editedUser.name + "',  surname  ='" +
                        editedUser.surname + "',  birth_date  = '" + editedUser.birth_date + "',  "
                        +
                        "contact_number  = '" + editedUser.contact_number + "'  WHERE  id  =" +
                        editedUser.id;
        try {
            dbConnector.executeStatement(buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "Blad przy updateAppuser", exception);
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
    public List<Discount> getDicsounts() {
        String buildedFunction
                = "SELECT * FROM pjkursdb.discounts";
        try {
            List<Discount> list = dbConnector
                    .getMappedArrayList(new Discount(), buildedFunction);
            return list;
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "getCourseStatuses", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public void updateDiscount(Discount editedDiscount) {
        String moneyString = editedDiscount.money != null ? editedDiscount.money.toString() :
                "null";
        String percentageString = editedDiscount.discount_precentage != null ?
                editedDiscount.discount_precentage.toString() :
                "null";

        String buildedFunction =
                "update pjkursdb.discounts set name ='" + editedDiscount.name + "', "
                        + "description ='" + editedDiscount.description + "', money =" + moneyString
                        + ", discount_precentage= " + percentageString + " where id="
                        + editedDiscount.id;
        try {
            dbConnector.executeStatement(buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "updateDiscount", exception);
        }
    }

    @Override
    public void addDiscount(Discount discount) {
        String moneyString = discount.money != null ? discount.money.toString() :
                "null";
        String percentageString = discount.discount_precentage != null ?
                discount.discount_precentage.toString() :
                "null";

        String buildedFunction =
                "insert into pjkursdb.discounts(name,description,discount_precentage,money ) "
                        + "values( "
                        + "'" + discount.name + "', "
                        + "'" + discount.description + "'," + percentageString
                        + ",    " + moneyString + ")";
        try {
            dbConnector.executeStatement(buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName())
                    .log(Level.ALL, "updateDiscount", exception);
        }
    }
}
