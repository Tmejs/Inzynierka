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

import com.pjkurs.domain.Appusers;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.pjkurs.vaadin.views.system.MyModel;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Tmejs
 */
public class LoggedInPanel<T extends MyModel> extends MyContainer<T> {

    public LoggedInPanel(T model) {
        super(model);
    }

    @Override
    public Component buildView() {
        this.setSizeFull();
        VerticalLayout mainLayout = new VerticalLayout();
        if (getModel() instanceof MainViewModel) {
            MainViewModel tempModel = (MainViewModel) getModel();
            mainLayout.setWidth("100%");
            /*

        *********************************************
        *  Zalogowany jako:
        *  Email
        ***********************************************
             */
            VerticalLayout logedDataLayout = new VerticalLayout();

            Label logedAs = new Label(Words.TXT_LOGGED_ASS);
            logedDataLayout.addComponent(logedAs);

            Label logedName =
                    new Label(((Appusers) VaadinSession.getCurrent().getAttribute(Words.SESSION_LOGIN_NAME)).email);

            logedDataLayout.addComponent(logedName);

            mainLayout.addComponent(logedDataLayout);
            /*
        ***********************************************
        *
        *  Moje dane.
        *                       (Wyloguj)
             */

            VerticalLayout myDataLayout = new VerticalLayout();

            Button myDataButton = new Button(Words.TXT_MY_DATA);

            //Dodanie eventu przejścia 
            myDataButton.addClickListener((event) -> {
                tempModel.myDataButtonClicked();
            });

            myDataLayout.addComponent(myDataButton);

            Button logoutButton = new Button(Words.TXT_LOGOUT);

            //Dodanie eventu wylogowania
            logoutButton.addClickListener((event) -> {
                tempModel.logoutButtonClick(event);
            });

            myDataLayout.addComponent(logoutButton);

            mainLayout.addComponent(myDataLayout);
        }
        return mainLayout;
    }
}
