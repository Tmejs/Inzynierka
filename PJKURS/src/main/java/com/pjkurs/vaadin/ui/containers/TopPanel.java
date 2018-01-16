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
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.pjkurs.vaadin.views.models.RegisterViewModel;
import com.pjkurs.vaadin.views.system.MyModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.annotations.Theme;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import java.io.File;

/**
 *
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
    public void buildView() {
        this.addStyleName("top-main-panel");
        HorizontalLayout mainLayout = new HorizontalLayout();

        //
        if (getModel() instanceof MainViewModel) {
            MainViewModel currentModel = (MainViewModel) getModel();
            mainLayout.addStyleName("top-panel");
            //LOGO aplikacji
            Image logoImage = new Image();
            logoImage.setAlternateText(Words.TXT_LOGO_NAME);
            logoImage.setStyleName("logo-image");
            logoImage.setIcon(getLogoResource());

            mainLayout.addComponent(logoImage);

            //Nazwa aplikcji
            Label textField = new Label(Words.TXT_APP_NAME);
            textField.addStyleName("app-name-label");
            mainLayout.addComponent(textField);

            //Panel logowania
            Component loginPanel;
            if (NavigatorUI.getLoginStatus()) {
                loginPanel = new LoggedInPanel(currentModel);
            } else {
                loginPanel = new LoginPanel(currentModel);
            }
            loginPanel.addStyleName("login-component");
            mainLayout.addComponent(loginPanel);
        } else if (getModel() instanceof RegisterViewModel) {
            RegisterViewModel currentModel = (RegisterViewModel) getModel();
            mainLayout.addStyleName("top-panel");
            //LOGO aplikacji
            Image logoImage = new Image();
            logoImage.setStyleName("logo-image");
            logoImage.setIcon(getLogoResource());

            mainLayout.addComponent(logoImage);

            //Nazwa aplikcji
            Label textField = new Label(Words.TXT_APP_NAME);
            textField.addStyleName("app-name-label");
            mainLayout.addComponent(textField);

        }

        this.addComponent(mainLayout);
    }

}
