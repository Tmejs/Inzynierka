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
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.ui.containers.*;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.vaadin.ui.Label;

/**
 *
 * @author Tmejs
 */
public class AdminViewControllerImpl implements InterfaceAdminViewController {

    AdminViewModel model;

    public AdminViewControllerImpl(AdminViewModel model) {
        this.model = model;
    }

    private AdminViewModel getModel() {
        return model;
    }

    @Override
    public void menuCoursesOverviewClicked() {
        getModel().getView().setMainPanel(new AdminCoursesOverviewPanel(model));
    }

    @Override
    public void menuCoursesAddNewClicked() {
        getModel().getView().setMainPanel(new AddCoursePanel(model));
    }

    @Override
    public void menuTeachersOvervievClicked() {
//        getModel().getView().setMainPanel(model.getTeachersOverviewPanel());
    }

    @Override
    public void menuTeachersAddNewClicked() {
//        getModel().getView().setMainPanel(model.getTeachersAddPanel());
    }

    @Override
    public void menuUsersClicked() {
        getModel().getView().setMainPanel(new EditableUsersListPanel(model));
    }

    @Override
    public void editCourseDataButtonClicked(Course item) {
        getModel().getView().setMainPanel(new AdminEditCoursePanel(item, model));
    }

    @Override
    public void menuCategoriesClicked() {
        getModel().getView().setMainPanel(new EditCategoriesPanel(model));
    }

    @Override
    public void addNewCouurse(Course newCourse) {
        NavigatorUI.getDBProvider().addnewCourse(newCourse);
    }

    @Override
    public void menuSubcategoriesClicked() {
        getModel().getView().setMainPanel(new EditSubcategoriesPanel(model));
    }
}
