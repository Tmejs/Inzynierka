/*
 * AWS PROJECT (c) 2017 by tmejs (mateusz.rzad@gmail.com)
 * --------------------------------------------------------
 * Niniejszy program chroniony jest prawem autorskim. Jego rozpowszechnianie bez wyraźnej zgody autora
 * jest zabronione. Jakakolwiek ingerencja w oprogramowanie bez upoważnienia autora,
 * w tym w szczególności jego modyfikacja lub nieuprawnione kopiowanie jest sprzeczne z prawem.
 * Wersja opracowana dla Domax Sp. z o.o. z siedzibą w Łężycach
 */
package com.pjkurs.vaadin.views.system;

import com.vaadin.ui.UI;
import java.util.HashMap;

/**
 *
 * @author Tmejs
 */
public class MyModel<T> {

    private HashMap<String, Object> modelParams;
    private T view;
    public UI currentUI;

    public UI getUi() {
        return currentUI;
    }

    public MyModel() {
        modelParams = new HashMap<>();
    }

    public void setView(T view) {
        this.view = view;
    }

    public T getView() {
        return view;
    }

    public void setParam(String paramName, Object value) {
        modelParams.put(paramName, value);
    }

    public void deleteParameter(String paramName) {
        modelParams.remove(paramName);
    }

    public <T> T getParam(String paramName) {
        return (T) modelParams.get(paramName);
    }

}
