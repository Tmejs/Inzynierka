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

import com.pjkurs.domain.ArchiveCourse;
import com.pjkurs.domain.Course;
import com.pjkurs.domain.Client;
import com.pjkurs.domain.MyCourse;
import java.util.List;

/**
 *
 * @author Tmejs
 */
public interface InterfacePjkursDataProvider {

    /*
    Operacje z klientem
     */
    public Boolean registerNewClient(Client client);

    public Boolean updateClient(Client client);

    public Boolean loginClient(String login, String password);
    
    public Boolean checkDoEmailOcuppied(String login);

    /*
    Operacje związane z przypisaniem klienta do kursu
     */
    public Boolean addClientToCourse(Client client, Course course);

    /*
    Operacje zwiazane z kursami
     */
    public List<Course> getAvalibleCourses();
    public List<MyCourse> getMyCourses();
    
    public List<ArchiveCourse> getArchiveCourses();

    public Boolean addnewCourse(Course newCourse);

    public Boolean updateCourse(Course course);

}
