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
import com.pjkurs.domain.LoginData;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.system.MyModel;
import com.pjkurs.vaadin.views.MainView;
import com.vaadin.data.Binder;
import com.vaadin.data.BinderValidationStatusHandler;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.Setter;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import java.io.File;
import java.rmi.ServerError;

/**
 *
 * @author Tmejs
 */
public class MainViewModel extends MyModel<MainView> {

    public final static String PARAM_BINDED_LOGIN_DATA = "PARAM_BINDED_LOGIN_DATA";

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

        Notification.show("Wprowadzony login:" + ((LoginData) getParam(PARAM_BINDED_LOGIN_DATA)).email + "\n"
                + "Wprowadzone hasło:" + ((LoginData) getParam(PARAM_BINDED_LOGIN_DATA)).password);
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

    public void bindLoginEmailTextField(TextField emailTextField) {
        Binder<LoginData> binder = new Binder<>(LoginData.class);
        if (getParam(PARAM_BINDED_LOGIN_DATA) == null) {
            LoginData loginData = new LoginData();
            setParam(PARAM_BINDED_LOGIN_DATA, loginData);
            binder.setBean(loginData);
        } else {
            binder.setBean((LoginData) getParam(PARAM_BINDED_LOGIN_DATA));
        }

        binder.forField(emailTextField)
                .withValidator(
                        t -> t.contains("@"),
                        "Email musi zawierać @"
                ).
                bind(LoginData::getEmail, LoginData::setEmail);

    }

    public void bindLoginPaswordTextField(TextField passwordTextField) {
        Binder<LoginData> binder = new Binder<>(LoginData.class);
        if (getParam(PARAM_BINDED_LOGIN_DATA) == null) {
            LoginData loginData = new LoginData();
            setParam(PARAM_BINDED_LOGIN_DATA, loginData);
            binder.setBean(loginData);
        } else {
            binder.setBean((LoginData) getParam(PARAM_BINDED_LOGIN_DATA));
        }

        binder.forField(passwordTextField).withValidationStatusHandler((statusChange) -> {
            if (statusChange.getStatus() != BindingValidationStatus.Status.OK) {
                ((LoginData) getParam(PARAM_BINDED_LOGIN_DATA)).password = "";
            }
        }).bind(LoginData::getPassword, LoginData::setPassword);

    }
}
