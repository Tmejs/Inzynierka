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
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.data.HasValue;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.components.grid.ItemClickListener;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

/**
 *
 * @author Tmejs
 */
public class AdminCoursesOverviewPanel<T extends AdminViewModel> extends MyContainer<T> {

    Grid<Course> grid;
    List courses;
    List shownList = new ArrayList<Course>();
    Boolean isNewCourses = true;
    Boolean isOngoingCourses = true;
    Boolean isArchiveCourses = true;

    public AdminCoursesOverviewPanel(T model) {
        super(model);
    }

    private void updateGrid() {
        shownList.clear();
        shownList.addAll(courses);

        if (!isArchiveCourses) {
            shownList.removeAll((List) shownList.stream().filter((t) -> {
                return ((Course) t).getCourseStatus().equals(Course.CourseStatus.ARCHIWALNY);
            }).collect(Collectors.toList()));
        }

        if (!isNewCourses) {
            shownList.removeAll((List) shownList.stream().filter((t) -> {
                return ((Course) t).getCourseStatus().equals(Course.CourseStatus.NOWY);
            }).collect(Collectors.toList()));
        }

        if (!isOngoingCourses) {
            shownList.removeAll((List) shownList.stream().filter((t) -> {
                return ((Course) t).getCourseStatus().equals(Course.CourseStatus.TRWAJACY);
            }).collect(Collectors.toList()));
        }

        grid.setItems(shownList);

    }

    private Component buildFiltersLayout() {
        HorizontalLayout lay = new HorizontalLayout();

        CheckBox newCoursesFilter = new CheckBox(Words.TXT_NEW_COURSES, true);
        newCoursesFilter.addValueChangeListener(new HasValue.ValueChangeListener<Boolean>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<Boolean> event) {
                isNewCourses = event.getValue();
                updateGrid();
            }
        });

        CheckBox archiveCoursesFilter = new CheckBox(Words.TXT_ARCHIVE_COURSES, true);
        archiveCoursesFilter.addValueChangeListener(new HasValue.ValueChangeListener<Boolean>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<Boolean> event) {
                isArchiveCourses = event.getValue();
                updateGrid();
            }
        });
        CheckBox ongoingCoursesFilter = new CheckBox(Words.TXT_ONGOING_COURSES, true);
        ongoingCoursesFilter.addValueChangeListener(new HasValue.ValueChangeListener<Boolean>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<Boolean> event) {
                isOngoingCourses = event.getValue();
                updateGrid();
            }
        });

        Button setAllButton = new Button(Words.TXT_ALL, new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                newCoursesFilter.setValue(Boolean.TRUE);
                archiveCoursesFilter.setValue(Boolean.TRUE);
                ongoingCoursesFilter.setValue(Boolean.TRUE);
                updateGrid();
            }
        });

        lay.addComponents(newCoursesFilter, ongoingCoursesFilter, archiveCoursesFilter, setAllButton);
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
        grid.addColumn(Course::getCategoryName).setCaption(Words.TXT_COURSE_CATEGORY_NAME);
        grid.addColumn(Course::getSubcategoryName).setCaption(Words.TXT_COURSE_SUB_CATEGORY_NAME);
        grid.addColumn(Course::getCurseStatusAsString).setCaption(Words.TXT_COURSE_STATUS);
        grid.setColumnResizeMode(ColumnResizeMode.SIMPLE);
        grid.addComponentColumn(this::getEditButton).setHidden(true);

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

        return layout;
    }

    private Button getEditButton(Course course) {
        Button but = new Button();

        return but;
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
