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

import com.pjkurs.db.DbDataProvider;
import com.pjkurs.domain.Client;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.system.MyModel;
import com.pjkurs.vaadin.views.MainView;
import com.vaadin.data.Binder;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import java.io.File;

/**
 *
 * @author Tmejs
 */
public class MainViewModel extends MyModel<MainView> {

    public final static String PARAM_BINDED_LOGIN_DATA = "PARAM_BINDED_LOGIN_DATA";
    public final static String PARAM_BINDER_LOGIN = "PARAM_BINDER_LOGIN";

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

    public MainViewModel(UI ui) {
        this.currentUI = ui;
    }

    public void coursesButtonClicked(Button.ClickEvent event) {

    }

    public void archiveButtonClicked(Button.ClickEvent event) {

    }

    public void detailCourseButtonClickd(Button.ClickEvent event) {

    }

    public void registerButtonClicked(Button.ClickEvent event) {
        registerNewUser();
    }

    public void myCoursesButtonClicked(Button.ClickEvent event) {

    }

    public void personalDataButtonClicked(Button.ClickEvent event) {

    }

    public void registerToCourseButtonClicked(Button.ClickEvent event) {

    }

    public void logoutButtonClick(Button.ClickEvent event) {

    }

    public void loginButtonClick(Button.ClickEvent event) {
        if (((Binder) getParam(PARAM_BINDER_LOGIN)).writeBeanIfValid(getParam(PARAM_BINDED_LOGIN_DATA))) {
            //TODO próba zalogowania
            Notification.show("Wprowadzony login:" + ((LoginData) getParam(PARAM_BINDED_LOGIN_DATA)).email + "\n"
                    + "Wprowadzone hasło:" + ((LoginData) getParam(PARAM_BINDED_LOGIN_DATA)).password);
        } else {
            Notification.show("Wprowadzony poprawne dane");
        }

    }

    public void notifButtonClicked(Button.ClickEvent clickEvent) {
        getView().setLoginButtonClicked();
    }

    public void niezlogowanyButtonClicked(Button.ClickEvent event) {
        getUi().getNavigator().navigateTo(NavigatorUI.View.REGISTER_VIEW.getName());
    }

    private void registerNewUser() {
        DbDataProvider dataProvider = new DbDataProvider();
        if (dataProvider.registerNewClient(new Client())) {
            Notification.show("Poprawnie zalogowano");
        } else {
            Notification.show("Zle zalogowano");
        }
    }

    public void myDataButtonClicked(Button.ClickEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

        //TODO poprawna walidacja
        loginBinder.forField(emailTextField)
                .withValidator(
                        t -> t.contains("@"),
                        "Email musi zawierać @"
                ).
                bind(LoginData::getEmail, LoginData::setEmail);

        loginBinder.forField(passwordTextField).bind(LoginData::getPassword, LoginData::setPassword);
    }
}
