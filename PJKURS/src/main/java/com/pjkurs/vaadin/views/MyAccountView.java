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
import com.pjkurs.vaadin.ui.containers.client.RegisterPanel;
import com.pjkurs.vaadin.views.models.MyAccountViewModel;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

/**
 *
 * @author Tmejs
 */
@Theme("pjtheme")
public class MyAccountView extends MyContainer<MyAccountViewModel> implements View, InterfacePJKURSView {

    public MyAccountView(MyAccountViewModel model) {
        super(model, true);
    }

    @Override
    public Component buildView() {
        this.setStyleName("horrizontaly-full-view");
        return new RegisterPanel(getModel());
    }

    @Override
    public Component generateTopPanel() {
        return new TextField(this.getClass().toString() + " generateTopPanel()");
    }

    @Override
    public Component generateMenu() {
        return new TextField(this.getClass().toString() + " generateMenu()");
    }

    @Override
    public Component generateMainAppPanel() {
        return new TextField(this.getClass().toString() + " generateMainAppPanel()");
    }

}
