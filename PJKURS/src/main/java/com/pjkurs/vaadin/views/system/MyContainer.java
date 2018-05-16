/*
 * AWS PROJECT (c) 2017 by tmejs (mateusz.rzad@gmail.com)
 * --------------------------------------------------------
 * Niniejszy program chroniony jest prawem autorskim. Jego rozpowszechnianie bez wyraźnej zgody autora
 * jest zabronione. Jakakolwiek ingerencja w oprogramowanie bez upoważnienia autora,
 * w tym w szczególności jego modyfikacja lub nieuprawnione kopiowanie jest sprzeczne z prawem.
 * Wersja opracowana dla Domax Sp. z o.o. z siedzibą w Łężycach
 */
package com.pjkurs.vaadin.views.system;

import com.vaadin.ui.Panel;

/**
 *
 * @author Tmejs
 */
public abstract class MyContainer<T extends MyModel> extends Panel implements InterfaceMyView<T> {

    private T model;
    private final String CLASS_NAME;

    @Override
    public void setModel(T model) {
        this.model = model;
    }

    @Override
    public T getModel() {
        return model;
    }

    public String getClassNameToStyle() {
        return CLASS_NAME;
    }

    public MyContainer(T model, Boolean isMainModel) {
        setModel(model);
        CLASS_NAME = getClass().toGenericString().toLowerCase();
        if (isMainModel) {
            model.setView(this);
        }
        this.setContent(buildView());
    }

    /**
     * Konstruktor ustawiający model i generujący komponent
     *
     * @param model
     */
    public MyContainer(T model) {
        setModel(model);
        CLASS_NAME = getClass().toGenericString().toLowerCase();
        this.setContent(buildView());
    }

    /**
     * Konstruktor
     *
     * @param generateView Czy automatycznie ustawiać content?
     * @param model
     */
    public MyContainer(Boolean generateView, T model) {
        setModel(model);
        CLASS_NAME = getClass().toGenericString().toLowerCase();
        if (generateView) {
            this.setContent(buildView());
        }
    }

    private MyContainer() {
        CLASS_NAME = getClass().toGenericString();
    }

    @Override
    public void refreshView() {
        this.setContent(buildView());
    }

}
