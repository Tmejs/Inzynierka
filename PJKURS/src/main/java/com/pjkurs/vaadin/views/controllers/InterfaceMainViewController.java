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

import com.pjkurs.domain.Category;
import com.pjkurs.domain.Course;
import com.vaadin.ui.Button;
import com.vaadin.ui.MenuBar;

/**
 *
 * @author Tmejs
 */
public interface InterfaceMainViewController {

    public void coursesButtonClicked(Category category, MenuBar.MenuItem Item);


    public void detailCourseButtonClickd(Button.ClickEvent event, Integer courseId);

    public void registerButtonClicked(Button.ClickEvent event);

    public void myCoursesButtonClicked(MenuBar.MenuItem item);

    public void personalDataButtonClicked(Button.ClickEvent event);

    public void registerToCourseButtonClicked(Button.ClickEvent event);

    public void logoutButtonClick(Button.ClickEvent event);

    public void loginButtonClick(Button.ClickEvent event);

    public void niezlogowanyButtonClicked(Button.ClickEvent event);

    public void myDataButtonClicked();

    public void addToCourseButtonClicked(Button.ClickEvent event, Course course);

    public void contactDataButtonClicked(MenuBar.MenuItem selectedItem);

    public void archiveCoursesButtonClicked(MenuBar.MenuItem selectedItem);
}
