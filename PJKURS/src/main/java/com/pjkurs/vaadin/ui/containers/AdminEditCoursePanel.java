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
import com.pjkurs.domain.Category;
import com.pjkurs.domain.Course;
import com.pjkurs.domain.SubCategory;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.ui.*;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Tmejs
 */
public class AdminEditCoursePanel<T extends AdminViewModel> extends MyContainer<T> {

    Course course;
    SubCategory selectedSubcategoryToAddToCourse;
    public AdminEditCoursePanel(Course course, T model) {
        super(false, model);
        this.course = course;
        this.course.setSubcategoryList(NavigatorUI.getDBProvider().getSubCategorysByCourseId(course.id));
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
            VerticalLayout lay = new VerticalLayout();
            Label label = new Label(Words.TXT_CATEGORIES);
            lay.addComponent(label);
            lay.addComponent(new
                    HorizontalLayout(new Label(Words.TXT_SUBCATEGORYNAME),
                    new Label(Words.TXT_CATEGORIES)));

            course.getSubcategoryList().forEach(subCategory ->{
                    HorizontalLayout horizontalLayout = new
                            HorizontalLayout();
                    Label subCategoryLabel = new Label();
                    subCategoryLabel.setCaption(subCategory.name);

                    Label categoriesLabel = new Label();
                    categoriesLabel.setCaption(StringUtils.join(subCategory.getCategories().stream().map(category -> category.name).iterator(), ", "));

                    horizontalLayout.addComponent(subCategoryLabel);
                    horizontalLayout.addComponent(categoriesLabel);
                    lay.addComponent(horizontalLayout);
            });
            layout.addComponent(lay);
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
        TextArea categoryLabel = new TextArea("Kategorie: ");
        categoryLabel.setReadOnly(true);
        categoryLabel.setVisible(false);
        NativeSelect<SubCategory> subCategorySelect = new NativeSelect<>(Words.TXT_SELECT_SUB_CATEGORY);
        subCategorySelect.setItemCaptionGenerator(new ItemCaptionGenerator<SubCategory>() {
            @Override
            public String apply(SubCategory item) {
                return item.getName();
            }
        });

        subCategorySelect.setEmptySelectionAllowed(false);
        subCategorySelect.addSelectionListener((eventT) -> {
            selectedSubcategoryToAddToCourse = eventT.getSelectedItem().get();
            categoryLabel.setValue(StringUtils.join(selectedSubcategoryToAddToCourse.getCategories().stream().map(
                    Category::getName).iterator(),", "));
            categoryLabel.setVisible(true);
        });

        subCategorySelect.setItems(NavigatorUI.getDBProvider().getSubCategories().stream().filter(sb -> !course.getSubcategoryList().contains(sb)));

        Button addButton = new Button(Words.TXT_ADD, (newEvent) -> {
            Boolean isAdded = NavigatorUI.getDBProvider().addSubCategoryToCourse(course.id,
                    selectedSubcategoryToAddToCourse.id);
            if(isAdded){
                 Notification.show(Words.TXT_CORRECTRLY_SAVED, Notification.Type.TRAY_NOTIFICATION);
                 course = NavigatorUI.getDBProvider().getCourse(course.id);
                 setContent(generateOverwievVew(true));
            }else{
                Notification.show(Words.TXT_ERROR, Notification.Type.TRAY_NOTIFICATION);
            }
            subWindow.close();
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
                setContent(generateOverwievVew(true));
            });
            temp.addComponent(subCategoryNameLabel);
            temp.addComponent(deleteCategoryButton);
            categoriesLayout.addComponent(temp);
        });

        return categoriesLayout;
    }

}
