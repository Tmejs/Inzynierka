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

import java.util.List;

import com.pjkurs.domain.Appusers;
import com.pjkurs.domain.Course;
import com.pjkurs.domain.MyCourse;
import com.pjkurs.domain.Training;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.ui.containers.client.CoursePanel;
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.pjkurs.vaadin.views.system.MyModel;
import com.vaadin.annotations.Theme;
import com.vaadin.data.HasValue;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Tmejs
 */
@Theme("pjtheme")
public class MyCoursesPanel<T extends MyModel> extends MyContainer<T> {

    public MyCoursesPanel(T model) {
        super(model);
    }

    String filter;
    Component coursesComponent;
    VerticalLayout mainViewComponent;

    private Component buildFiltersMenu() {

        HorizontalLayout mainLayout = new HorizontalLayout();

        Label filterName = new Label(Words.TXT_FIND);
        mainLayout.addComponent(filterName);

        TextField filteredText = new TextField(new HasValue.ValueChangeListener<String>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<String> event) {
                if (event.getValue().length() > 2) {
                    filter = event.getValue();

                    refreshCoursesComponent();
                } else {
                    filter = null;
                    refreshCoursesComponent();
                }
            }
        });

        mainLayout.addComponent(filteredText);

        return mainLayout;
    }

    private Component buildCourses() {

        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setWidth("100%");
        Appusers appUser = NavigatorUI.getLoggedUser();
        List<MyCourse> courses = NavigatorUI.getDBProvider().getMyCourses(appUser.getEmail());

        //Sprawdzenie czy są dostępne kursy
        if (courses == null || courses.isEmpty()) {
            mainLayout.addComponent(new Label(Words.TXT_NO_AVALIBLE_COURSES));
            return mainLayout;
        }

        //Utworzenie komponentów
        Integer counter = 0;
        HorizontalLayout horizontalPanel = new HorizontalLayout();
        horizontalPanel.setWidth("100%");

        for (Course course : courses) {
            if (!checkFilter(course)) {
                continue;
            }

            CoursePanel coursePanel = new CoursePanel(course, getModel());
            //            coursePanel.setSizeUndefined();
            if (counter >= 3) {
                counter = 0;
                mainLayout.addComponent(horizontalPanel);
                horizontalPanel = new HorizontalLayout();
                horizontalPanel.setWidth("100%");
            }
            horizontalPanel.addComponent(coursePanel);
            counter = counter + 1;
        }
        mainLayout.addComponent(horizontalPanel);
        return mainLayout;
    }

    private void refreshCoursesComponent() {
        Component newCourses = buildCourses();
        mainViewComponent.replaceComponent(coursesComponent, newCourses);
        coursesComponent = newCourses;
    }

    @Override
    public Component buildView() {
        this.setWidth("100%");

        VerticalLayout mainView = new VerticalLayout();
        mainViewComponent = mainView;

        mainView.setSizeFull();

        coursesComponent = buildCourses();
        Component trainingsPanel = createMyTrainingsPanel();
        if (trainingsPanel != null) {
            mainView.addComponentsAndExpand(trainingsPanel);
        }

        mainView.addComponent(new Label(Words.TXT_COURSES_AWAITING_START));
        mainView.addComponent(buildFiltersMenu());
        mainView.addComponentsAndExpand(coursesComponent);

        return mainView;
    }

    private Component createMyTrainingsPanel() {
        VerticalLayout verticalLayout = new VerticalLayout();

        Label ongoingTrainigns = new Label(Words.TXT_MY_TRAININGS);

        Component myTrainingsPanel = generateMyTrainingsPanel();
        if (myTrainingsPanel == null) {
            return null;
        }
        verticalLayout.addComponent(ongoingTrainigns);
        verticalLayout.addComponentsAndExpand(myTrainingsPanel);
        return verticalLayout;
    }

    private Component generateMyTrainingsPanel() {
        HorizontalLayout lay = new HorizontalLayout();
        List<Training> trainings =
                NavigatorUI.getDBProvider().getUserTrainigs(NavigatorUI.getLoggedUser());
        trainings.forEach(c -> lay.addComponentsAndExpand(generateTrainingPanel(c)));
        return lay;
    }

    private Component generateTrainingPanel(Training c) {
        VerticalLayout lay = new VerticalLayout();
        Course trainingCourse = NavigatorUI.getDBProvider().getCourse(c.course_id);

        Label courseName = new Label();
        courseName.setValue(trainingCourse.getName());
        courseName.setCaption(Words.TXT_COURSE_NAME);
        Label statusLabel = new Label(Words.TXT_COURSE_STATUS);
        if (c.getTrainingStatus() != null) {
            statusLabel.setValue(c.getTrainingStatus().getName());
        }

        lay.addComponent(courseName);
        lay.addComponent(statusLabel);
        lay.addComponent(createTrainingButtonsPane(c));
        return lay;
    }

    private Component createTrainingButtonsPane(Training training) {
        VerticalLayout buttonsLayout = new VerticalLayout();

        Button detailsButton = new Button(Words.TXT_DETAILS);
        detailsButton.addClickListener(event -> {
            ((MainViewModel) getModel()).detailedTrainingPanelClicked(training, false, false);
        });

        buttonsLayout.addComponent(detailsButton);
        return buttonsLayout;
    }

    private boolean checkFilter(Course course) {
        if (filter != null) {
            return (course.description.contains(filter)
                    || course.name.contains(filter)
                    || course.getLecturer().contains(filter)
                    || course.getCategoryList().stream().anyMatch((t) -> {
                return t.description.contains(filter)
                        || t.name.contains(filter);

            }));
        } else {
            return true;
        }
    }

}
