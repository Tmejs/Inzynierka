package com.pjkurs;

import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.views.MainView;
import com.pjkurs.vaadin.views.RegisterView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionDestroyEvent;
import com.vaadin.server.SessionDestroyListener;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of an HTML page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("pjtheme")
@SuppressWarnings("serial")
public class NavigatorUI extends UI {

    public static Boolean getLoginStatus() {
        if (VaadinSession.getCurrent().getAttribute(Words.SESSION_LOGIN_NAME) != null) {
            return (Boolean) VaadinSession.getCurrent().getAttribute(Words.SESSION_LOGIN_NAME);
        } else {
            return false;
        }
    }
    
    //Nawigator aplikacji
    public Navigator navigator;

    //Widoki w aplikacji
    public enum View {
        MAINVIEW(""),
        REGISTER_VIEW("register");

        private String name;

        private View(String val) {
            this.name = val;
        }

        public String getName() {
            return name;
        }
    }

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
        Navigator.ComponentContainerViewDisplay viewDisplay = new Navigator.ComponentContainerViewDisplay(layout);
        navigator = new Navigator(UI.getCurrent(), viewDisplay);

        navigator.addView(View.MAINVIEW.getName(), new MainView());
        navigator.addView(View.REGISTER_VIEW.getName(), new RegisterView());
    }

    @WebServlet(urlPatterns = "/*", name = "NavigatorUI", asyncSupported = true)
    @VaadinServletConfiguration(ui = NavigatorUI.class, productionMode = false)
    public static class MainViewServlet extends VaadinServlet
            implements SessionInitListener, SessionDestroyListener {

        @Override
        protected void servletInitialized() throws ServletException {
            super.servletInitialized();
            getService().addSessionInitListener(this);
            getService().addSessionDestroyListener(this);
        }

        @Override
        public void sessionInit(SessionInitEvent event) throws ServiceException {

        }

        @Override
        public void sessionDestroy(SessionDestroyEvent event) {

        }
    }

}
