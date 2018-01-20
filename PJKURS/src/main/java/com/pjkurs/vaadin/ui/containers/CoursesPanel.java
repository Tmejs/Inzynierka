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

import com.pjkurs.domain.Course;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.system.MyModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import java.util.List;

/**
 *
 * @author Tmejs
 */
public class CoursesPanel<T extends MyModel> extends MyContainer<T> {

    public CoursesPanel(T model) {
        super(model);
    }

    @Override
    public Component buildView() {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeUndefined();

        List<Course> courses = NavigatorUI.getDBProvider().getAvalibleCourses();

        //Sprawdzenie czy są dostępne kursy
        if (courses == null || courses.isEmpty()) {
            mainLayout.addComponent(new Label(Words.TXT_NO_AVALIBLE_COURSES));
            return mainLayout;
        }

        //Utworzenie komponentów
        Integer counter = 0;
        HorizontalLayout horizontalPanel = new HorizontalLayout();
        horizontalPanel.setWidth(1.0f, Unit.PERCENTAGE);

        courses.addAll(courses);
        courses.addAll(courses);
        for (Course course : courses) {
            CoursePanel coursePanel = new CoursePanel(course, getModel());
            if (counter > 3) {
                counter = 0;
                mainLayout.addComponent(horizontalPanel);
                horizontalPanel = new HorizontalLayout();
//                horizontalPanel.setSizeUndefined();
                horizontalPanel.setWidth(1.0f, Unit.PERCENTAGE);
            }
            horizontalPanel.addComponent(coursePanel);

        }

        mainLayout.addComponent(horizontalPanel);

        return mainLayout;

    }

}
