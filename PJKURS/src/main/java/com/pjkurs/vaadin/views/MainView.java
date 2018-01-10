/*
 * AWS PROJECT (c) 2017 by tmejs (mateusz.rzad@gmail.com)
 * --------------------------------------------------------
 * Niniejszy program chroniony jest prawem autorskim. Jego rozpowszechnianie bez wyraźnej zgody autora
 * jest zabronione. Jakakolwiek ingerencja w oprogramowanie bez upoważnienia autora,
 * w tym w szczególności jego modyfikacja lub nieuprawnione kopiowanie jest sprzeczne z prawem.
 * Wersja opracowana dla Domax Sp. z o.o. z siedzibą w Łężycach
 */
package com.pjkurs.vaadin.views;

import com.pjkurs.db.DbDataProvider;
import com.pjkurs.domain.Client;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.ui.containers.LoggedInPanel;
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tmejs
 */
public class MainView extends MyContainer<MainViewModel> implements View, InterfacePJKURSView {

    Button notifButton;

    public MainView(MainViewModel model) {
        super(model);
    }

    //Zbudowanie widoku głównej aplikacji
    @Override
    public Component buildView() {
        addComponent(generateTopPanel());
        addComponent(generateMenu());
        addComponent(generateMainAppPanel());
        return null;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Showing view: Main!");
        removeAllComponents();
        buildView();
    }

    private Component generateLoginWindow() {
        Component comp = null;

        //W przypadku zalogowania
        if (NavigatorUI.getLoginStatus()) {
            Logger.getGlobal().log(Level.ALL, "login true");
            comp = new LoggedInPanel<MainViewModel>(getModel());

        } else {
            Logger.getGlobal().log(Level.ALL, "login false");
            comp = generateLoggedOutLoginComponent();
        }

        //comp style
        comp.setWidth("40%");
        return comp;

    }

    public final Component generateTopPanel() {
        if (notifButton == null) {
            Button notif = new Button("Wyswietl Notif", clickEvent -> {
                model.notifButtonClicked(clickEvent);
            });
            notifButton = notif;
        }
        return notifButton;
//        //Główny komponent
//        HorizontalLayout horizontalLayout = new HorizontalLayout();
//        horizontalLayout.setStyleName("to-horizontalLayout");
//        //Rozmiary komponentu
//        horizontalLayout.setWidth("100%");
//        horizontalLayout.setHeight("20%");
//        //Budowanie panelu
//
//
//        //TODO podmienić na zdjęcie
//        TextArea area = new TextArea("Tutaj będzie logo aplikacji");
//        area.setWidth("30%");
//
//        horizontalLayout.addComponent(area);
//
//        //Nazwa aplikacji
//        //TODO podmienić na zdjęcie
//        TextField appNameArea = new TextField(Words.TXT_APP_NAME);
//        area.setWidth("30%");
//
//        horizontalLayout.addComponent(appNameArea);
//
//        //Okno logowania
//        horizontalLayout.addComponent(generateLoginWindow());
//
//        return horizontalLayout;
//    }
//
//    private Component generateLoggedLoginComponent() {
//        VerticalLayout verticalLayout = new VerticalLayout();
//        verticalLayout.setWidth("100%");
//        /*
//
//        *********************************************
//        *  Zalogowany jako:
//        *  Imie    Nazwisko
//        ***********************************************
//         */
//        VerticalLayout logedDataLayout = new VerticalLayout();
//
//        TextField logedAs = new TextField();
//        logedAs.setPlaceholder(Words.TXT_LOGGED_ASS);
//        logedAs.setReadOnly(true);
//        logedDataLayout.addComponent(logedAs);
//
//        VerticalLayout logedDataNameLayout = new VerticalLayout();
//
//        TextField logedName = new TextField();
//        logedName.setReadOnly(true);
//        logedName.setPlaceholder((String) VaadinSession.getCurrent().getAttribute(Words.SESSION_LOGGED_LOGIN));
//        logedName.setCaption((String) VaadinSession.getCurrent().getAttribute(Words.TXT_NAME));
//        logedDataNameLayout.addComponent(logedName);
//
//        TextField logedEmail = new TextField();
//        logedEmail.setReadOnly(true);
//        logedEmail.setPlaceholder((String) VaadinSession.getCurrent().getAttribute(Words.SESSION_LOGGED_EMAIL));
//        logedEmail.setCaption(Words.TXT_EMAIL);
//        logedDataNameLayout.addComponent(logedEmail);
//
//        logedDataLayout.addComponent(logedDataNameLayout);
//
//        verticalLayout.addComponent(logedDataLayout);
//        /*
//        ***********************************************
//        *
//        *  Moje dane.
//        *                       (Wyloguj)
//         */
//
//        VerticalLayout myDataLayout = new VerticalLayout();
//
//        Button myDataButton = new Button(Words.TXT_MY_DATA);
//
//        myDataLayout.addComponent(myDataButton);
//
//        Button logoutButton = new Button(Words.TXT_LOGOUT);
//
//        logoutButton.setStyleName(ValoTheme.BUTTON_DANGER);
//        logoutButton.addClickListener((event) -> {
//            model.logoutButtonClick(event);
//        });
//
//        myDataLayout.addComponent(logoutButton);
//
//        verticalLayout.addComponent(myDataLayout)
//
//        return verticalLayout;
    }


    private Component generateLoggedOutLoginComponent() {

        HorizontalLayout retLayout = new HorizontalLayout();

        Button loginButton = new Button(Words.TXT_LOGIN);
        //TODO przykład stylowania guzika
        loginButton.setPrimaryStyleName("to-horizontalLayout");
//        loginButton.setTheme("valo");
        loginButton.addClickListener((event) -> {
            model.registerButtonClicked(event);
        });

        retLayout.addComponent(loginButton);

        return retLayout;
    }
    
    @Override
    public Component generateMenu() {
        //Generowanie menu jeśli zalogowany
        if (NavigatorUI.getLoginStatus()) {
            return generateMenuPanelIfLogged();
        } else {
            return generateMenuPanelIfLoggedOut();
        }
    }

    private Component generateMenuPanelIfLogged() {
        return new Button("Buttonik bo zalogowany");
    }

    private Component generateMenuPanelIfLoggedOut() {
        Button loggedButton = new Button("NIEzalogowany", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                model.niezlogowanyButtonClicked(event);
            }
        });

        return loggedButton;
    }

    @Override
    public Component generateMainAppPanel() {
        return new TextArea("Main component");
    }

    public void setLoginButtonClicked() {
        Button notif = new Button("Juz kliknieto", clickEvent -> {
            model.notifButtonClicked(clickEvent);
        });
       
        this.replaceComponent(notifButton, notif);
         notifButton = notif;
       
//        Notification.show("Notif button clicked");
    }

}
