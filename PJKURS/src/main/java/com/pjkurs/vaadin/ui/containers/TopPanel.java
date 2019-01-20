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

import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.ui.containers.menu.LoginPanel;
import com.pjkurs.vaadin.ui.containers.menu.LoggedInPanel;
import com.pjkurs.vaadin.views.AdminView;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.pjkurs.vaadin.views.models.RegisterViewModel;
import com.pjkurs.vaadin.views.system.MyModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.annotations.Theme;
import com.vaadin.event.MouseEvents;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.io.File;

/**
 * @author Tmejs
 */
@Theme("pjtheme")
public class TopPanel<T extends MyModel> extends MyContainer<T> {

    public TopPanel(T model) {
        super(model);

    }

    public Resource getLogoResource() {
        // Find the application directory
        String basepath = VaadinService.getCurrent()
                .getBaseDirectory().getAbsolutePath();

        // Image as a file resource
        FileResource resource = new FileResource(new File(basepath
                + Words.IMAGE_FOLDER_PATH + "/" + Words.PJURS_LOGO_IMAGE_NAME));

        return resource;
    }

    @Override
    public Component buildView() {
        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.setSizeFull();
        mainLayout.setHeight("20%");
        if (getModel() instanceof MainViewModel) {
            MainViewModel currentModel = (MainViewModel) getModel();
            //LOGO aplikacji
            Image logoImage = new Image();
            //            logoImage.setAlternateText(Words.TXT_LOGO_NAME);
            logoImage.setStyleName("logo-image");
            logoImage.setIcon(getLogoResource());

            mainLayout.addComponent(logoImage);

            //Panel logowania
            Component loginPanel;
            if (NavigatorUI.getLoginStatus()) {
                loginPanel = new LoggedInPanel(currentModel);
            } else {
                loginPanel = new LoginPanel(currentModel);
            }
            mainLayout.addComponent(loginPanel);
        } else if (getModel() instanceof RegisterViewModel) {
            mainLayout.addStyleName("top-panel");
            //LOGO aplikacji
            Image logoImage = new Image();
            logoImage.addClickListener(new MouseEvents.ClickListener() {
                @Override
                public void click(MouseEvents.ClickEvent event) {
                    getUI().getNavigator().navigateTo(NavigatorUI.View.MAINVIEW.getName());
                }
            });

            logoImage.setStyleName("logo-image");
            logoImage.setIcon(getLogoResource());

            mainLayout.addComponent(logoImage);

            //Nazwa aplikcji
            Label textField = new Label(Words.TXT_APP_NAME);
            textField.addStyleName("app-name-label");
            mainLayout.addComponent(textField);

        } else if (getModel() instanceof AdminViewModel) {
            mainLayout.addStyleName("top-panel");

            //LOGO aplikacji
            Image logoImage = new Image();
            logoImage.addClickListener((event) -> {
                getUI().getNavigator().navigateTo(NavigatorUI.View.MAINVIEW.getName());
            });

            logoImage.setStyleName("logo-image");
            logoImage.setIcon(getLogoResource());
            mainLayout.addComponent(logoImage);

            //Informacja że w oknie admina
            Label textField = new Label(Words.TXT_ADMINISTRATION);
            textField.addStyleName("app-name-label");
            mainLayout.addComponent(textField);

            if (NavigatorUI.getAdminLoginStatus()) {
                VerticalLayout verLay= new VerticalLayout();
                Label label = new Label();
                label.setCaption(Words.TXT_LOGED_AS);
                label.setValue(NavigatorUI.getLoggedAdmin().getEmail());
                Button exitButton = new Button(Words.TXT_LOGOUT);
                exitButton.addClickListener((event) -> {
                    NavigatorUI.setLogedAdmin(null);
                    ((AdminView) getUI().getNavigator().getCurrentView()).refreshView();
                });
                verLay.addComponent(label);
                verLay.addComponent(exitButton);
                mainLayout.addComponent(verLay);
            }
        }
        return mainLayout;
    }

}
