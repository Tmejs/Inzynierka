/*
 * AWS PROJECT (c) 2017 by tmejs (mateusz.rzad@gmail.com)
 * --------------------------------------------------------
 * Niniejszy program chroniony jest prawem autorskim. Jego rozpowszechnianie bez wyraźnej zgody autora
 * jest zabronione. Jakakolwiek ingerencja w oprogramowanie bez upoważnienia autora,
 * w tym w szczególności jego modyfikacja lub nieuprawnione kopiowanie jest sprzeczne z prawem.
 * Wersja opracowana dla Domax Sp. z o.o. z siedzibą w Łężycach
 */
package com.pjkurs.vaadin.views;

import com.pjkurs.vaadin.views.models.MyModel;
import com.vaadin.navigator.View;
import com.vaadin.ui.Component;

/**
 *
 * @author Tmejs
 */
public interface InterfacePJKURSView {
    
    
    
    //Generacja Informacyjnego okna aplikacji
    public Component generateTopPanel();

    //Generacja menu aplikacji
    public Component generateMenu();

    //Generacja głównego okna aplikacji
    public Component generateMainAppPanel();
}
