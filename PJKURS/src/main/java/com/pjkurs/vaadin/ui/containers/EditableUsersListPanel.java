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
package com.pjkurs.vaadin.ui.containers;

import com.pjkurs.domain.Appusers;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Tmejs
 */
public class EditableUsersListPanel<T extends AdminViewModel> extends MyContainer<T> {

    public EditableUsersListPanel(T model) {
        super(model);
    }

    @Override
    public Component buildView() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeUndefined();
        layout.addComponent(new HorizontalLayout(new Label("email"), new Label("haslo")));
        for (Appusers user : NavigatorUI.getDBProvider().getUsers()) {
            HorizontalLayout lt = new HorizontalLayout();
            lt.setSizeUndefined();
            lt.addComponent(new Label(user.email));
            lt.addComponent(new Label(user.haslo));
            try {
                lt.addComponent(new Label(user.data_dodania.toString()));
            } catch (Exception e) {
            }
            layout.addComponent(lt);
        }
        return layout;
    }
    
    

    
    
    
}
