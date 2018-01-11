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
import com.pjkurs.vaadin.views.system.MyModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

/**
 *
 * @author Tmejs
 */
public class TopPanel<T extends MyModel> extends MyContainer<T> {

    public TopPanel(T model) {
        super(model);

    }

    @Override
    public void buildView() {
        HorizontalLayout mainLayout = new HorizontalLayout();
        //
        if (getModel() instanceof MainViewModel) {
            MainViewModel currentModel = (MainViewModel) getModel();

            //LOGO aplikacji
            Image logoImage = new Image();
            logoImage.setAlternateText(Words.TXT_LOGO_NAME);
            logoImage.setIcon(currentModel.getLogoResource());

            mainLayout.addComponent(logoImage);

            //Nazwa aplikcji
            Label textField = new Label(Words.TXT_APP_NAME);
            mainLayout.addComponent(textField);

            //Panel logowania
            if (NavigatorUI.getLoginStatus()) {
                mainLayout.addComponent(new LoggedInPanel(currentModel));
            } else {
                mainLayout.addComponent(new LoginPanel(currentModel));
            }
        }
        this.addComponent(mainLayout);
    }

}
