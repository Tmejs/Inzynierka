package my.vaadin.presenters;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import java.util.logging.Level;
import java.util.logging.Logger;
import my.vaadin.Words;
import my.vaadin.views.impl.MainViewImpl;
import my.vaadin.views.interfaces.IMainView;
import my.vaadin.views.models.MainViewModel;

/**
 * Created by Tmejs on 04.01.2018.
 */
public class MainViewPresenter
        implements IMainView.MainViewEvents {

    MainViewModel model;
    MainViewImpl view;

    public MainViewPresenter(MainViewModel model,
            MainViewImpl view) {
        this.model = model;
        this.view = view;

        view.setEvents(this);
    }

    @Override
    public void newButtonClicked(Button.ClickEvent event) {
        Notification.show(event.getButton().getCaption());
    }

    @Override
    public void setLoginStatus() {
        VaadinSession session = view.getVaadinSession();

        if (session.getAttribute(Words.SESSION_LOGIN_NAME) != null) {
//            Logger.getLogger(MainViewPresenter.class.toString()).log(Level.ALL.ALL,"ustawiam na " + session.getAttribute(Words.SESSION_LOGIN_NAME).toString());
            session.setAttribute(Words.SESSION_LOGIN_NAME, !(Boolean) session.getAttribute(Words.SESSION_LOGIN_NAME));

        } else {
//            Logger.getLogger(MainViewPresenter.class.toString()).log(Level.ALL.ALL,"ustawiam na " + Boolean.TRUE.toString());
            session.setAttribute(Words.SESSION_LOGIN_NAME, Boolean.TRUE);
        }
    }

    @Override
    public void logoutButtonClick(Button.ClickEvent event) {
        VaadinSession session = view.getVaadinSession();
        session.setAttribute(Words.SESSION_LOGIN_NAME, Boolean.FALSE);
        view.refreshLoginComponent();
        //TODO clear params data
    }
    
    
    
    @Override
    public void loginButtonClick(Button.ClickEvent event) {
        VaadinSession session = view.getVaadinSession();
        session.setAttribute(Words.SESSION_LOGIN_NAME, Boolean.TRUE);
        session.setAttribute(Words.SESSION_LOGGED_EMAIL,"Mateusz.rzad@gmail.com");
        session.setAttribute(Words.SESSION_LOGGED_LOGIN, "Mateusz_Logins");
        view.refreshLoginComponent();
        //TODO clear params data
    }
    
    
    

}
