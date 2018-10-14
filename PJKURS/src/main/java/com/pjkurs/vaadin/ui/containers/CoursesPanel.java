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
import com.vaadin.annotations.Theme;
import com.vaadin.data.HasValue;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Tmejs
 */
@Theme("pjtheme")
public class CoursesPanel<T extends MyModel> extends MyContainer<T> {

    public CoursesPanel(Long categoryId, T model) {
        super(model);
        this.categoryId = categoryId;
        Logger.getGlobal().log(Level.SEVERE, "category ID:" + categoryId);
        this.setContent(buildView());
    }

    String filter;
    Component coursesComponent;
    VerticalLayout mainViewComponent;
    Long categoryId;

    private Component buildFiltersMenu() {
        VerticalLayout mainLayout = new VerticalLayout();

        if (this.categoryId != null) {
            String[] categoryName = new String[2];
            NavigatorUI.getDBProvider().getCategories().forEach((t) -> {
                if (t.id.equals(this.categoryId)) {
                    categoryName[0] = t.name;
                    categoryName[1] = t.description;

                }
            });
            Label label = new Label(categoryName[0] + ": " + categoryName[1]);
            mainLayout.addComponent(label);
        }
        HorizontalLayout horLay = new HorizontalLayout();

        Label filterName = new Label(Words.TXT_FIND);
        horLay.addComponent(filterName);

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

        horLay.addComponent(filteredText);
        mainLayout.addComponent(horLay);

        return mainLayout;
    }

    private Component buildCourses() {
        Logger.getGlobal().log(Level.SEVERE, "buildCourses()" + categoryId);
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setWidth("100%");
        List<Course> tempCourses = NavigatorUI.getDBProvider().getAvalibleCourses();
        List<Course> courses = tempCourses.stream().filter((t) -> {
            return checkFilter(t);
        }).collect(Collectors.toList());

        if (courses == null || courses.isEmpty()) {
            mainLayout.addComponent(new Label(Words.TXT_NO_AVALIBLE_COURSES));
            return mainLayout;
        }

        //Utworzenie komponentów
        Integer counter = 0;
        HorizontalLayout horizontalPanel = new HorizontalLayout();
        horizontalPanel.setWidth("100%");

        for (Course course : courses) {
            CoursePanel coursePanel = new CoursePanel(course, getModel());
            paintCoursePanel(coursePanel, course);
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

    private void paintCoursePanel(CoursePanel panel, Course course) {

        if (course.statusId == 2) {
            panel.addStyleName("new-course");
        }
        if (course.statusId == 1) {
            panel.addStyleName("end-course");
        }
        if (course.statusId == 3) {
            panel.addStyleName("starting-course");
        }

    }

    @Override
    public Component buildView() {
        Logger.getGlobal().log(Level.SEVERE, "buildView()");
        this.setWidth("100%");

        VerticalLayout mainView = new VerticalLayout();
        mainViewComponent = mainView;

        mainView.setSizeFull();

        coursesComponent = buildCourses();
        mainView.addComponent(buildFiltersMenu());
        mainView.addComponent(coursesComponent);

        return mainView;

    }

    private boolean checkFilter(Course course) {
        Logger.getGlobal().log(Level.SEVERE, "checkFilter()");

        if (this.categoryId != null) {
            Logger.getGlobal().log(Level.SEVERE, "category ID w check:" + categoryId);
            if (!course.getSubcategoryList().stream().anyMatch((t) -> {
                return t.getCategories().stream().anyMatch(category -> category.id.equals(categoryId)); //To change body of generated lambdas, choose Tools | Templates.
            })) {
                return false;
            }
        }
        if (filter != null) {
            return (course.description.contains(filter)
                    || course.name.contains(filter)
                    || course.getLecturer().contains(filter)
                    || course.getSubcategoryList().stream().anyMatch((t) -> {
                        return t.description.contains(filter)
                                || t.name.contains(filter)
                                || t.getCategories().stream().anyMatch(category -> category.id.equals(categoryId));
                    }));
        } else {
            return true;
        }
    }

}
