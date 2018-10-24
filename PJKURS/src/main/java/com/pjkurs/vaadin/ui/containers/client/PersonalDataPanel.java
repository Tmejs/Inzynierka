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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pjkurs.domain.Appusers;
import com.pjkurs.domain.Discount;
import com.pjkurs.domain.GrantedDiscount;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.pjkurs.vaadin.views.system.MyModel;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * @author Tmejs
 */
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
        TextArea contactNumberArea = new TextArea(Words.TXT_PHONE_CONTACT);
        if (selectedUser.getContact_number() != null) {
            contactNumberArea.setValue(selectedUser.getContact_number());
        }

        Component discountComponent = generateDiscountComponent();

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

        layout.addComponent(emailArea);
        layout.addComponent(discountComponent);
        layout.addComponent(nameArea);
        layout.addComponent(surnameArea);
        layout.addComponent(birth_dateDatePicker);
        layout.addComponent(contactNumberArea);
        layout.addComponent(editDataButton);
        return layout;
    }

    private Component generateDiscountComponent() {
        HorizontalLayout lay = new HorizontalLayout();
        Label label = new Label();
        label.setCaption(Words.TXT_DISCOUNT_DISCOUNT_VALUE);
        GrantedDiscount discount = NavigatorUI.getDBProvider().getUserGrantedDiscount(selectedUser);
        if (discount != null && (discount.isConfirmed != null || !discount.isConfirmed)) {
            String value = "";
            if (discount == null) {
                value = discount.name + "(" + Words.TXT_AWAITING_FOR_CONFIRMATION + ")";
            } else if (discount.isConfirmed) {
                if (discount.money != null) {
                    value = discount.money.toString() + " PLN";
                } else {
                    value = discount.discount_precentage.toString() + " %";
                }
            }
            label.setValue(value);
            return label;
        }
        lay.addComponent(label);
        lay.addComponent(generateAskForDiscountPanel());
        return lay;
    }

    private Component generateAskForDiscountPanel() {
        Button askForDiscountButton = new Button(Words.TXT_APPLY_FOR_DISCOUNT);
        askForDiscountButton.addClickListener(mainEvent -> {
            com.vaadin.ui.Window subWindow = new Window(Words.TXT_INSERT_NEW_CATEGORY_DATA);
            VerticalLayout subContent = new VerticalLayout();

            TextArea discountDescriptionArea = new TextArea();
            discountDescriptionArea.setReadOnly(true);
            discountDescriptionArea.setVisible(false);
            discountDescriptionArea.setCaption(Words.TXT_DESCRIPTION);

            List<Discount> discountList = NavigatorUI.getDBProvider().getDicsounts();
            NativeSelect<Discount> discountSelect = new NativeSelect<>();
            discountSelect.setItems(discountList);
            discountSelect.setEmptySelectionAllowed(false);
            discountSelect.setItemCaptionGenerator(item -> item.name);
            discountSelect.addValueChangeListener(event -> {
                if (event.getValue().description != null) {
                    discountDescriptionArea.setValue(event.getValue().description);
                    discountDescriptionArea.setVisible(true);
                } else {
                    discountDescriptionArea.setVisible(false);
                }
            });

            TextArea reasonArea = new TextArea();
            reasonArea.setCaption(Words.TXT_REASON);

            Button addButton = new Button(Words.TXT_APPLY);
            addButton.addClickListener(event -> {
                if (reasonArea.getValue() == null) {
                    Notification.show(Words.TXT_REASON_CANT_BE_EMPTY);
                } else if (discountSelect.getValue() == null) {
                    Notification.show(Words.TXT_SELECT_DISCOUNT);
                } else {
                    GrantedDiscount discount = new GrantedDiscount();
                    Discount selectedDiscount = discountSelect.getValue();
                    discount.id = selectedDiscount.id;
                    discount.isConfirmed = false;
                    discount.userDescription = reasonArea.getValue();
                    discount.appusers_id = selectedUser.id;

                    if (NavigatorUI.getDBProvider().addAplicationForDiscount(discount)) {
                        subWindow.close();
                        refreshPanel();
                        Notification.show(Words.TXT_CORRECTLY_SEND);
                    } else {
                        Notification.show(Words.TXT_SOMETHIN_WENT_WRONG);
                    }
                }
            });
            subContent.addComponent(discountDescriptionArea);
            subContent.addComponent(discountSelect);
            subContent.addComponent(reasonArea);
            subContent.addComponent(addButton);
            subWindow.setContent(subContent);
            subWindow.center();
            getModel().currentUI.addWindow(subWindow);
        });

        return askForDiscountButton;
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
        layout.addComponent(contactNumberArea);
        layout.addComponent(passwordArea);
        layout.addComponent(newL);
        return layout;
    }
}
