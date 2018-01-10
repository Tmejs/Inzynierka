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
import com.pjkurs.vaadin.views.MyAccountView;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

/**
 *
 * @author Tmejs
 */
public class MyAccountViewModel extends MyModel<MyAccountView> {

    public MyAccountViewModel(UI ui) {
        this.currentUI = ui;
    }
    
    public void myCoursesButtonClicked(Button.ClickEvent event){
        
    }
    
    public void myDataButtonClicked(Button.ClickEvent event){
        
    }
    
    public void editMyDataButtonClicked(Button.ClickEvent event){
        
    }
    
    public void contactButtonClicked(Button.ClickEvent event){
        
    }
    
    
    
    
}
