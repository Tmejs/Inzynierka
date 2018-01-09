/*
 * AWS PROJECT (c) 2017 by tmejs (mateusz.rzad@gmail.com)
 * --------------------------------------------------------
 * Niniejszy program chroniony jest prawem autorskim. Jego rozpowszechnianie bez wyraźnej zgody autora
 * jest zabronione. Jakakolwiek ingerencja w oprogramowanie bez upoważnienia autora,
 * w tym w szczególności jego modyfikacja lub nieuprawnione kopiowanie jest sprzeczne z prawem.
 * Wersja opracowana dla Domax Sp. z o.o. z siedzibą w Łężycach
 */
package com.pjkurs.vaadin.views;

import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.models.MyModel;
import com.pjkurs.vaadin.models.RegisterViewModel;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ClassResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Tmejs
 */
public class RegisterView extends MyContainer<RegisterViewModel> implements View, InterfacePJKURSView {

    public RegisterView(RegisterViewModel model) {
        super(model);
    }

    @Override
    public void setModel(RegisterViewModel model) {
        this.model = model;
    }

    @Override
    public RegisterViewModel getModel() {
        return model;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Showing view: Register!");
    }

    //Zbudowanie Widoku dla panelu rejestrowania
    @Override
    public Component buildView() {
        generateTopPanel();

        generateMenu();

        generateMainAppPanel();
        return null;
    }

    @Override
    public Component generateTopPanel() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return null;
    }

    @Override
    public Component generateMenu() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return null;
    }

    @Override
    public Component generateMainAppPanel() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return null;
    }

}
