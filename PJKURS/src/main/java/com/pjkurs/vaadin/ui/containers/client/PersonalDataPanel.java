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
package com.pjkurs.vaadin.ui.containers.client;

import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pjkurs.domain.Appusers;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.pjkurs.vaadin.views.system.MyModel;
import com.vaadin.annotations.Theme;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Tmejs
 */
@Theme("pjtheme")
public class PersonalDataPanel<T extends MyModel> extends MyContainer<T> {

    public PersonalDataPanel(T model) {
        super(model);
    }

    private Appusers selectedUser;
    private Boolean isEditable = false;
    private Component myDataPanel;

    @Override
    public Component buildView() {
        VerticalLayout mainView = new VerticalLayout();
        selectedUser = NavigatorUI.getDBProvider().getUser(NavigatorUI.getLoggedUser().email);
        mainView.setSizeFull();
        myDataPanel = buildMyDataPanel();
        mainView.addComponent(myDataPanel);

        return mainView;
    }

    private void refreshPanel() {
        Component newPanel;
        if (isEditable) {
            newPanel = generateEditablePanel();
        } else {
            newPanel = buildMyDataPanel();
        }

        ((VerticalLayout) this.getContent()).replaceComponent(myDataPanel, newPanel);
        myDataPanel = newPanel;
    }

    private Component buildMyDataPanel() {
        VerticalLayout layout = new VerticalLayout();
        selectedUser = NavigatorUI.getDBProvider().getUser(selectedUser.email);
        TextArea emailArea = new TextArea(Words.TXT_EMAIL, selectedUser.getEmail());
        TextArea nameArea = new TextArea(Words.TXT_NAME);
        if (selectedUser.getName() != null) {
            nameArea.setValue(selectedUser.getName());
        }
        TextArea surnameArea = new TextArea(Words.TXT_SURRNAME);
        if (selectedUser.getSurname() != null) {
            surnameArea.setValue(selectedUser.getSurname());
        }
        DateField birth_dateDatePicker =
                new DateField(Words.TXT_BIRTH_DATE);
        if (selectedUser.getBirth_date() != null) {
            birth_dateDatePicker.setValue(selectedUser.getBirth_date().toLocalDate());
        }

        TextArea placeOfBirth = new TextArea(Words.TXT_PLACE_OF_BIRTH);
        if (selectedUser.getPlace_of_birth() != null) {
            surnameArea.setValue(selectedUser.getPlace_of_birth());
        }


        TextArea contactNumberArea = new TextArea(Words.TXT_PHONE_CONTACT);
        if (selectedUser.getContact_number() != null) {
            contactNumberArea.setValue(selectedUser.getContact_number());
        }

        Button editDataButton = new Button(Words.TXT_EDIT);
        editDataButton.addClickListener(event -> {
            isEditable = true;
            refreshPanel();
        });

        emailArea.setReadOnly(true);
        nameArea.setReadOnly(true);
        surnameArea.setReadOnly(true);
        contactNumberArea.setReadOnly(true);
        birth_dateDatePicker.setReadOnly(true);
        placeOfBirth.setReadOnly(true);

        layout.addComponent(emailArea);
        layout.addComponent(nameArea);
        layout.addComponent(surnameArea);
        layout.addComponent(birth_dateDatePicker);
        layout.addComponent(placeOfBirth);
        layout.addComponent(contactNumberArea);
        layout.addComponent(editDataButton);
        return layout;
    }

    private Component generateEditablePanel() {
        VerticalLayout layout = new VerticalLayout();
        Appusers editedUser = new Appusers(selectedUser);

        TextArea emailArea = new TextArea(Words.TXT_EMAIL, selectedUser.getEmail());
        emailArea.setReadOnly(true);
        TextArea nameArea = new TextArea(Words.TXT_NAME);
        if (selectedUser.getName() != null) {
            nameArea.setValue(selectedUser.getName());
        }
        TextArea surnameArea = new TextArea(Words.TXT_SURRNAME);
        if (selectedUser.getSurname() != null) {
            surnameArea.setValue(selectedUser.getSurname());
        }
        DateField birth_dateDatePicker =
                new DateField(Words.TXT_BIRTH_DATE);
        if (selectedUser.getBirth_date() != null) {
            birth_dateDatePicker.setValue(selectedUser.getBirth_date().toLocalDate());
        }

        TextArea placeOfBirth = new TextArea(Words.TXT_PLACE_OF_BIRTH);
        if (selectedUser.getPlace_of_birth() != null) {
            surnameArea.setValue(selectedUser.getPlace_of_birth());
        }

        TextArea contactNumberArea = new TextArea(Words.TXT_PHONE_CONTACT);
        if (selectedUser.getContact_number() != null) {
            contactNumberArea.setValue(selectedUser.getContact_number());
        }

        TextArea passwordArea = new TextArea(Words.TXT_PASSWORD);
        if (selectedUser.getPassword() != null) {
            passwordArea.setValue(selectedUser.getPassword());
        }

        HorizontalLayout newL = new HorizontalLayout();
        Button saveButton = new Button(Words.TXT_SAVE_DATA);
        saveButton.addClickListener(event -> {
            try {
                editedUser.name = nameArea.getValue();
                editedUser.surname = surnameArea.getValue();
                editedUser.birth_date =
                        Date.valueOf(birth_dateDatePicker.getValue());
                editedUser.contact_number = contactNumberArea.getValue();
                editedUser.password = passwordArea.getValue();
                editedUser.place_of_birth = placeOfBirth.getValue();
                NavigatorUI.getDBProvider().updateAppuser(editedUser);
                selectedUser = NavigatorUI.getDBProvider().getUser(editedUser.email);
                isEditable = false;
                refreshPanel();
            } catch (Exception e) {
                Notification.show(Words.TXT_NOT_SAVED_CHECK_DATA);
                Logger.getGlobal().log(Level.SEVERE, "Błąd przy updateAppuser", e);
            }
        });

        Button undoButton = new Button(Words.TXT_DISCARD_CHANGES);
        undoButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                isEditable = false;
                refreshPanel();
            }
        });
        newL.addComponent(saveButton);
        newL.addComponent(undoButton);

        layout.addComponent(emailArea);
        layout.addComponent(nameArea);
        layout.addComponent(surnameArea);
        layout.addComponent(birth_dateDatePicker);
        layout.addComponent(placeOfBirth);
        layout.addComponent(contactNumberArea);
        layout.addComponent(passwordArea);
        layout.addComponent(newL);
        return layout;
    }
}
