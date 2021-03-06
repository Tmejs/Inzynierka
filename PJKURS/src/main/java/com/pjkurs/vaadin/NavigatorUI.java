/*
 * Copyright (C) 2018 Tmejs
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.pjkurs.vaadin;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.pjkurs.db.DbDataProvider;
import com.pjkurs.db.InterfacePjkursDataProvider;
import com.pjkurs.domain.Appusers;
import com.pjkurs.domain.DeaneryUser;
import com.pjkurs.usables.Words;
import com.pjkurs.utils.MailSenderUtil;
import com.pjkurs.utils.Params;
import com.pjkurs.vaadin.views.AdminView;
import com.pjkurs.vaadin.views.MainView;
import com.pjkurs.vaadin.views.RegisterView;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.pjkurs.vaadin.views.models.RegisterViewModel;
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

    private final static String PARAM_DATA_PROVIDER = "PARAM_DATA_PROVIDER";
    private final static String PARAM_MAIL_SENDER = "PARAM_MAIL_SENDER";

    public static Params PARAMS = new Params();

    public static InterfacePjkursDataProvider getDBProvider() {
        InterfacePjkursDataProvider dbDataProvider = (InterfacePjkursDataProvider) VaadinSession
                .getCurrent().getAttribute(PARAM_DATA_PROVIDER);
        if (dbDataProvider == null) {
            try {
                dbDataProvider = new DbDataProvider(Words.DB_NAME, Words.LOGIN, Words.HASLO);
                VaadinSession.getCurrent().setAttribute(PARAM_DATA_PROVIDER, dbDataProvider);

            } catch (Exception exception) {
                Logger.getLogger("NavigatorUI").log(Level.SEVERE, "Stirng", exception);
            }
        }
        return dbDataProvider;
    }

    public static MailSenderUtil getMailSender() {
        MailSenderUtil mailSender = (MailSenderUtil) VaadinSession
                .getCurrent().getAttribute(PARAM_MAIL_SENDER);
        if (mailSender == null) {
            mailSender = new MailSenderUtil(PARAMS.EMAIL_LOGIN,
                    PARAMS.EMAIL_PASSWORD,
                    PARAMS.EMAIL_SMTP, PARAMS.EMAIL_PORT);
        }
        return mailSender;
    }

    /**
     * To jest jakliś nasz śmieszny opis
     *
     * @return
     */
    public static Boolean getLoginStatus() {
        return VaadinSession.getCurrent().getAttribute(Words.SESSION_LOGIN_NAME) != null;

    }

    public static Appusers getLoggedUser() {
        return (Appusers) VaadinSession.getCurrent().getAttribute(Words.SESSION_LOGIN_NAME);
    }

    public static DeaneryUser getLoggedAdmin() {
        return (DeaneryUser) VaadinSession.getCurrent().getAttribute(Words.SESSION_ADMIN);
    }

    public static void setLogedAdmin(DeaneryUser admin) {
        VaadinSession.getCurrent().setAttribute(Words.SESSION_ADMIN, admin);
    }

    public static Boolean getAdminLoginStatus() {
        return VaadinSession.getCurrent().getAttribute(Words.SESSION_ADMIN) != null;
    }

    //Nawigator aplikacji
    public Navigator navigator;

    public static void setLoggeddUser(Appusers user) {
        VaadinSession.getCurrent().setAttribute(Words.SESSION_LOGIN_NAME, user);
    }

    //Widoki w aplikacji
    public enum View {
        MAINVIEW(""),
        REGISTER_VIEW("register"),
        ADMIN_VIEW("admin");

        private String name;

        private View(String val) {
            this.name = val;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }

    }

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setSpacing(true);
        setContent(layout);
        Navigator.ComponentContainerViewDisplay viewDisplay =
                new Navigator.ComponentContainerViewDisplay(
                        layout);
        navigator = new Navigator(UI.getCurrent(), viewDisplay);

        navigator.addView(View.MAINVIEW.getName(), new MainView(new MainViewModel(this)));
        navigator.addView(View.REGISTER_VIEW.getName(),
                new RegisterView(new RegisterViewModel(this)));
        navigator.addView(View.ADMIN_VIEW.getName(), new AdminView(new AdminViewModel(this)));
    }

    @WebServlet(urlPatterns = "/*", name = "NavigatorUI", asyncSupported = true)
    @VaadinServletConfiguration(ui = NavigatorUI.class, productionMode = false)
    public static class MainViewServlet extends VaadinServlet
            implements SessionInitListener, SessionDestroyListener {

        public static com.pjkurs.utils.Logger LOGGER;

        @Override
        protected void servletInitialized() throws ServletException {
            super.servletInitialized();
            getService().addSessionInitListener(this);
            getService().addSessionDestroyListener(this);

            if (LOGGER != null) {
                try {
                    LOGGER = new com.pjkurs.utils.Logger("Log.txt", Boolean.TRUE, Boolean.TRUE);
                } catch (Exception e) {
                    Logger.getLogger(MainViewServlet.class.toString())
                            .log(Level.SEVERE, e.getMessage());
                }
            }
        }

        @Override
        public void sessionInit(SessionInitEvent event) throws ServiceException {

        }

        @Override
        public void sessionDestroy(SessionDestroyEvent event) {

        }
    }

}
