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

import com.pjkurs.vaadin.views.system.MyContainer;
import com.pjkurs.vaadin.views.models.RegisterViewModel;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

/**
 *
 * @author Tmejs
 */
public class RegisterView extends MyContainer<RegisterViewModel> implements View, InterfacePJKURSView {

    public RegisterView(RegisterViewModel model) {
        super(model);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Showing view: Register!");
    }

    //Zbudowanie Widoku dla panelu rejestrowania
    @Override
    public void buildView() {
        this.addComponent(generateTopPanel());

        this.addComponent(generateMenu());

        this.addComponent(generateMainAppPanel());
    }

    @Override
    public Component generateTopPanel() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return new TextField(this.getClass().toString() + " generateTopPanel()");
    }

    @Override
    public Component generateMenu() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return new TextField(this.getClass().toString() + " generateMenu()");
    }

    @Override
    public Component generateMainAppPanel() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return new TextField(this.getClass().toString() + " generateMainAppPanel()");
    }

}
