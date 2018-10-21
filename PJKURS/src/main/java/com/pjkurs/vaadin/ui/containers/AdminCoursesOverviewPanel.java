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
import com.pjkurs.domain.CourseStatus;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.ItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tmejs
 */
public class AdminCoursesOverviewPanel<T extends AdminViewModel> extends MyContainer<T> {

    Grid<Course> grid;
    List<Course> courses;
    List<CourseStatus> filteredStatuses;
    boolean showNotStatused = true;

    private List<CourseStatus> getFilteredStatuses() {
        if (filteredStatuses == null) {
            filteredStatuses = new ArrayList<>();
        }
        return filteredStatuses;
    }

    public AdminCoursesOverviewPanel(T model) {
        super(model);
    }

    private void updateGrid() {
        List<Course> shownList = new ArrayList<>(courses);
        if (!getFilteredStatuses().isEmpty()) {
            shownList = shownList.stream().filter(o ->
                    getFilteredStatuses().stream()
                            .anyMatch(courseStatus -> courseStatus.id == o.getStatusId())
            ).collect(Collectors.toList());
        }
        if (showNotStatused) {
            shownList.addAll(courses.stream().filter(o -> o.getCourseStatus() == null)
                    .collect(Collectors.toList()));
        }
        grid.setItems(shownList);
    }

    private Component buildFiltersLayout() {
        HorizontalLayout lay = new HorizontalLayout();
        List<CourseStatus> statuses = NavigatorUI.getDBProvider().getCourseStatuses();
        List<CheckBox> checkboxes = new ArrayList<>();
        CheckBox cb = new CheckBox(Words.TXT_NO_STATUS, true);
        showNotStatused=true;
        cb.addValueChangeListener(event -> {
            showNotStatused = event.getValue();
        });
        checkboxes.add(cb);
        statuses.forEach(courseStatus -> {
            CheckBox checkBox = new CheckBox(courseStatus.name, true);
            getFilteredStatuses().add(courseStatus);
            checkBox.addValueChangeListener(event -> {
                if (event.getValue()) {
                    getFilteredStatuses().add(courseStatus);
                } else {
                    getFilteredStatuses().remove(courseStatus);
                }
                updateGrid();
            });
            checkboxes.add(checkBox);
        });


        Button setAllButton = new Button(Words.TXT_ALL, new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                checkboxes.forEach(checkBox -> checkBox.setValue(true));
                updateGrid();
            }
        });
        checkboxes.forEach(lay::addComponent);
        lay.addComponent(setAllButton);
        return lay;
    }

    @Override
    public Component buildView() {
        VerticalLayout layout = new VerticalLayout();

        layout.addComponent(buildFiltersLayout());
        courses = NavigatorUI.getDBProvider().getAllCourses();

        grid = new Grid<>();
        grid.setItems(courses);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setColumnReorderingAllowed(true);
        grid.setSizeFull();

        grid.setResponsive(true);
        //Kolumny

        grid.addColumn(Course::getName).setCaption(Words.TXT_COURSE_NAME);
        grid.addColumn(Course::getCourseStatusAsString).setCaption(Words.TXT_COURSE_STATUS);
        grid.setColumnResizeMode(ColumnResizeMode.SIMPLE);

        grid.setFrozenColumnCount(2);
        grid.addItemClickListener(new ItemClickListener<Course>() {
            @Override
            public void itemClick(Grid.ItemClick<Course> event) {
                if (event.getMouseEventDetails().isDoubleClick()) {
                    ((AdminViewModel) getModel()).editCourseDataButtonClicked(event.getItem());
                }
            }
        });
        layout.addComponent(grid);
        updateGrid();
        return layout;
    }

    private PopupView.Content buildPopupContent() {
        PopupView.Content content = new PopupView.Content() {
            @Override
            public String getMinimizedValueAsHTML() {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                return "aa";
            }

            @Override
            public Component getPopupComponent() {
                VerticalLayout lay = new VerticalLayout();
                lay.addComponent(new Button("Edytuj"));
                lay.addComponent(new Button("Ankiety"));
                return lay;
            }
        };
        return content;
    }

}
