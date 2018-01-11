/*
 * AWS PROJECT (c) 2017 by tmejs (mateusz.rzad@gmail.com)
 * --------------------------------------------------------
 * Niniejszy program chroniony jest prawem autorskim. Jego rozpowszechnianie bez wyraźnej zgody autora
 * jest zabronione. Jakakolwiek ingerencja w oprogramowanie bez upoważnienia autora,
 * w tym w szczególności jego modyfikacja lub nieuprawnione kopiowanie jest sprzeczne z prawem.
 * Wersja opracowana dla Domax Sp. z o.o. z siedzibą w Łężycach
 */
package com.pjkurs.vaadin.views;

import com.pjkurs.vaadin.views.system.MyContainer;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.ui.containers.LoggedInPanel;
import com.pjkurs.vaadin.ui.containers.LoginPanel;
import com.pjkurs.vaadin.ui.containers.TopPanel;
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
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
    public void buildView() {
        addComponent(generateTopPanel());
        addComponent(generateMenu());
        addComponent(generateMainAppPanel());
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

    @Override
    public final Component generateTopPanel() {
        //zwracamy top panel
        return new TopPanel<>(getModel());
    }

    private Component generateLoggedOutLoginComponent() {

        HorizontalLayout retLayout = new HorizontalLayout();

        Button loginButton = new Button(Words.TXT_LOGIN);
        //TODO przykład stylowania guzika

//        loginButton.setTheme("valo");
        loginButton.addClickListener((event) -> {
            getModel().registerButtonClicked(event);
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
       return new LoginPanel(getModel());
    }

    private Component generateMenuPanelIfLoggedOut() {
        Button loggedButton = new Button("NIEzalogowany", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                getModel().niezlogowanyButtonClicked(event);
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
            getModel().notifButtonClicked(clickEvent);
        });

        this.replaceComponent(notifButton, notif);
        notifButton = notif;
    }

}
