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

import com.pjkurs.InterfacePjkursDataProvider;
import com.pjkurs.domain.Client;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.system.MyModel;
import com.pjkurs.vaadin.views.RegisterView;
import com.vaadin.data.Binder;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import java.io.Serializable;

/**
 *
 * @author Tmejs
 */
public class RegisterViewModel extends MyModel<RegisterView> {

    public final static String PARAM_BINDER_REGISTER = "PARAM_BINDER_REGISTER";
    public final static String PARAM_BINDER_REGISTER_DATA = "PARAM_BINDER_REGISTER_DATA";

    public RegisterViewModel(UI ui) {
        this.currentUI = ui;
    }

    public void registerButtonClicked(Button.ClickEvent event) {
        Binder<RegisterData> binder = ((Binder) getParam(PARAM_BINDER_REGISTER));
        RegisterData data = (RegisterData) getParam(PARAM_BINDER_REGISTER_DATA);

        if (!binder.writeBeanIfValid(data)) {
            return;
        }

        //Sprawdzenie czy takie same hasła
        if (!data.password.equals(data.passwordConf)) {
            Notification.show(Words.TXT_PASSWORDS_MUST_MATCH);
            data.password = data.passwordConf = "";
            binder.readBean(data);
        } else {
            Client client = new Client();
            client.email = data.getLogin();
            client.password = data.getPassword();
            if (NavigatorUI.getDBProvider().registerNewClient(client)) {
                //Poprawnie zarejestrowano
                getUi().getSession().setAttribute(Words.SESSION_LOGIN_NAME, client);
                getUi().getNavigator().navigateTo(NavigatorUI.View.MAINVIEW.getName());
                Notification.show(Words.TXT_CORRECTLY_REGISTERED);
            } else {
                //TODO błąd rejestracji
            };
        }
    }

    class RegisterData implements Serializable {

        String login;
        String password;
        String passwordConf;

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPasswordConf() {
            return passwordConf;
        }

        public void setPasswordConf(String passwordConf) {
            this.passwordConf = passwordConf;
        }

    }

    public void backButtonClicked(Button.ClickEvent event) {
        getUi().getNavigator().navigateTo(NavigatorUI.View.MAINVIEW.getName());
    }

    public void bindLoginData(TextField emailTextField, TextField passwordTextField, TextField confirmPasswordTextField) {
        Binder<RegisterData> registerBinder = new Binder<>(RegisterData.class);
        RegisterData registerData = new RegisterData();
        setParam(PARAM_BINDER_REGISTER_DATA, registerData);
        setParam(PARAM_BINDER_REGISTER, registerBinder);

        //walidacja maila
        registerBinder.forField(emailTextField)
                .withValidator(
                        t -> t.contains("@"),
                        "Email musi zawierać @"
                )
                .withValidator((t) -> {
                    return !NavigatorUI.getDBProvider().checkDoEmailOcuppied(t);
                },
                        Words.TXT_USED_EMAIL
                ).
                bind(RegisterData::getLogin, RegisterData::setLogin);

        registerBinder.forField(passwordTextField)
                .withValidator((t) -> t.length() > 4, "Min 5 znaków")
                .bind(RegisterData::getPassword, RegisterData::setPassword);

        registerBinder.forField(confirmPasswordTextField)
                .bind(RegisterData::getPasswordConf, RegisterData::setPasswordConf);
    }

}
