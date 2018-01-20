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
package com.pjkurs.vaadin.ui.menu;

import com.pjkurs.vaadin.views.system.MyModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.views.models.MainViewModel;

/**
 *
 * @author Tmejs
 */
public class MainMenuPanel<T extends MyModel> extends MyContainer<T> {

    public MainMenuPanel(T model) {
        super(model);
    }

    @Override
    public Component buildView() {
        this.setSizeFull();
        HorizontalLayout mainLayout = new HorizontalLayout();

        Button coursesButton = new Button(Words.TXT_BUTTON_COURSES);
        coursesButton.addClickListener((event) -> {
            if (getModel() instanceof MainViewModel) {
                ((MainViewModel) getModel()).showAllCoursesButtonClicked(event);
            }

        });

        mainLayout.addComponent(coursesButton);

        return mainLayout;
    }

}
