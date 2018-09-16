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
package com.pjkurs.vaadin.views.controllers;

import com.pjkurs.domain.Course;

/**
 *
 * @author Tmejs
 */
public interface InterfaceAdminViewController {

    void menuCoursesOverviewClicked();

    void menuCoursesAddNewClicked();

    void menuTeachersOvervievClicked();

    void menuTeachersAddNewClicked();

    void menuUsersClicked();

    void editCourseDataButtonClicked(Course item);
    
    void menuCategoriesClicked();

}
