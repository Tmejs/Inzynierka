/*
 * AWS PROJECT (c) 2017 by tmejs (mateusz.rzad@gmail.com)
 * --------------------------------------------------------
 * Niniejszy program chroniony jest prawem autorskim. Jego rozpowszechnianie bez wyraźnej zgody autora
 * jest zabronione. Jakakolwiek ingerencja w oprogramowanie bez upoważnienia autora,
 * w tym w szczególności jego modyfikacja lub nieuprawnione kopiowanie jest sprzeczne z prawem.
 * Wersja opracowana dla Domax Sp. z o.o. z siedzibą w Łężycach
 */
package com.pjkurs.vaadin.views;

import com.pjkurs.vaadin.views.models.MyAccountViewModel;
import com.pjkurs.vaadin.views.models.RegisterViewModel;
import com.vaadin.navigator.View;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

/**
 *
 * @author Tmejs
 */
public class MyAccountView extends MyContainer<MyAccountViewModel> implements View, InterfacePJKURSView {

    public MyAccountView(MyAccountViewModel model) {
        super(model);
    }
    
    
    @Override
    public void setModel(MyAccountViewModel model) {
        this.model = model;
    }

    @Override
    public Component buildView() {
        return null;
    }

    
    @Override
    public MyAccountViewModel getModel() {
        return model;
    }

    @Override
    public Component generateTopPanel() {
        return null;
    }

    @Override
    public Component generateMenu() {
        return null;
    }

    @Override
    public Component generateMainAppPanel() {
        return null;
    }

}
