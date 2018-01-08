/*
 * AWS PROJECT (c) 2017 by tmejs (mateusz.rzad@gmail.com)
 * --------------------------------------------------------
 * Niniejszy program chroniony jest prawem autorskim. Jego rozpowszechnianie bez wyraźnej zgody autora
 * jest zabronione. Jakakolwiek ingerencja w oprogramowanie bez upoważnienia autora,
 * w tym w szczególności jego modyfikacja lub nieuprawnione kopiowanie jest sprzeczne z prawem.
 * Wersja opracowana dla Domax Sp. z o.o. z siedzibą w Łężycach
 */
package my.vaadin.presenters;

import com.vaadin.ui.Button;
import my.vaadin.views.impl.RegisterViewImpl;
import my.vaadin.views.models.RegisterViewModel;
import my.vaadin.views.interfaces.IRegisterView;

/**
 *
 * @author Tmejs
 */
public class RegisterViewPresenter 
    implements IRegisterView.RegisterViewEvents {

            RegisterViewModel model;
            RegisterViewImpl view;

    public RegisterViewPresenter(RegisterViewModel model, RegisterViewImpl view) {
        this.model = model;
        this.view = view;

        view.setEvents(this);
    }

    @Override
    public void backToMainButtonClick(Button.ClickEvent event) {
        
    }
    
    
    
    
    
}
