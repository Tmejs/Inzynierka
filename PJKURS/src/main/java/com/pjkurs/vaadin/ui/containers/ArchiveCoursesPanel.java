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
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.data.HasValue;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.List;

/**
 *
 * @author Tmejs
 */
public class ArchiveCoursesPanel<T extends MainViewModel> extends MyContainer<T> {

    public ArchiveCoursesPanel(T model) {
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
        List<ArchiveCourse> courses = NavigatorUI.getDBProvider().getArchiveCourses();

        //Sprawdzenie czy są dostępne kursy
        if (courses == null || courses.isEmpty()) {
            mainLayout.addComponent(new Label(Words.TXT_NO_AVALIBLE_COURSES));
            return mainLayout;
        }

        //Utworzenie komponentów
        Integer counter = 0;
        HorizontalLayout horizontalPanel = new HorizontalLayout();
        horizontalPanel.setWidth("100%");

        for (ArchiveCourse course : courses) {
            if (!checkFilter(course)) {
                continue;
            }

            ArchiveCoursePanel coursePanel = new ArchiveCoursePanel(course, getModel());
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

    private boolean checkFilter(ArchiveCourse course) {

        if (filter != null) {
            return (course.description.contains(filter)
                    || course.name.contains(filter)
                    || course.subcategoryList.stream().anyMatch((t) -> {
                        return t.description.contains(filter)
                                || t.name.contains(filter)
                                || t.getCategories().stream().anyMatch(category -> category.name.contains(filter))
                                || t.getCategories().stream().anyMatch(category -> category.description.contains(filter));
                    }));
        } else {
            return true;
        }
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
        mainView.addComponent(buildFiltersMenu());
        mainView.addComponent(coursesComponent);

        return mainView;

    }

}
