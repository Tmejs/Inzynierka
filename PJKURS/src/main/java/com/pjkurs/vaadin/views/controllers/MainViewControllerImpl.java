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

import java.util.logging.Level;
import java.util.logging.Logger;

import com.pjkurs.db.DbDataProvider;
import com.pjkurs.domain.Category;
import com.pjkurs.domain.Client;
import com.pjkurs.domain.Course;
import com.pjkurs.domain.Training;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.ui.containers.admin.DetailedTrainingPanel;
import com.pjkurs.vaadin.ui.containers.admin.TeacherTrainingsPanel;
import com.pjkurs.vaadin.ui.containers.client.GraduatedCoursesPanel;
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.vaadin.ui.Button;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;

/**
 * @author Tmejs
 */
public class MainViewControllerImpl implements InterfaceMainViewController {

    MainViewModel model;

    public MainViewControllerImpl(MainViewModel model) {
        this.model = model;
    }

    private MainViewModel getModel() {
        return model;
    }

    @Override
    public void coursesButtonClicked(Category category, MenuBar.MenuItem event) {
        getModel().getView().setCoursesAsMainPanel(category, event);
    }

    @Override
    public void contactDataButtonClicked(MenuBar.MenuItem selectedItem) {
        getModel().getView().setContactPanelAsMainPanel(selectedItem);
    }

    @Override
    public void archiveCoursesButtonClicked(MenuBar.MenuItem selectedItem) {
        getModel().getView().setArchiveCoursesPanelAsMainPanel(selectedItem);
    }

    @Override
    public void detailCourseButtonClickd(Button.ClickEvent event, Integer id) {
        Logger.getGlobal().log(Level.SEVERE, "detailCourseButtonClickd:");
        getModel().getView().setDetailedCourseAsMainPanel(id);
    }

    @Override
    public void registerButtonClicked(Button.ClickEvent event) {
        registerNewUser();
    }

    @Override
    public void myCoursesButtonClicked(MenuBar.MenuItem item) {
        getModel().getView().setMyCoursesAsMainPanel(item);
    }

    @Override
    public void personalDataButtonClicked(Button.ClickEvent event) {
        throw new UnsupportedOperationException(
                "Not supported yet."); //To change body of generated methods, choose Tools |
        // Templates.
    }

    @Override
    public void registerToCourseButtonClicked(Button.ClickEvent event) {
        throw new UnsupportedOperationException(
                "Not supported yet."); //To change body of generated methods, choose Tools |
        // Templates.
    }

    @Override
    public void logoutButtonClick(Button.ClickEvent event) {
        getModel().getUi().getSession().setAttribute(Words.SESSION_LOGIN_NAME, null);
        Notification.show(Words.TXT_CORRECTLY_LOGGED_OUT);
        getModel().getView().refreshView();
    }

    @Override
    public void loginButtonClick(Button.ClickEvent event) {

    }

    @Override
    public void niezlogowanyButtonClicked(Button.ClickEvent event) {
        throw new UnsupportedOperationException(
                "Not supported yet."); //To change body of generated methods, choose Tools |
        // Templates.
    }

    @Override
    public void myDataButtonClicked() {
        getModel().getView().setMyDataAsPanel();
    }

    @Override
    public void addToCourseButtonClicked(Button.ClickEvent event, Course course) {
        throw new UnsupportedOperationException(
                "Not supported yet."); //To change body of generated methods, choose Tools |
        // Templates.
    }

    private void registerNewUser() {
        DbDataProvider dataProvider = new DbDataProvider();
        if (dataProvider.registerNewUser(new Client())) {
            Notification.show("Poprawnie zalogowano");
        } else {
            Notification.show("Zle zalogowano");
        }
    }

    @Override
    public void detailedTrainingPanelClicked(Training training, Boolean inEditMode,
            Boolean openedByTeachers) {
        getModel().getView().setMainPanel(new DetailedTrainingPanel(model,
                training, inEditMode, openedByTeachers));
    }

    @Override
    public void myTrainingsButtonClicked(MenuBar.MenuItem selectedItem) {
        getModel().getView().setMainPanel(new TeacherTrainingsPanel(model));
    }

    @Override
    public void graduatedCourses() {
        getModel().getView().setMainPanel(new GraduatedCoursesPanel(model));
    }
}
