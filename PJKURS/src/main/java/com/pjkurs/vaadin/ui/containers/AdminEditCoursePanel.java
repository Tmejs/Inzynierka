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

import com.pjkurs.InterfacePjkursDataProvider;
import com.pjkurs.db.DbDataProvider;
import com.pjkurs.domain.Category;
import com.pjkurs.domain.Course;
import com.pjkurs.domain.CourseSubCategory;
import com.pjkurs.domain.SubCategory;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.MultiSelect;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Tmejs
 */
public class AdminEditCoursePanel<T extends AdminViewModel> extends MyContainer<T> {

    Course course;

    public AdminEditCoursePanel(Course course, T model) {
        super(false, model);
        this.course = course;
        this.setContent(buildView());
    }

    @Override
    public Component buildView() {
        return generateOverwievVew(false);
    }

    private Component generateOverwievVew(Boolean isEditable) {
        VerticalLayout layout = new VerticalLayout();

        if (isEditable) {
            layout.addComponent(new Label(Words.TXT_DURING_MODIFICATION));
        }
        TextArea courseName = new TextArea(Words.TXT_COURSE_NAME, this.course.name);
        courseName.setReadOnly(!isEditable);
        layout.addComponent(courseName);

        TextArea courseDesc = new TextArea(Words.TXT_COURSE_DESCRIPTION, this.course.description);
        courseDesc.setReadOnly(!isEditable);
        layout.addComponent(courseDesc);

//        Informacje o ilości zapiasnych ludzi
//        layout.addComponent(new Label(Words.TXT_COURSE_PARTICIPANTS));
//        layout.addComponent(new Label(course.paricipants.toString()));
        if (!isEditable) {

            Label label = new Label(Words.TXT_CATEGORIES);
            layout.addComponent(label);

            Grid<CourseSubCategory> grid = new Grid<>();
            grid.setItems(course.getSubcategoryList());
            grid.setSelectionMode(Grid.SelectionMode.SINGLE);
            grid.setColumnReorderingAllowed(true);
            grid.setSizeFull();
            grid.setResponsive(true);
            //Kolumny
            grid.addColumn(CourseSubCategory::getCategoryName).setCaption(Words.TXT_CATEGORY_NAME);
            grid.addColumn(CourseSubCategory::getName).setCaption(Words.TXT_SUBCATEGORYNAME);
            grid.setColumnResizeMode(ColumnResizeMode.SIMPLE);
            layout.addComponent(grid);

            Button editButton = new Button(Words.TXT_EDIT);

            editButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    setContent(generateOverwievVew(true));
                }
            });
            layout.addComponent(editButton);
        } else {
            Label label = new Label(Words.TXT_CATEGORIES);
            layout.addComponent(label);

            Component categoriesEditableComponent = generateCategoriesEditableComponent();
            layout.addComponent(categoriesEditableComponent);

            Button addCategoriesButton = new Button(Words.TXT_ADD_CATEGORY);

            addCategoriesButton.addClickListener((event) -> {
                showSetCategoryPanel();
            });

            layout.addComponent(addCategoriesButton);
            Button editButton = new Button(Words.TXT_SAVE_DATA);

            editButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    //TODO update danych
                    setContent(generateOverwievVew(false));
                    Notification.show(Words.TXT_CHANGED_DATA_SAVED, Notification.Type.TRAY_NOTIFICATION);

                }
            });
            layout.addComponent(editButton);
        }

        return layout;
    }

    private void showSetCategoryPanel() {
        com.vaadin.ui.Window subWindow = new Window(Words.TXT_INSERT_NEW_CATEGORY_DATA);
        VerticalLayout subContent = new VerticalLayout();
        final String[] selectedCategoryName = new String[1];
        final String[] selectedSubCategoryName = new String[1];
        NativeSelect<String> categorySelect;
        NativeSelect<String> subCategorySelect = new NativeSelect<>(Words.TXT_SELECT_SUB_CATEGORY);

        subCategorySelect.setEmptySelectionAllowed(false);
        subCategorySelect.addSelectionListener((eventT) -> {
            selectedSubCategoryName[0] = eventT.getValue();
        });

        categorySelect = new NativeSelect<>(Words.TXT_SELECT_CATEGORY, NavigatorUI.getDBProvider().getCategories().stream().map((t) -> {
            return t.name;
        }).collect(Collectors.toList()));

        categorySelect.setEmptySelectionAllowed(false);

        categorySelect.addSelectionListener(new SingleSelectionListener<String>() {
            @Override
            public void selectionChange(SingleSelectionEvent<String> event) {
                selectedCategoryName[0] = event.getValue();
                List<SubCategory> subList = NavigatorUI.getDBProvider().getSubCategories();
                subList = subList.stream().filter((t) -> {
                    return t.getCategories().stream().anyMatch(category -> category.id.equals(selectedCategoryName[0]));
                }).collect(Collectors.toList());

                subCategorySelect.setItems(subList.stream().map((t) -> {
                    return t.getName();
                }).collect(Collectors.toList()));

            }
        });

        Button addButton = new Button(Words.TXT_ADD, (newEvent) -> {

            List<SubCategory> subList = NavigatorUI.getDBProvider().getSubCategories();
            subList = subList.stream().filter((tsubCat) -> {
                return tsubCat.getName().equals(selectedSubCategoryName[0]);
            }).collect(Collectors.toList());

            Boolean isAdded = false;
            if (subList.size() > 0) {
                isAdded = NavigatorUI.getDBProvider().addSubCategoryToCourse(course.id, subList.get(0).id);
                Notification.show(Words.TXT_CORRECTRLY_SAVED, Notification.Type.TRAY_NOTIFICATION);
                setContent(generateOverwievVew(true));
            }
            
            if(isAdded){
                 Notification.show(Words.TXT_CORRECTRLY_SAVED, Notification.Type.TRAY_NOTIFICATION);
                 course.setSubcategoryList(NavigatorUI.getDBProvider().getSubCategorysByCourseId(course.id));
                 setContent(generateOverwievVew(true));
            }else{
                Notification.show(Words.TXT_ERROR, Notification.Type.TRAY_NOTIFICATION);
            }
            subWindow.close();
        });

        subContent.addComponent(categorySelect);
        subContent.addComponent(subCategorySelect);
        subContent.addComponent(addButton);
        subWindow.setContent(subContent);
        subWindow.center();
        getModel().currentUI.addWindow(subWindow);
    }

    private Component generateCategoriesEditableComponent() {
        VerticalLayout categoriesLayout = new VerticalLayout();
        HorizontalLayout tempHeaders = new HorizontalLayout();
        Label categoryHeadName = new Label(Words.TXT_CATEGORY_NAME);
        Label subCategoryHeadName = new Label(Words.TXT_SUBCATEGORYNAME);

        tempHeaders.addComponent(categoryHeadName);
        tempHeaders.addComponent(subCategoryHeadName);

        course.getSubcategoryList().stream().forEach((t) -> {
            HorizontalLayout temp = new HorizontalLayout();

            Label subCategoryNameLabel = new Label(t.name);
            subCategoryNameLabel.setDescription(t.description);

            Button deleteCategoryButton = new Button("-");
            deleteCategoryButton.addClickListener((event) -> {
                InterfacePjkursDataProvider dataProvider = NavigatorUI.getDBProvider();
                dataProvider.deleteSubCategoryFromCourse(course.getId(), t.id);
                course.setSubcategoryList(dataProvider.getSubCategorysByCourseId(course.getId()));
                setContent(generateOverwievVew(true));
            });
            temp.addComponent(subCategoryNameLabel);
            temp.addComponent(deleteCategoryButton);
            categoriesLayout.addComponent(temp);
        });

        return categoriesLayout;
    }

}
