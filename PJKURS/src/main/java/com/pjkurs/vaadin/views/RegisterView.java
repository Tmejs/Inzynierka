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
package com.pjkurs.vaadin.views;

import com.pjkurs.domain.Client;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.ui.containers.TopPanel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.pjkurs.vaadin.views.models.RegisterViewModel;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Tmejs
 */
@Theme("pjtheme")
public class RegisterView extends MyContainer<RegisterViewModel> implements View, InterfacePJKURSView {
    
    public RegisterView(RegisterViewModel model) {
        super(model, true);
    }

    //Zbudowanie Widoku dla panelu rejestrowania
    @Override
    public Component buildView() {
        this.setStyleName("horrizontaly-full-view");
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(false);
        layout.setWidthUndefined();
        layout.addComponent(generateTopPanel());
        
        layout.addComponent(generateMenu());
        
        layout.addComponent(generateMainAppPanel());
        return layout;
    }
    
    @Override
    public Component generateTopPanel() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return new TopPanel(getModel());
    }
    
    @Override
    public Component generateMenu() {
        Button button = new Button(Words.TXT_BACK);
        button.addClickListener((event) -> {
            getModel().backButtonClicked(event);
        });
        return button;
    }
    
    @Override
    public Component generateMainAppPanel() {
        VerticalLayout verticalLayout = new VerticalLayout();
        
        TextField email = new TextField(Words.TXT_EMAIL);
        verticalLayout.addComponent(email);
        
        TextField password = new PasswordField(Words.TXT_PASSWORD);
        verticalLayout.addComponent(password);
        
        TextField passwordConfirmation = new PasswordField(Words.TXT_PASSWORD);
        verticalLayout.addComponent(passwordConfirmation);
        
        getModel().bindLoginData(email, password, passwordConfirmation);
        
        Button confirmButton = new Button(Words.TXT_REGISTER);
        confirmButton.addClickListener((event) -> {
            getModel().registerButtonClicked(event);
        });
        
        verticalLayout.addComponent(confirmButton);
        
        return verticalLayout;
        
    }
    
}
