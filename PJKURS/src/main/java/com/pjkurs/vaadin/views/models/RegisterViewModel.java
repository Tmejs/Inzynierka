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

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import com.pjkurs.domain.Appusers;
import com.pjkurs.usables.EmailsGenerator;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.RegisterView;
import com.pjkurs.vaadin.views.system.MyModel;
import com.vaadin.data.Binder;
import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

/**
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
        RegisterData data = getParam(PARAM_BINDER_REGISTER_DATA);

        if (!binder.writeBeanIfValid(data)) {
            return;
        }

        //Sprawdzenie czy takie same hasła
        if (!data.password.equals(data.passwordConf)) {
            Notification.show(Words.TXT_PASSWORDS_MUST_MATCH);
            data.password = data.passwordConf = "";
            binder.readBean(data);
        } else {
            Appusers appuser = new Appusers();
            appuser.email = data.login;
            appuser.password = data.password;
            appuser.name = data.name;
            appuser.surname = data.surname;
            appuser.contact_number = data.phoneNumber;
            if (NavigatorUI.getDBProvider().registerNewUser(appuser)) {
                Appusers registeredUser = NavigatorUI.getDBProvider().getUser(appuser.email);
                NavigatorUI.setLoggeddUser(registeredUser);
                getUi().getNavigator().navigateTo(NavigatorUI.View.MAINVIEW.getName());
                Notification.show(Words.TXT_CORRECTLY_REGISTERED);
                try {
                    NavigatorUI.getMailSender()
                            .sendAsHtml(EmailsGenerator.getRegistrationMessage(registeredUser));
                } catch (MessagingException e) {
                    Logger.getLogger(getClass().toString()).log(Level.WARNING, e.getMessage());
                }
            } else {
            }
        }
    }

    class RegisterData implements Serializable {

        String login;
        String password;
        String passwordConf;
        String name;

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        String surname;
        String phoneNumber;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

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

    public void bindLoginData(TextField emailTextField, TextField passwordTextField,
            TextField confirmPasswordTextField, TextField nameTextField,
            TextField phoneNumberTextField, TextField surnameTextField) {
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

        registerBinder.forField(phoneNumberTextField)
                .withValidator(RegisterViewModel::isValidNumber, "Numer telefonu może "
                        + "zawierać tylko \"+\" oraz cyfry")
                .withConverter(new Converter<String, String>() {
                    @Override
                    public Result<String> convertToModel(String value, ValueContext context) {
                        String string = value;
                        string = string.replace(" ", "");
                        return Result.ok(string);
                    }

                    @Override
                    public String convertToPresentation(String value, ValueContext context) {
                        return value;
                    }
                })
                .bind(RegisterData::getPhoneNumber, RegisterData::setPhoneNumber);

        registerBinder.forField(nameTextField)
                .withValidator(RegisterViewModel::isValidName, "Imie może zawierać tylko litery")
                .bind(RegisterData::getName, RegisterData::setName);

        registerBinder.forField(surnameTextField)
                .withValidator(RegisterViewModel::isValidName, "Nazwisko może zawierać tylko "
                        + "litery")
                .bind(RegisterData::getSurname, RegisterData::setSurname);
    }

    private static Boolean isValidNumber(String string) {
        string = string.replace(" ", "");
        string = string.replace("+", "");
        for (char c : string.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private static Boolean isValidName(String name) {
        return name.matches("[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]*");
    }
}
