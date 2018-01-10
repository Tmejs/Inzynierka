/*
 * AWS PROJECT (c) 2017 by tmejs (mateusz.rzad@gmail.com)
 * --------------------------------------------------------
 * Niniejszy program chroniony jest prawem autorskim. Jego rozpowszechnianie bez wyraźnej zgody autora
 * jest zabronione. Jakakolwiek ingerencja w oprogramowanie bez upoważnienia autora,
 * w tym w szczególności jego modyfikacja lub nieuprawnione kopiowanie jest sprzeczne z prawem.
 * Wersja opracowana dla Domax Sp. z o.o. z siedzibą w Łężycach
 */
package com.pjkurs.vaadin.views.models;

import com.pjkurs.vaadin.views.system.MyModel;
import com.pjkurs.vaadin.views.RegisterView;
import com.vaadin.ui.UI;

/**
 *
 * @author Tmejs
 */
public class RegisterViewModel extends MyModel<RegisterView> {


    public RegisterViewModel(UI ui) {
        super();
        this.ui = ui;
    }
    
}
