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
import my.vaadin.presenters.RegisterViewPresenter;
import my.vaadin.views.impl.RegisterViewImpl;
import my.vaadin.views.models.RegisterViewModel;

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
public class RegisterView extends VerticalLayout implements View {

    public RegisterView() {
        setSizeFull();
        
//        addComponent(button);
//        setComponentAlignment(button, Alignment.MIDDLE_CENTER);
    }

//    @Override
//    protected void init(VaadinRequest vaadinRequest) {
//        RegisterViewModel model = new RegisterViewModel();
//        RegisterViewImpl view = new RegisterViewImpl();
//        RegisterViewPresenter presenter = new RegisterViewPresenter(model, view);
//        setContent(view);
//    }
//    
    
    
    
}
