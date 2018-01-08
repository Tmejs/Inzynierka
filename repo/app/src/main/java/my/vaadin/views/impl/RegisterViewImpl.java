/*
 * AWS PROJECT (c) 2017 by tmejs (mateusz.rzad@gmail.com)
 * --------------------------------------------------------
 * Niniejszy program chroniony jest prawem autorskim. Jego rozpowszechnianie bez wyraźnej zgody autora
 * jest zabronione. Jakakolwiek ingerencja w oprogramowanie bez upoważnienia autora,
 * w tym w szczególności jego modyfikacja lub nieuprawnione kopiowanie jest sprzeczne z prawem.
 * Wersja opracowana dla Domax Sp. z o.o. z siedzibą w Łężycach
 */
package my.vaadin.views.impl;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import my.vaadin.views.interfaces.IRegisterView;

/**
 *
 * @author Tmejs
 */
public class RegisterViewImpl extends CustomComponent
        implements IRegisterView {

    IRegisterView.RegisterViewEvents events;
    
    public RegisterViewImpl() {
        VerticalLayout layout = new VerticalLayout();
       
        layout.addComponent(new TextField("Rejestrowanie"));
        
        layout.addComponent(new Button("Główna", (event) -> {
            events.backToMainButtonClick(event);
        }));
        

        setCompositionRoot(layout);
    }
    
    @Override
    public void setEvents(RegisterViewEvents events) {
        this.events=events;
    }
    
    
    
}
