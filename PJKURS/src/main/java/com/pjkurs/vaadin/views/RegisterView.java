/*
 * AWS PROJECT (c) 2017 by tmejs (mateusz.rzad@gmail.com)
 * --------------------------------------------------------
 * Niniejszy program chroniony jest prawem autorskim. Jego rozpowszechnianie bez wyraźnej zgody autora
 * jest zabronione. Jakakolwiek ingerencja w oprogramowanie bez upoważnienia autora,
 * w tym w szczególności jego modyfikacja lub nieuprawnione kopiowanie jest sprzeczne z prawem.
 * Wersja opracowana dla Domax Sp. z o.o. z siedzibą w Łężycach
 */
package com.pjkurs.vaadin.views;

import com.pjkurs.NavigatorUI;
import com.pjkurs.vaadin.models.RegisterViewModel;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ClassResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Tmejs
 */
public class RegisterView extends HorizontalLayout implements View, MyView<RegisterViewModel> {

    RegisterViewModel model;

    @Override
    public void setModel(RegisterViewModel model) {
        this.model = model;
    }

    @Override
    public RegisterViewModel getModel() {
        return model;
    }

    public RegisterView() {
        setModel(new RegisterViewModel(getUI()));
        setSizeFull();

        setSpacing(true);

        buildView();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Showing view: Register!");
    }

    //Zbudowanie Widoku dla panelu rejestrowania
    private void buildView() {
        addComponent(new Button("Back To ma", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                getUI().getNavigator().navigateTo(NavigatorUI.View.MAINVIEW.getName());
            }
        }));
    }

}
