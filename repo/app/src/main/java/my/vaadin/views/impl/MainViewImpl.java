package my.vaadin.views.impl;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;

import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.util.logging.Level;
import java.util.logging.Logger;
import my.vaadin.Words;

import my.vaadin.views.interfaces.IMainView;

/**
 * Created by Tmejs on 04.01.2018.
 */
public class MainViewImpl extends CustomComponent
        implements IMainView {

    IMainView.MainViewEvents myOwnEvents = null;
    Component logedStatusWindow;
    Layout topMenuComponent;
    private VerticalLayout menuLayout;

    @Override
    public void setEvents(MainViewEvents events) {
        this.myOwnEvents = events;
    }

    public VaadinSession getVaadinSession() {
        return VaadinSession.getCurrent();
    }

    public MainViewImpl() {
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(generateTopPanel());

        layout.addComponent(generateMenu());

        setCompositionRoot(layout);
    }

    private Boolean getLoginStatus() {
        if (VaadinSession.getCurrent().getAttribute(Words.SESSION_LOGIN_NAME) != null) {
            return (Boolean) VaadinSession.getCurrent().getAttribute(Words.SESSION_LOGIN_NAME);
        } else {
            return false;
        }
    }

    private Component generateLoginWindow() {
        Component comp = null;
        //W przypadku zalogowania
        if (getLoginStatus()) {
            Logger.getGlobal().log(Level.ALL, "login true");
            comp = generateLoggedLoginComponent();

        } else {
            Logger.getGlobal().log(Level.ALL, "login false");
            comp = generateLoggedOutLoginComponent();
        }

        //comp style
        comp.setWidth("40%");
        return comp;

    }

    public final Component generateTopPanel() {
        //Główny komponent
        HorizontalLayout horizontalLayout = new HorizontalLayout();

        //Rozmiary komponentu
        horizontalLayout.setWidth("100%");
        horizontalLayout.setHeight("20%");
//        Budowanie panelu
//TODO podmienić na zdjęcie
        TextArea area = new TextArea("Tutaj będzie logo aplikacji");
        area.setWidth("30%");
        horizontalLayout.addComponent(area);

        //Nazwa aplikacji
        //TODO podmienić na zdjęcie
        TextField appNameArea = new TextField(Words.TXT_APP_NAME);
        area.setWidth("30%");
        horizontalLayout.addComponent(appNameArea);

        //Okno logowania
        logedStatusWindow = generateLoginWindow();
        horizontalLayout.addComponent(logedStatusWindow);

        topMenuComponent = horizontalLayout;
        return horizontalLayout;
    }

    private Component generateLoggedLoginComponent() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setWidth("100%");
        /*
        
        *********************************************
        *  Zalogowany jako:
        *  Imie    Nazwisko
        ***********************************************
         */
        VerticalLayout logedDataLayout = new VerticalLayout();

        TextField logedAs = new TextField();
        logedAs.setPlaceholder(Words.TXT_LOGGED_ASS);
        logedAs.setReadOnly(true);
        logedDataLayout.addComponent(logedAs);

        VerticalLayout logedDataNameLayout = new VerticalLayout();

        TextField logedName = new TextField();
        logedName.setReadOnly(true);
        logedName.setPlaceholder((String) VaadinSession.getCurrent().getAttribute(Words.SESSION_LOGGED_LOGIN));
        logedName.setCaption((String) VaadinSession.getCurrent().getAttribute(Words.TXT_NAME));
        logedDataNameLayout.addComponent(logedName);

        TextField logedEmail = new TextField();
        logedEmail.setReadOnly(true);
        logedEmail.setPlaceholder((String) VaadinSession.getCurrent().getAttribute(Words.SESSION_LOGGED_EMAIL));
        logedEmail.setCaption(Words.TXT_EMAIL);
        logedDataNameLayout.addComponent(logedEmail);

        logedDataLayout.addComponent(logedDataNameLayout);

        verticalLayout.addComponent(logedDataLayout);
        /*
        ***********************************************
        *         
        *  Moje dane.
        *                       (Wyloguj) 
         */

        VerticalLayout myDataLayout = new VerticalLayout();

        Button myDataButton = new Button(Words.TXT_MY_DATA);

        myDataLayout.addComponent(myDataButton);

        Button logoutButton = new Button(Words.TXT_LOGOUT);
        logoutButton.setStyleName(ValoTheme.BUTTON_DANGER);
        logoutButton.addClickListener((event) -> {
            myOwnEvents.logoutButtonClick(event);
        });

        myDataLayout.addComponent(logoutButton);

        verticalLayout.addComponent(myDataLayout);

        return verticalLayout;
    }

    private Component generateLoggedOutLoginComponent() {

        HorizontalLayout retLayout = new HorizontalLayout();

        Button loginButton = new Button(Words.TXT_LOGIN);
//        loginButton.setTheme("valo");
        loginButton.addClickListener((event) -> {
            myOwnEvents.loginButtonClick(event);
        });

        retLayout.addComponent(loginButton);

        return retLayout;
    }

    
    
    
    public void refreshLoginComponent() {
        this.topMenuComponent.removeComponent(logedStatusWindow);
        this.logedStatusWindow = generateLoginWindow();
        this.topMenuComponent.addComponent(logedStatusWindow);
    }

    private Component generateMenu() {
        VerticalLayout menuLayout = new VerticalLayout();
        menuLayout.setWidth("100%");

        generateMenuPanel(menuLayout);

        this.menuLayout=menuLayout;
        return menuLayout;
    }

    private void generateMenuPanel(VerticalLayout menuLayout) {
        //Generowanie menu jeśli zalogowany
        if (getLoginStatus()) {
            generateMenuPanelIfLogged(menuLayout);
        }
        else {
            generateMenuPanelIfLoggedOut(menuLayout);
        }
    }

    private void generateMenuPanelIfLogged(VerticalLayout menuLayout) {
        Button loggedButton = new Button("Buttonik bo zalogowany");
        
        menuLayout.addComponent(loggedButton);
    }

    private void generateMenuPanelIfLoggedOut(VerticalLayout menuLayout) {
        Button loggedButton = new Button("NIEzalogowany");
        
        menuLayout.addComponent(loggedButton);
    }

}
