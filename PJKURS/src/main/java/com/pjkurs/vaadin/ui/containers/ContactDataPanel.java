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
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.data.HasValue;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tmejs
 */
public class ContactDataPanel<T extends MainViewModel> extends MyContainer<T> {

    public ContactDataPanel(T model) {
        super(model);
    }

    @Override
    public Component buildView() {
        HorizontalLayout layout = new HorizontalLayout();

        layout.addComponent(buildContactFormular());

        layout.addComponent(buildContactData());

        return layout;
    }

    private Component buildContactData() {
        VerticalLayout layout = new VerticalLayout();
        Label nameLabel = new Label(Words.TXT_CONTACT_NAME);
        layout.addComponent(nameLabel);

        Label descLabel = new Label(Words.TXT_CONTACT_DESCRIPTION);

        layout.addComponent(descLabel);

        Label addressLabel = new Label(Words.TXT_CONTACT_ADDRESS);

        layout.addComponent(addressLabel);

        return layout;
    }

    private Component buildContactFormular() {
        VerticalLayout layout = new VerticalLayout();

        final String[] data = new String[2];
        Button sendFormularButton = new Button(Words.TXT_SEND_QUESTION, (event) -> {
            Logger.getGlobal().log(Level.SEVERE, data[0] + "   " + data[1]);
        });
        sendFormularButton.setEnabled(false);

        TextField nameField = new TextField(Words.TXT_EMAIL);
        nameField.addValueChangeListener(new HasValue.ValueChangeListener<String>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<String> event) {
                data[0] = event.getValue();

            }
        });
        if (NavigatorUI.getLoggedUser() != null) {
            nameField.setValue(NavigatorUI.getLoggedUser().getEmail());
            nameField.setEnabled(false);
        }

        layout.addComponent(nameField);

        TextArea infoField = new TextArea(Words.TXT_MESSAGE);
        infoField.setHeight(null);
        infoField.addValueChangeListener(new HasValue.ValueChangeListener<String>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<String> event) {
                data[1] = event.getValue();
                sendFormularButton.setEnabled(checkCanSend(data));
            }
        });

        layout.addComponent(infoField);

        layout.addComponent(sendFormularButton);

        return layout;
    }

    private Boolean checkCanSend(String[] data) {

        if (data[0] == null || data[1] == null) {
            return false;
        }

        if (data[1].length() < 10) {
            return false;
        }

        if (!data[0].contains("@")) {
            return false;
        }

        return true;

    }

}
