/*
 * AWS PROJECT (c) 2017 by tmejs (mateusz.rzad@gmail.com)
 * --------------------------------------------------------
 * Niniejszy program chroniony jest prawem autorskim. Jego rozpowszechnianie bez wyraźnej zgody autora
 * jest zabronione. Jakakolwiek ingerencja w oprogramowanie bez upoważnienia autora,
 * w tym w szczególności jego modyfikacja lub nieuprawnione kopiowanie jest sprzeczne z prawem.
 * Wersja opracowana dla Domax Sp. z o.o. z siedzibą w Łężycach
 */
package com.pjkurs.vaadin.ui.containers;

import com.pjkurs.vaadin.views.models.MyModel;
import com.pjkurs.vaadin.views.MyContainer;
import com.vaadin.ui.Component;

/**
 *
 * @author Tmejs
 */
public class RegisterPanel<T extends MyModel> extends MyContainer<T> {

    public RegisterPanel(T model) {
        super(model);
    }

    @Override
    public Component buildView() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
