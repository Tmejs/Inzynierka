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
package com.pjkurs.vaadin.ui.containers.admin;

import com.pjkurs.InterfacePjkursDataProvider;
import com.pjkurs.domain.Category;
import com.pjkurs.domain.Course;
import com.pjkurs.domain.CourseStatus;
import com.pjkurs.domain.SubCategory;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.ui.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Tmejs
 */
public class AdminEditCoursePanel<T extends AdminViewModel> extends MyContainer<T> {

    Course course;
    SubCategory selectedSubcategoryToAddToCourse;

    Component mainPanel;
    private boolean inEditMode = false;

    public AdminEditCoursePanel(Course course, T model) {
        super(false, model);
        this.course = course;
        this.course.setSubcategoryList(
                NavigatorUI.getDBProvider().getSubCategorysByCourseId(course.id));
        this.setContent(buildView());
    }

    @Override
    public Component buildView() {
        VerticalLayout layout = new VerticalLayout();

        mainPanel = generateMainPanel();
        layout.addComponent(mainPanel);
        return layout;
    }

    private Component generateMainPanel() {
        if (inEditMode) {
            return generateEditablePanel();
        } else {
            return generateOverwievVew();
        }
    }

    private Component generateOverwievVew() {
        VerticalLayout layout = new VerticalLayout();

        TextArea courseName = new TextArea(Words.TXT_COURSE_NAME);
        courseName.setReadOnly(true);
        courseName.setValue(course.getName());
        if(course.getName()!=null) courseName.setValue(course.getName());
        TextArea courseDesc = new TextArea(Words.TXT_COURSE_DESCRIPTION);
        courseDesc.setReadOnly(true);
        if(course.getDescription()!=null)courseDesc.setValue(course.getDescription());

        TextArea coursePrice = new TextArea(Words.TXT_PRICE);
        coursePrice.setReadOnly(true);
        if(course.price!=null)coursePrice.setValue(course.getPrice().toString());

        TextArea participants = new TextArea(Words.TXT_COURSE_PARTICIPANTS);
        participants.setReadOnly(true);
        if(course.paricipants!=null) participants.setValue(course.paricipants.toString());

        TextArea status = new TextArea(Words.TXT_COURSE_STATUS);
        status.setReadOnly(true);
        status.setValue(course.getCourseStatusAsString());

        Component categories = generateCategoriesView();

        Button editButton = new Button(Words.TXT_EDIT);

        editButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                inEditMode=true;
                refreshView();
            }
        });


        layout.addComponent(courseName);
        layout.addComponent(courseDesc);
        layout.addComponent(participants);
        layout.addComponent(coursePrice);
        layout.addComponent(status);
        layout.addComponent(categories);
        layout.addComponent(editButton);
        return layout;
    }


    private Component generateCategoriesView() {
        VerticalLayout lay = new VerticalLayout();
        Label label = new Label(Words.TXT_CATEGORIES);
        lay.addComponent(label);
        lay.addComponent(new
                HorizontalLayout(new Label(Words.TXT_SUBCATEGORYNAME),
                new Label(Words.TXT_CATEGORIES)));

        course.getSubcategoryList().forEach(subCategory -> {
            HorizontalLayout horizontalLayout = new
                    HorizontalLayout();
            Label subCategoryLabel = new Label();
            subCategoryLabel.setCaption(subCategory.name);

            Label categoriesLabel = new Label();
            categoriesLabel.setCaption(StringUtils
                    .join(subCategory.getCategories().stream().map(category -> category.name)
                            .iterator(), ", "));

            horizontalLayout.addComponent(subCategoryLabel);
            horizontalLayout.addComponent(categoriesLabel);
            lay.addComponent(horizontalLayout);
        });
        return lay;


    }

    private void showSetCategoryPanel() {
        com.vaadin.ui.Window subWindow = new Window(Words.TXT_INSERT_NEW_CATEGORY_DATA);
        VerticalLayout subContent = new VerticalLayout();
        TextArea categoryLabel = new TextArea("Kategorie: ");
        categoryLabel.setReadOnly(true);
        categoryLabel.setVisible(false);
        NativeSelect<SubCategory> subCategorySelect = new NativeSelect<>(
                Words.TXT_SELECT_SUB_CATEGORY);
        subCategorySelect.setItemCaptionGenerator(new ItemCaptionGenerator<SubCategory>() {
            @Override
            public String apply(SubCategory item) {
                return item.getName();
            }
        });

        subCategorySelect.setEmptySelectionAllowed(false);
        subCategorySelect.addSelectionListener((eventT) -> {
            selectedSubcategoryToAddToCourse = eventT.getSelectedItem().get();
            categoryLabel.setValue(
                    StringUtils.join(selectedSubcategoryToAddToCourse.getCategories().stream().map(
                            Category::getName).iterator(), ", "));
            categoryLabel.setVisible(true);
        });

        List<SubCategory> filteredSubcatgory = NavigatorUI.getDBProvider().getSubCategories();
        if (course.getSubcategoryList() != null) {
            filteredSubcatgory = filteredSubcatgory.stream().filter(subCategory -> {
                return !course.getSubcategoryList().stream()
                        .anyMatch(subCategory1 -> subCategory.id == subCategory1.id);
            }).collect(Collectors.toList());
        }
        subCategorySelect.setItems(filteredSubcatgory);
        Button addButton = new Button(Words.TXT_ADD, (newEvent) -> {
            if (selectedSubcategoryToAddToCourse != null) {
                Boolean isAdded = NavigatorUI.getDBProvider().addSubCategoryToCourse(course.id,
                        selectedSubcategoryToAddToCourse.id);
                if (isAdded) {
                    this.course = NavigatorUI.getDBProvider().getCourse(course.id);
                    Notification
                            .show(Words.TXT_CORRECTRLY_SAVED, Notification.Type.TRAY_NOTIFICATION);
                    refreshView();
                } else {
                    Notification.show(Words.TXT_ERROR, Notification.Type.TRAY_NOTIFICATION);
                }
                selectedSubcategoryToAddToCourse = null;
                subWindow.close();
            }
        });

        subContent.addComponent(categoryLabel);
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
                refreshView();
            });
            temp.addComponent(subCategoryNameLabel);
            temp.addComponent(deleteCategoryButton);
            categoriesLayout.addComponent(temp);
        });

        return categoriesLayout;
    }

    private Component generateEditablePanel() {
        VerticalLayout layout = new VerticalLayout();

        Course editedCourse = new Course(course);

        TextArea courseName = new TextArea(Words.TXT_COURSE_NAME);
        courseName.setValue(course.getName());
        if(course.getName()!=null) courseName.setValue(course.getName());
        TextArea courseDesc = new TextArea(Words.TXT_COURSE_DESCRIPTION);
        if(course.getDescription()!=null)courseDesc.setValue(course.getDescription());

        TextArea coursePrice = new TextArea(Words.TXT_PRICE);
        if(course.price!=null)coursePrice.setValue(course.getPrice().toString());

        Label label = new Label(Words.TXT_CATEGORIES);
        layout.addComponent(label);

        NativeSelect<CourseStatus> statuses = new NativeSelect<>();
        statuses.setCaption(Words.TXT_COURSE_STATUS);
        statuses.setEmptySelectionAllowed(true);
        statuses.setItems(NavigatorUI.getDBProvider().getCourseStatuses());
        if (course.getCourseStatus() != null)
            statuses.setSelectedItem(course.getCourseStatus());
        statuses.setItemCaptionGenerator(item -> item.name);
        statuses.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                editedCourse.statusId = event.getValue().id;
            }
        });

        Component categoriesEditableComponent = generateCategoriesEditableComponent();

        Button addCategoriesButton = new Button(Words.TXT_ADD_CATEGORY);

        addCategoriesButton.addClickListener((event) -> {
            showSetCategoryPanel();
        });


        Button editButton = new Button(Words.TXT_SAVE_DATA);

        editButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    editedCourse.name = courseName.getValue();
                    editedCourse.description = courseDesc.getValue();
                    if(coursePrice.getValue()!=null || !coursePrice.getValue().isEmpty())
                        editedCourse.price = Double.valueOf(coursePrice.getValue());
                    else
                        editedCourse.price=0d;
                    inEditMode = false;
                    NavigatorUI.getDBProvider().updateCourse(editedCourse);
                    Notification.show(Words.TXT_CHANGED_DATA_SAVED,
                            Notification.Type.TRAY_NOTIFICATION);
                    course = NavigatorUI.getDBProvider().getCourse(course.id);
                    refreshView();
                } catch (Exception e) {
                    Notification.show(Words.TXT_NOT_SAVED_CHECK_DATA,
                            Notification.Type.TRAY_NOTIFICATION);
                    Logger.getLogger(getClass().toString()).log(Level.WARNING,"SAVE data", e);
                }
            }
        });

        Button undoButton = new Button(Words.TXT_DISCARD_CHANGES);
        undoButton.addClickListener(event -> {
            inEditMode =false;
            refreshView();
        });

        layout.addComponent(courseName);
        layout.addComponent(courseDesc);
        layout.addComponent(coursePrice);
        layout.addComponent(statuses);
        layout.addComponent(categoriesEditableComponent);
        layout.addComponent(addCategoriesButton);
        layout.addComponent(editButton);
        layout.addComponent(undoButton);

        return  layout;
    }

}
