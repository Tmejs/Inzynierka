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

import com.pjkurs.domain.Appusers;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.vaadin.data.HasValue;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.ItemClickListener;

import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Tmejs
 */
public class EditableUsersListPanel<T extends AdminViewModel> extends MyContainer<T> {

    public EditableUsersListPanel(T model) {
        super(model);
    }

    private Component usersPanel;
    private Appusers selectedUser;
    private String filter;

    @Override
    public Component buildView() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeUndefined();

        if (selectedUser == null) {
            usersPanel = generateOverwieComponent();
        } else {
            usersPanel = generateEditablePanel(selectedUser);
        }
        layout.addComponent(usersPanel);
        return layout;
    }

    private void refreshPanel() {
        Component newPanel;
        if (selectedUser == null) {
            newPanel = generateOverwieComponent();
        } else {
            newPanel = generateEditablePanel(selectedUser);
        }
        ((VerticalLayout) this.getContent()).replaceComponent(usersPanel, newPanel);
        usersPanel = newPanel;
    }


    private Component generateEditablePanel(Appusers user) {
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
        com.vaadin.ui.DateField birth_dateDatePicker =
                new DateField(Words.TXT_BIRTH_DATE);
        if (selectedUser.getBirth_date() != null) {
            birth_dateDatePicker.setValue(selectedUser.getBirth_date().toLocalDate());
        }
        TextArea contactNumberArea = new TextArea(Words.TXT_PHONE_CONTACT);
        if (selectedUser.getContact_number() != null) {
            contactNumberArea.setValue(selectedUser.getContact_number());
        }

        HorizontalLayout newL = new HorizontalLayout();
        Button saveButton = new Button(Words.TXT_SAVE_DATA);
        saveButton.addClickListener(event -> {
            try{
            editedUser.name = nameArea.getValue();
            editedUser.surname = surnameArea.getValue();
            editedUser.birth_date =
                    Date.valueOf(birth_dateDatePicker.getValue());
            editedUser.contact_number = contactNumberArea.getValue();
            NavigatorUI.getDBProvider().updateAppuser(editedUser);
            selectedUser = null;
            refreshPanel();
            }catch (Exception e){
                Notification.show(Words.TXT_NOT_SAVED_CHECK_DATA);
                Logger.getGlobal().log(Level.SEVERE, "Błąd przy updateAppuser", e);
            }
        });
        Button undoButton = new Button(Words.TXT_DISCARD_CHANGES);
        undoButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                selectedUser = null;
                refreshPanel();
            }
        });
        newL.addComponent(saveButton);
        newL.addComponent(undoButton);

        layout.addComponent(emailArea);
        layout.addComponent(nameArea);
        layout.addComponent(surnameArea);
        layout.addComponent(birth_dateDatePicker);
        layout.addComponent(contactNumberArea);
        layout.addComponent(newL);
        return layout;
    }

    private Component generateOverwieComponent() {
        VerticalLayout lay = new VerticalLayout();
        Grid<Appusers> userGrid = new Grid<>();
        TextArea filterArea = new TextArea(Words.TXT_FIND,
                new HasValue.ValueChangeListener<String>() {
                    @Override
                    public void valueChange(HasValue.ValueChangeEvent<String> event) {
                        filter = event.getValue();
                        List<Appusers> filteredUserList = NavigatorUI.getDBProvider().getUsers();
                        if (filter != null && !filter.isEmpty()) {
                            filteredUserList =
                                    filteredUserList.stream()
                                            .filter(appusers -> checkFilter(appusers,
                                                    filter)).collect(Collectors.toList());
                        }
                        userGrid.setItems(filteredUserList);
                    }
                });
        if (filter != null)
            filterArea.setValue(filter);

        lay.addComponent(filterArea);
        List<Appusers> filteredUserList = NavigatorUI.getDBProvider().getUsers();

        userGrid.setItems(filteredUserList);
        userGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        userGrid.setColumnReorderingAllowed(true);
        userGrid.setSizeFull();

        userGrid.setResponsive(true);
        //Kolumny

        userGrid.addColumn(Appusers::getEmail).setCaption(Words.TXT_COURSE_NAME);
        userGrid.addColumn(Appusers::getName).setCaption(Words.TXT_NAME);
        userGrid.addColumn(Appusers::getSurname).setCaption(Words.TXT_SURRNAME);
        userGrid.setColumnResizeMode(ColumnResizeMode.SIMPLE);
        userGrid.addItemClickListener(new ItemClickListener<Appusers>() {
            @Override
            public void itemClick(Grid.ItemClick<Appusers> event) {
                selectedUser = event.getItem();
                refreshPanel();
            }
        });

        lay.addComponent(userGrid);
        return lay;
    }

    private boolean checkFilter(Appusers appusers, String filter) {
        if (appusers.getName() != null) {
            if (appusers.getName().contains(filter)) {
                return true;
            }
        }
        if (appusers.getSurname() != null) {
            if (appusers.getSurname().contains(filter)) {
                return true;
            }
        }

        if (appusers.getEmail() != null) {
            if (appusers.getEmail().contains(filter)) {
                return true;
            }
        }
        return false;

    }
}
