/*
 * AWS PROJECT (c) 2017 by tmejs (mateusz.rzad@gmail.com)
 * --------------------------------------------------------
 * Niniejszy program chroniony jest prawem autorskim. Jego rozpowszechnianie bez wyraźnej zgody autora
 * jest zabronione. Jakakolwiek ingerencja w oprogramowanie bez upoważnienia autora,
 * w tym w szczególności jego modyfikacja lub nieuprawnione kopiowanie jest sprzeczne z prawem.
 * Wersja opracowana dla Domax Sp. z o.o. z siedzibą w Łężycach
 */
package com.pjkurs.vaadin.views;

import com.pjkurs.vaadin.views.system.InterfaceMyView;
import com.pjkurs.vaadin.views.system.MyModel;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Tmejs
 */
public abstract class MyContainer<T extends MyModel> extends VerticalLayout implements InterfaceMyView<T> {

    T model;
    @Override
    public void setModel(T model) {
        this.model = model;
    }

    @Override
    public T getModel() {
        return model;
    }

    public MyContainer(T model) {
        setModel(model);
        model.setView(this);
        buildView();
    }
    
    private MyContainer(){
    }
}
