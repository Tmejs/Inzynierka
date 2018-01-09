/*
 * AWS PROJECT (c) 2017 by tmejs (mateusz.rzad@gmail.com)
 * --------------------------------------------------------
 * Niniejszy program chroniony jest prawem autorskim. Jego rozpowszechnianie bez wyraźnej zgody autora
 * jest zabronione. Jakakolwiek ingerencja w oprogramowanie bez upoważnienia autora,
 * w tym w szczególności jego modyfikacja lub nieuprawnione kopiowanie jest sprzeczne z prawem.
 * Wersja opracowana dla Domax Sp. z o.o. z siedzibą w Łężycach
 */
package com.pjkurs.vaadin.models;

import com.vaadin.ui.UI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Tmejs
 */
public class MyModel {

    private HashMap<String, Object> modelParams;

    public MyModel() {
        modelParams = new HashMap<>();
    }

    public void setParam(String paramName, Object value) throws Exception {
        //Sprawdzenie czy nazwa null
        if (paramName == null) {
            throw new Exception();
        }

        modelParams.put(paramName, value);
    }

    public void deleteParameter(String paramName) {
        modelParams.remove(paramName);
    }

    public <T> T getParam(String paramName) {
        return (T) modelParams.get(paramName);
    }

}
