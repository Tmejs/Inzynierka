/*
 * AWS PROJECT (c) 2017 by tmejs (mateusz.rzad@gmail.com)
 * --------------------------------------------------------
 * Niniejszy program chroniony jest prawem autorskim. Jego rozpowszechnianie bez wyraźnej zgody autora
 * jest zabronione. Jakakolwiek ingerencja w oprogramowanie bez upoważnienia autora,
 * w tym w szczególności jego modyfikacja lub nieuprawnione kopiowanie jest sprzeczne z prawem.
 * Wersja opracowana dla Domax Sp. z o.o. z siedzibą w Łężycach
 */
package com.pjkurs.vaadin.views.system;

import com.vaadin.navigator.View;
import com.vaadin.ui.Component;


/**
 *
 * @author Tmejs
 */
public interface InterfaceMyView<T extends MyModel> extends View {

        //Połączenie modelu z kontrolerem
        public void setModel(T model);

        public T getModel();
        
        public Component buildView();
        
        public void refreshView();
        
        //
    }
