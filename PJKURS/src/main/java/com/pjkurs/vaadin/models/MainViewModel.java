/*
 * AWS PROJECT (c) 2017 by tmejs (mateusz.rzad@gmail.com)
 * --------------------------------------------------------
 * Niniejszy program chroniony jest prawem autorskim. Jego rozpowszechnianie bez wyraźnej zgody autora
 * jest zabronione. Jakakolwiek ingerencja w oprogramowanie bez upoważnienia autora,
 * w tym w szczególności jego modyfikacja lub nieuprawnione kopiowanie jest sprzeczne z prawem.
 * Wersja opracowana dla Domax Sp. z o.o. z siedzibą w Łężycach
 */
package com.pjkurs.vaadin.models;

import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

/**
 *
 * @author Tmejs
 */
public class MainViewModel extends MyModel {

    public final UI currentUI;
    
    public MainViewModel(UI ui) {
        this.currentUI=ui;
    }

    public void loginButtonClick(Button.ClickEvent event){
        
    }
    public void logoutButtonClick(Button.ClickEvent event) {
       
    }
    
}
