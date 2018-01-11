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
package com.pjkurs.vaadin.ui.containers;

import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.pjkurs.vaadin.views.system.MyModel;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
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
    public void buildView() {
        VerticalLayout mainLayout = new VerticalLayout();
        if (getModel() instanceof MainViewModel) {
            MainViewModel tempModel = (MainViewModel) getModel();
            mainLayout.setWidth("100%");
            /*

        *********************************************
        *  Zalogowany jako:
        *  Imie    Nazwisko
        ***********************************************
             */
            VerticalLayout logedDataLayout = new VerticalLayout();

            TextField logedAs = new TextField();
            logedAs.setPlaceholder(Words.TXT_LOGGED_ASS);
            logedAs.setReadOnly(true);
            logedDataLayout.addComponent(logedAs);

            VerticalLayout logedDataNameLayout = new VerticalLayout();

            TextField logedName = new TextField();
            logedName.setReadOnly(true);
            logedName.setPlaceholder((String) VaadinSession.getCurrent().getAttribute(Words.SESSION_LOGGED_LOGIN));
            logedName.setCaption((String) VaadinSession.getCurrent().getAttribute(Words.TXT_NAME));
            logedDataNameLayout.addComponent(logedName);

            TextField logedEmail = new TextField();
            logedEmail.setReadOnly(true);
            logedEmail.setPlaceholder((String) VaadinSession.getCurrent().getAttribute(Words.SESSION_LOGGED_EMAIL));
            logedEmail.setCaption(Words.TXT_EMAIL);
            logedDataNameLayout.addComponent(logedEmail);

            logedDataLayout.addComponent(logedDataNameLayout);

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
                tempModel.myDataButtonClicked(event);
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
        this.addComponent(mainLayout);
    }
}
