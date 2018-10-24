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

import com.pjkurs.vaadin.ui.containers.admin.AdminCoursesOverviewPanel;
import com.pjkurs.vaadin.ui.containers.TopPanel;
import com.pjkurs.vaadin.ui.menu.AdminMenuPanel;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.navigator.View;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Tmejs
 */
public class AdminView extends MyContainer<AdminViewModel> implements View, InterfacePJKURSView {

    Component topPanel;
    Component menuPanel;
    Component mainPanel;

    public AdminView(AdminViewModel model) {
        super(model, true);
    }

    public void setMainPanel(Component newMainPanel) {
        ((VerticalLayout) this.getContent()).replaceComponent(mainPanel, newMainPanel);
        mainPanel = newMainPanel;
    }

    @Override
    public Component buildView() {
        this.setSizeFull();
        VerticalLayout mainLayout = new VerticalLayout();
        topPanel = generateTopPanel();
        mainLayout.addComponent(topPanel);
        menuPanel = generateMenu();
        mainLayout.addComponent(menuPanel);
        mainPanel = generateMainAppPanel();
        mainLayout.addComponent(mainPanel);
        return mainLayout;

    }

    @Override
    public Component generateTopPanel() {
        return new TopPanel(getModel());
    }

    @Override
    public Component generateMenu() {
        return new AdminMenuPanel(getModel());
    }

    @Override
    public Component generateMainAppPanel() {
        return new AdminCoursesOverviewPanel(getModel());
    }

}
