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

import com.pjkurs.domain.ArchiveCourse;
import com.pjkurs.domain.Course;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.pjkurs.vaadin.views.system.MyModel;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tmejs
 */
public class ArchiveCoursePanel<T extends MyModel> extends MyContainer<T> {

    private final ArchiveCourse course;

    public ArchiveCoursePanel(ArchiveCourse course, T model) {
        super(false, model);
        this.course = course;
        //Zbudoawnie widoku
        setContent(buildView());
    }

    @Override
    public Component buildView() {
        return buildView(course);
    }

    private Component buildView(ArchiveCourse course) {
        this.setWidth("100%");
        VerticalLayout mainLayout = new VerticalLayout();

        mainLayout.setWidth(100.0f, Unit.PERCENTAGE);
        mainLayout.setHeight(100.0f, Unit.PERCENTAGE);

        Label courseName = new Label(course.name);
        courseName.setSizeFull();
//        courseName.setWidth(100.0f, Unit.PERCENTAGE);
        mainLayout.addComponent(courseName);

        TextArea desc = new TextArea(Words.TXT_COURSE_DESCRIPTION);
        desc.setValue(course.description);
        desc.setEnabled(false);
        desc.setWidth("100%");
        desc.setWordWrap(true);
        mainLayout.addComponent(desc);
        mainLayout.setExpandRatio(desc, 1);

        Button details = new Button(Words.TXT_COURSE_DETAILS, (event) -> {
            Logger.getGlobal().log(Level.SEVERE, "TXT_COURSE_DETAILS:");
            ((MainViewModel) getModel()).detailCourseButtonClickd(event, course.id);

        });

        mainLayout.addComponent(details);
        return mainLayout;
    }

}
