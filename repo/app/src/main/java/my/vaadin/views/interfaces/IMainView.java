package my.vaadin.views.interfaces;

import com.vaadin.ui.Button;
import org.w3c.dom.html.HTMLButtonElement;

/**
 * Created by Tmejs on 04.01.2018.
 */
public interface IMainView {

    interface MainViewEvents {

        void newButtonClicked(Button.ClickEvent event);

        public void setLoginStatus();

        public void logoutButtonClick(Button.ClickEvent event);
        
        public void loginButtonClick(Button.ClickEvent event);

    }

    void setEvents(MainViewEvents events);
}