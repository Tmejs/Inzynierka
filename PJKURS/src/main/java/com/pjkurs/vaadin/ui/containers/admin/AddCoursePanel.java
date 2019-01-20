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
package com.pjkurs.vaadin.ui.containers.admin;

import com.mysql.fabric.RangeShardMapping;
import com.pjkurs.domain.Course;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.annotations.Theme;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;
import org.w3c.dom.ranges.Range;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author Tmejs
 */
@Theme("pjtheme")
public class AddCoursePanel<T extends AdminViewModel> extends MyContainer<T> {

    public AddCoursePanel(T model) {
        super(model);
    }

    @Override
    public Component buildView() {
        VerticalLayout mainLayout = new VerticalLayout();
        this.addStyleName("login-component");

        mainLayout.setSizeUndefined();

        mainLayout.addComponent(new Label(Words.TXT_INSERT_NEW_COURSE_DATA));

        TextField nameTextField = new TextField(Words.TXT_COURSE_NAME);
        nameTextField.setSizeUndefined();

        mainLayout.addComponent(nameTextField);

        //Opis
        TextField descriptionTextField = new TextField(Words.TXT_DESCRIPTION);

        mainLayout.addComponent(descriptionTextField);

        //Guzik logowania
        Button loginButton = new Button(Words.TXT_ADD_NEW_COURSE, ((event) -> {
            Course newCourse = new Course();
            newCourse.name = nameTextField.getValue();
            newCourse.description = descriptionTextField.getValue();
            getModel().addNewCouurse(newCourse);
            getModel().menuCoursesOverviewClicked();
            Notification.show(Words.TXT_COURSE_ADDED, Notification.Type.TRAY_NOTIFICATION);
        }));

        mainLayout.addComponent(loginButton);

        return mainLayout;
    }

}
