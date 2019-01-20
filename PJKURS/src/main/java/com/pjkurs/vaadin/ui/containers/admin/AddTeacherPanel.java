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
package com.pjkurs.vaadin.ui.containers.admin;

import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.annotations.Theme;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Tmejs
 */
@Theme("pjtheme")
public class AddTeacherPanel<T extends AdminViewModel> extends MyContainer<T>  {

    public AddTeacherPanel(T model) {
        super(model);
    }

    @Override
    public Component buildView() {
      VerticalLayout mainLayout = new VerticalLayout();
        this.addStyleName("login-component");

        mainLayout.setSizeUndefined();
        //Budowa okna w zalezności od strony na której jest wświetlane
        AdminViewModel tempModel = getModel();

        mainLayout.addComponent(new Label(Words.TXT_INSERT_NEW_TEACHER_DATA));
        /*
            Email : []
            Hasało: []
            [Zarejestruj]       [Zaloguj]
            Zapomniałeś hasła(url do storny z przypomniajka)
            
         */

        // imie
        TextField nameTextField = new TextField(Words.TXT_NAME);
        nameTextField.setSizeUndefined();

        mainLayout.addComponent(nameTextField);

        //Opis
        TextField descriptionTextField = new TextField(Words.TXT_SURRNAME);

        mainLayout.addComponent(descriptionTextField);

        TextField email = new TextField(Words.TXT_EMAIL);

        mainLayout.addComponent(email);

//            tempModel.bindLoginData(emailTextField, passwordTextField);
        //Guzik logowania
//        Button loginButton = new Button(Words.TXT_ADD_NEW_TEACHER, ((event) -> {
////                tempModel.loginButtonClick(event);
//        }));

//        mainLayout.addComponent(loginButton);

        return mainLayout;
    }
    
    
}
