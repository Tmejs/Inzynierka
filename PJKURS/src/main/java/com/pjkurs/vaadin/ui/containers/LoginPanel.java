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
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.pjkurs.vaadin.views.system.MyModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Tmejs
 */
public class LoginPanel<T extends MyModel> extends MyContainer<T> {

    public LoginPanel(T model) {
        super(model);
    }

    @Override
    public void buildView() {
        HorizontalLayout mainLayout = new HorizontalLayout();

        //Budowa okna w zalezności od strony na której jest wświetlane
        if (getModel() instanceof MainViewModel) {
            MainViewModel tempModel = (MainViewModel) getModel();
            mainLayout.setWidth("100%");
            
            mainLayout.addComponent(new Label(Words.TXT_LOGIN_TO_SERWIS));
            /*
            Email : []
            Hasało: []
            [Zarejestruj]       [Zaloguj]
            Zapomniałeś hasła(url do storny z przypomniajka)
            
            
            
            */
            
            

        }

        this.addComponent(mainLayout);
    }

}
