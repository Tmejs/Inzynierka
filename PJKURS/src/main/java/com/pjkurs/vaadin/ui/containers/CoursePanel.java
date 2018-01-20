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
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.pjkurs.vaadin.views.system.MyModel;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Tmejs
 */
class CoursePanel<T extends MyModel> extends MyContainer<T> {

    private final Course course;

    public CoursePanel(Course course, T model) {
        super(false, model);
        this.course = course;
        //Zbudoawnie widoku
        buildView();
    }

    @Override
    public Component buildView() {

        ///Nie zostawiamy dummy na wszelki wypadek
        Component comp = buildView(course);
        this.setContent(comp);

        return comp;
    }

    private Component buildView(Course course) {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSizeUndefined();
        Label courseName = new Label(course.name);

        mainLayout.addComponent(courseName);

        TextField courseDescription = new TextField(Words.TXT_COURSE_DESCRIPTION, course.description);

        mainLayout.addComponent(courseDescription);

        Button saveToCourse = new Button(Words.TXT_COURSE_ADD_TO_COURSE, (event) -> {
            ((MainViewModel) getModel()).addToCourseButtonClicked(event, course);
        });

        mainLayout.addComponent(saveToCourse);

        return mainLayout;
    }

}
