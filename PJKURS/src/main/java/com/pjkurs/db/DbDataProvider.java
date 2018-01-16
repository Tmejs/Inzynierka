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
import java.util.List;

/**
 *
 * @author Tmejs
 */
public class DbDataProvider implements InterfacePjkursDataProvider {

    private final DBConnector dbConnector;

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
    public Boolean registerNewClient(Client client) {
        String buildedFunction
                = "pjkursdb.zarejestruj_usera('" + client.email + "', '" + client.password + "')";
        dbConnector.commit();
        return dbConnector.getBooleanFunctionValue(buildedFunction);
    }

    @Override
    public Boolean updateClient(Client client) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Boolean loginClient(String login, String password) {
        String buildedFunction
                = "pjkursdb.zaloguj_usera('" + login + "', '" + password + "')";
        return dbConnector.getBooleanFunctionValue(buildedFunction);
    }

    @Override
    public Boolean addClientToCourse(Client client, Course course) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Course> getAvalibleCourses() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MyCourse> getMyCourses() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ArchiveCourse> getArchiveCourses() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
