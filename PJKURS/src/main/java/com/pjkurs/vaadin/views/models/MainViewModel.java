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

import com.pjkurs.domain.Appusers;
import com.pjkurs.domain.Category;
import com.pjkurs.domain.Course;
import com.pjkurs.domain.Training;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.system.MyModel;
import com.pjkurs.vaadin.views.MainView;
import com.pjkurs.vaadin.views.controllers.InterfaceMainViewController;
import com.pjkurs.vaadin.views.controllers.MainViewControllerImpl;
import com.vaadin.data.Binder;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import org.omg.CORBA.NamedValue;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tmejs
 */
public class MainViewModel extends MyModel<MainView> implements InterfaceMainViewController {

    public final static String PARAM_BINDED_LOGIN_DATA = "PARAM_BINDED_LOGIN_DATA";
    public final static String PARAM_BINDER_LOGIN = "PARAM_BINDER_LOGIN";
    private InterfaceMainViewController controller;

    public class LoginData implements java.io.Serializable {

        public String email;
        public String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    }

    private InterfaceMainViewController getController() {
        return controller;
    }

    public MainViewModel(UI ui) {
        this.currentUI = ui;
        this.controller = new MainViewControllerImpl(this);
    }

    @Override
    public void contactDataButtonClicked(MenuBar.MenuItem selectedItem) {
        getController().contactDataButtonClicked(selectedItem);
    }

    @Override
    public void coursesButtonClicked(Category category, MenuBar.MenuItem event) {
        getController().coursesButtonClicked(category, event);
    }

    @Override
    public void graduatedCourses() {
        getController().graduatedCourses();
    }

    @Override
    public void archiveCoursesButtonClicked(MenuBar.MenuItem selectedItem) {
        getController().archiveCoursesButtonClicked(selectedItem);
    }

    @Override
    public void detailCourseButtonClickd(Button.ClickEvent event, Integer id) {
        Logger.getGlobal().log(Level.SEVERE, "detailCourseButtonClickd:");
        getController().detailCourseButtonClickd(event, id);
    }

    @Override
    public void myCoursesButtonClicked(MenuBar.MenuItem item) {
        getController().myCoursesButtonClicked(item);
    }

    @Override
    public void personalDataButtonClicked(Button.ClickEvent event) {
        getController().personalDataButtonClicked(event);
    }

    @Override
    public void myTrainingsButtonClicked(MenuBar.MenuItem selectedItem) {
        getController().myTrainingsButtonClicked(selectedItem);
    }

    @Override
    public void registerToCourseButtonClicked(Button.ClickEvent event) {
        getController().registerToCourseButtonClicked(event);
    }

    @Override
    public void registerButtonClicked(Button.ClickEvent event) {
        getController().registerButtonClicked(event);

    }

    @Override
    public void logoutButtonClick(Button.ClickEvent event) {
        getController().logoutButtonClick(event);
    }

    @Override
    public void loginButtonClick(Button.ClickEvent event) {
        if (((Binder) getParam(PARAM_BINDER_LOGIN))
                .writeBeanIfValid(getParam(PARAM_BINDED_LOGIN_DATA))) {

            //funkcja sprawdzająca zalogowanie
            boolean loginStatus = NavigatorUI.getDBProvider()
                    .loginClient(((LoginData) getParam(PARAM_BINDED_LOGIN_DATA)).email,
                            ((LoginData) getParam(PARAM_BINDED_LOGIN_DATA)).password);

            Appusers user =
                    NavigatorUI.getDBProvider()
                            .getUser(((LoginData) getParam(PARAM_BINDED_LOGIN_DATA)).email);

            if (loginStatus && (user.isActive!=null && user.isActive) ) {
                Notification.show(Words.TXT_CORRECTLY_LOGGED);

                //Ustawienie w sesji zalogowanego usera
                getUi().getSession().setAttribute(Words.SESSION_LOGIN_NAME, user);

                getView().refreshView();
            } else if (loginStatus &&(user.isActive==null || !user.isActive)) {
                Notification.show(Words.TXT_NOT_ACTIVATED);
            } else {
                Notification.show(Words.TXT_WRONG_LOGIN_DATA);
                //Reset hasła
                ((LoginData) getParam(PARAM_BINDED_LOGIN_DATA)).password = "";
                //Czyszczenie inputu w widoku
                ((Binder) getParam(PARAM_BINDER_LOGIN))
                        .readBean(((LoginData) getParam(PARAM_BINDED_LOGIN_DATA)));
            }
        } else {
            Notification.show("Wprowadz poprawne dane");
        }
    }

    @Override
    public void niezlogowanyButtonClicked(Button.ClickEvent event) {
        getUi().getNavigator().navigateTo(NavigatorUI.View.REGISTER_VIEW.getName());
    }

    @Override
    public void myDataButtonClicked() {
        getController().myDataButtonClicked();
    }

    public Resource getLogoResource() {
        // Find the application directory
        String basepath = VaadinService.getCurrent()
                .getBaseDirectory().getAbsolutePath();

        // Image as a file resource
        FileResource resource = new FileResource(new File(basepath
                + Words.IMAGE_FOLDER_PATH + "/" + Words.PJURS_LOGO_IMAGE_NAME));

        return resource;
    }

    public void bindLoginData(TextField emailTextField, TextField passwordTextField) {
        Binder<LoginData> loginBinder = new Binder<>(LoginData.class);
        LoginData loginData = new LoginData();
        setParam(PARAM_BINDED_LOGIN_DATA, loginData);
        setParam(PARAM_BINDER_LOGIN, loginBinder);
        //((InterfacePjkursDataProvider) getUi().getSession().getAttribute(NavigatorUI
        // .PARAM_DATA_PROVIDER)).checkDoEmailOcuppied(((LoginData) getParam
        // (PARAM_BINDED_LOGIN_DATA)).email).toString()
        //TODO poprawna walidacja
        loginBinder.forField(emailTextField)
                .withValidator(
                        t -> t.contains("@"),
                        "Email musi zawierać @"
                ).bind(LoginData::getEmail, LoginData::setEmail);
        //TODO validator do rejestracji
        //                .withValidator(
        //                        t -> ((InterfacePjkursDataProvider) getUi().getSession()
        // .getAttribute(NavigatorUI.PARAM_DATA_PROVIDER)).checkDoEmailOcuppied(t),
        //                         "Email musi zawierać @"
        //                ).

        loginBinder.forField(passwordTextField)
                .bind(LoginData::getPassword, LoginData::setPassword);
    }

    @Override
    public void addToCourseButtonClicked(Button.ClickEvent event, Course course) {
        Notification.show("Próba zarejestrowania do kursu:" + course.name);
    }

    @Override
    public void detailedTrainingPanelClicked(Training training, Boolean inEditMode,
            Boolean isOpenedByTeacher) {
        controller.detailedTrainingPanelClicked(training, inEditMode, isOpenedByTeacher);
    }
}
