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

import com.pjkurs.domain.ArchiveCourse;
import com.pjkurs.domain.Client;
import com.pjkurs.domain.Course;
import com.pjkurs.domain.MyCourse;
import com.pjkurs.InterfacePjkursDataProvider;
import com.pjkurs.domain.Appusers;
import com.pjkurs.domain.Category;
import com.pjkurs.usables.Words;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tmejs
 */
public class DbDataProvider implements InterfacePjkursDataProvider {

    private final DBConnector dbConnector;

    @Override
    public List<Course> getAllCourses() {
        String buildedFunction
                = "SELECT * FROM pjkursdb.kursy_v";
        try {
            return dbConnector.getMappedArrayList(new Course(), buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName()).log(Level.ALL, "Blad przy mapowaniu usera", exception);
        }
        return new ArrayList<>();
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
            Logger.getLogger(this.getClass().getCanonicalName()).log(Level.ALL, "Blad przy mapowaniu usera", exception);
        }
        return new ArrayList<>();

    }

    @Override
    public Boolean registerNewClient(Client client) {
        String buildedFunction
                = "pjkursdb.zarejestruj_usera('" + client.email + "', '" + client.password + "')";
        dbConnector.commit();
        return dbConnector.getBooleanFunctionValue(buildedFunction);
    }

    @Override
    public List<Appusers> getUsers() {
        String buildedFunction
                = "select * from pjkursdb.appusers";
        try {
            return dbConnector.getMappedArrayList(new Appusers(), buildedFunction);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName()).log(Level.ALL, "Blad przy mapowaniu usera", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public Appusers getUser(String email) {
        String buildedFunction
                = "select * from pjkursdb.appusers where email='" + email + "'";
        try {
            return (Appusers) dbConnector.getMappedArrayList(new Appusers(), buildedFunction).get(0);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName()).log(Level.ALL, "Blad przy mapowaniu usera", exception);
        }
        return null;
    }

    @Override
    public Course getCourse(Integer courseId) {
        String buildedFunction
                = "SELECT * FROM pjkursdb.dostepne_kursy_v where id=" + courseId;
        try {
            return (Course) dbConnector.getMappedArrayList(new Course(), buildedFunction).get(0);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName()).log(Level.ALL, "Blad przy mapowaniu usera", exception);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            return dbConnector.getMappedArrayList(new Course(), sqlQuery);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName()).log(Level.SEVERE, "Blad przy mapowaniu Kursu", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public List<MyCourse> getMyCourses(String userEmail) {
        String sqlQuery = Words.SQL_SELECT_MY_COURSES_QUERY + " where username='" + userEmail + "'";
        Logger.getGlobal().log(Level.SEVERE, sqlQuery);
        try {
            return dbConnector.getMappedArrayList(new Course(), sqlQuery);
        } catch (Exception exception) {
            Logger.getLogger(this.getClass().getCanonicalName()).log(Level.SEVERE, "Blad przy mapowaniu Kursu", exception);
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
            Logger.getLogger(this.getClass().getCanonicalName()).log(Level.SEVERE, "Blad przy mapowaniu Kursu", exception);
        }
        return new ArrayList<>();
    }

    @Override
    public Boolean addnewCourse(Course newCourse) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean updateCourse(Course course) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean checkDoEmailOcuppied(String login) {
        String buildedFunction
                = "pjkursdb.czy_zarejestrowany_email('" + login + "')";
        return dbConnector.getBooleanFunctionValue(buildedFunction);
    }

}
