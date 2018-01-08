/*
 * AWS PROJECT (c) 2017 by tmejs (mateusz.rzad@gmail.com)
 * --------------------------------------------------------
 * Niniejszy program chroniony jest prawem autorskim. Jego rozpowszechnianie bez wyraźnej zgody autora
 * jest zabronione. Jakakolwiek ingerencja w oprogramowanie bez upoważnienia autora,
 * w tym w szczególności jego modyfikacja lub nieuprawnione kopiowanie jest sprzeczne z prawem.
 * Wersja opracowana dla Domax Sp. z o.o. z siedzibą w Łężycach
 */
package my.vaadin.views.interfaces;

import com.vaadin.ui.Button;

/**
 *
 * @author Tmejs
 */
public interface IRegisterView {
    
    public interface RegisterViewEvents {

        public void backToMainButtonClick(Button.ClickEvent event);
        
    }
    
    void setEvents(RegisterViewEvents events);
}
