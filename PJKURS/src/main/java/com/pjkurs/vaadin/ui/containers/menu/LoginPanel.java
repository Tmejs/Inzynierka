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
package com.pjkurs.vaadin.ui.containers.menu;

import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.pjkurs.vaadin.views.system.MyModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.annotations.Theme;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Tmejs
 */
@Theme("pjtheme")
public class LoginPanel<T extends MyModel> extends MyContainer<T> {

    public LoginPanel(T model) {
        super(model);
    }

    @Override
    public Component buildView() {
        VerticalLayout mainLayout = new VerticalLayout();
        this.addStyleName("login-component");

        mainLayout.setSizeUndefined();
        //Budowa okna w zalezności od strony na której jest wświetlane
        if (getModel() instanceof MainViewModel) {
            MainViewModel tempModel = (MainViewModel) getModel();

            mainLayout.addComponent(new Label(Words.TXT_LOGIN_TO_SERWIS));
            /*
            Email : []
            Hasało: []
            [Zarejestruj]       [Zaloguj]
            Zapomniałeś hasła(url do storny z przypomniajka)
            
             */

            // email
            TextField emailTextField = new TextField(Words.TXT_EMAIL);
            emailTextField.setSizeUndefined();

            mainLayout.addComponent(emailTextField);

            //hasło
            TextField passwordTextField = new PasswordField(Words.TXT_PASSWORD);

            mainLayout.addComponent(passwordTextField);

            tempModel.bindLoginData(emailTextField, passwordTextField);
            //Guzik logowania
            Button loginButton = new Button(Words.TXT_LOGIN_BUTTON, ((event) -> {
                tempModel.loginButtonClick(event);
            }));

            mainLayout.addComponent(loginButton);

            //Link do przypominajki
//            Link linkToForgotPassword = new Link(Words.TXT_FORGOT_PASSWORD,
//                    new ExternalResource("#!"));
//
//            mainLayout.addComponent(linkToForgotPassword);

            //Link do rejestracji
            Link linkToRegister = new Link(Words.TXT_REGISTER,
                    new ExternalResource("#!" + NavigatorUI.View.REGISTER_VIEW.getName()));

            mainLayout.addComponent(linkToRegister);

        }
        return mainLayout;
    }

}
