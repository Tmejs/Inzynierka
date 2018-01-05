package my.vaadin.views;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionDestroyEvent;
import com.vaadin.server.SessionDestroyListener;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import javax.servlet.ServletException;
import my.vaadin.presenters.MainViewPresenter;
import my.vaadin.views.impl.MainViewImpl;
import my.vaadin.views.models.MainViewModel;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of an HTML page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
public class MainView extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        MainViewModel model = new MainViewModel();
        MainViewImpl view = new MainViewImpl();
        MainViewPresenter presenter = new MainViewPresenter(model, view);
        setContent(view);
    }

    @WebServlet(urlPatterns = "/*", name = "MainView", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainView.class, productionMode = false)
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
