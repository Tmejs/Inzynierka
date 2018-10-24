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
package com.pjkurs.vaadin.ui.containers.client;

import com.pjkurs.domain.Course;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.pjkurs.vaadin.views.system.MyModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tmejs
 */
public class DetailedCoursePanel<T extends MyModel> extends MyContainer<T> {

    Course course;

    public DetailedCoursePanel(T model, Course course) {
        super(model);
        this.course = course;
        this.setContent(buildView());
    }

    @Override
    public Component buildView() {
        VerticalLayout layout = new VerticalLayout();
//        layout.setSizeUndefined();
        if (course != null) {
            TextArea courseName = new TextArea(Words.TXT_COURSE_NAME, this.course.name);
            courseName.setReadOnly(true);
            layout.addComponent(courseName);

            TextArea courseDesc = new TextArea(Words.TXT_COURSE_DESCRIPTION,
                    this.course.description);
            courseDesc.setReadOnly(true);
            layout.addComponent(courseDesc);

            layout.addComponent(new Label(Words.TXT_COURSE_PARTICIPANTS));
            layout.addComponent(new Label(course.paricipants.toString()));

            //Sprawdzenie czy już zapisany
            if (NavigatorUI.getLoggedUser() != null) {
                if (!NavigatorUI.getDBProvider()
                        .isUserSignedToCourse(NavigatorUI.getLoggedUser().email, course.id)) {
                    layout.addComponent(new Button(Words.TXT_SIGN_TO_COURSE, (event) -> {
                        Logger.getGlobal().log(Level.SEVERE, "TXT_SIGN_TO_COURSE:");

                        if (NavigatorUI.getDBProvider()
                                .addClientToCourse(NavigatorUI.getLoggedUser().email, course)) {
                            Notification.show("Poprawnie zapisano do kursu");
                            ((MainViewModel) getModel()).getView()
                                    .setDetailedCourseAsMainPanel(course.id);
                        } else {
                            Notification.show("Nie można zapisać do kursu");
                        }
                    }));
                } else {
                    Button undoCoursSign = new Button(Words.TXT_UNDO_SIGN_TO_COURS);

                    undoCoursSign.addClickListener(event -> {
                        NavigatorUI.getDBProvider()
                                .deleteCientFromCourse(NavigatorUI.getLoggedUser(), course);
                        ((MainViewModel) getModel()).myCoursesButtonClicked(null);
                        Notification.show(Words.TXT_CORRECTLY_UNSGNED);
                    });
                    layout.addComponent(undoCoursSign);
                }
            } else {
                layout.addComponent(new Label("Zaloguj aby zapisać się do kursu"));
            }

        }
        return layout;
    }
}
