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
package com.pjkurs.vaadin.views.models;

import com.pjkurs.domain.Course;
import com.pjkurs.domain.Training;
import com.pjkurs.vaadin.views.AdminView;
import com.pjkurs.vaadin.views.controllers.AdminViewControllerImpl;
import com.pjkurs.vaadin.views.controllers.InterfaceAdminViewController;
import com.pjkurs.vaadin.views.system.MyModel;
import com.vaadin.ui.UI;

/**
 *
 * @author Tmejs
 */
public class AdminViewModel extends MyModel<AdminView> implements InterfaceAdminViewController {

    private InterfaceAdminViewController controller;

    private InterfaceAdminViewController getController() {
        return controller;
    }

    public AdminViewModel(UI ui) {
        this.currentUI = ui;
        this.controller = new AdminViewControllerImpl(this);
    }

    @Override
    public void menuCoursesOverviewClicked() {
        controller.menuCoursesOverviewClicked();
    }

    @Override
    public void menuCoursesAddNewClicked() {
        controller.menuCoursesAddNewClicked();
    }

    @Override
    public void menuTeachersOvervievClicked() {
        controller.menuTeachersOvervievClicked();
    }

    @Override
    public void menuTeachersAddNewClicked() {
        controller.menuTeachersAddNewClicked();
    }

    @Override
    public void menuUsersClicked() {
        controller.menuUsersClicked();
    }

    @Override
    public void editCourseDataButtonClicked(Course item) {
        controller.editCourseDataButtonClicked(item);
    }

    @Override
    public void menuCategoriesClicked() {
        controller.menuCategoriesClicked();
    }

    @Override
    public void addNewCouurse(Course newCourse) {
        controller.addNewCouurse(newCourse);
    }

    @Override
    public void menuAwaitingDicountsClicked() {
        controller.menuAwaitingDicountsClicked();
    }

    @Override
    public void detailedTrainingPanelClicked(Training training) {
        controller.detailedTrainingPanelClicked(training);
    }

    @Override
    public void correctlyLoged() {
        getView().refreshView();
    }

    @Override
    public void deaneryUsersClicked() {
        controller.deaneryUsersClicked();
    }

    @Override
    public void statistickMenuClicked() {
        controller.statistickMenuClicked();
    }

    @Override
    public void menuTrainigsClicked() {
        controller.menuTrainigsClicked();
    }
}
