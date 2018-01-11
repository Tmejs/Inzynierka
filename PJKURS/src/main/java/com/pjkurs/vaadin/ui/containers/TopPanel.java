/*
 * AWS PROJECT (c) 2017 by tmejs (mateusz.rzad@gmail.com)
 * --------------------------------------------------------
 * Niniejszy program chroniony jest prawem autorskim. Jego rozpowszechnianie bez wyraźnej zgody autora
 * jest zabronione. Jakakolwiek ingerencja w oprogramowanie bez upoważnienia autora,
 * w tym w szczególności jego modyfikacja lub nieuprawnione kopiowanie jest sprzeczne z prawem.
 * Wersja opracowana dla Domax Sp. z o.o. z siedzibą w Łężycach
 */
package com.pjkurs.vaadin.ui.containers;

import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.pjkurs.vaadin.views.system.MyModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.TextField;

/**
 *
 * @author Tmejs
 */
public class TopPanel<T extends MyModel> extends MyContainer<T> {

    public TopPanel(T model) {
        super(model);

    }

    @Override
    public void buildView() {
        HorizontalLayout mainLayout = new HorizontalLayout();
        //
        if (getModel() instanceof MainViewModel) {
            MainViewModel currentModel = (MainViewModel) getModel();

            //LOGO aplikacji
            Image logoImage = new Image();
            logoImage.setAlternateText(Words.TXT_LOGO_NAME);
            logoImage.setIcon(currentModel.getLogoResource());

            mainLayout.addComponent(logoImage);

            //Nazwa aplikcji
            TextField textField = new TextField(Words.TXT_APP_NAME);
            mainLayout.addComponent(textField);
            
            
            //Panel logowania
            if (NavigatorUI.getLoginStatus()) {
                mainLayout.addComponent(new LoggedInPanel(currentModel));
            } else {
                mainLayout.addComponent(new LoginPanel(currentModel));
            }
        }
        this.addComponent(mainLayout);
    }

}
