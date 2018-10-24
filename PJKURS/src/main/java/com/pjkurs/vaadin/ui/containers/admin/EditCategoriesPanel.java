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

import com.pjkurs.domain.Category;
import com.pjkurs.domain.SubCategory;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.pjkurs.vaadin.views.system.MyModel;
import com.vaadin.event.selection.SelectionListener;
import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
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
public class EditCategoriesPanel<T extends MyModel> extends MyContainer<T> {

    Category selectedCategory;
    SubCategory selectedSubCategory;

    List<Category> categoryList;

    Component categoriesPanel;
    Component subCategoriesPanel;

    public EditCategoriesPanel(T model) {
        super(model);
    }

    @Override
    public Component buildView() {
        HorizontalLayout lay = new HorizontalLayout();

        categoriesPanel = generateCategoriesPanel();
        subCategoriesPanel = generateSubCategoriesPanel();
        lay.addComponent(categoriesPanel);
        lay.addComponent(subCategoriesPanel);

        return lay;
    }

    private Component generateCategoriesPanel() {
        VerticalLayout lay = new VerticalLayout();
        Button deleteButton;
        deleteButton = new Button(Words.TXT_DELETE, (event) -> {
            NavigatorUI.getDBProvider().deleteCategory(selectedCategory);
            refreshCategoriesPanel();
        });

        lay.setSizeFull();

        List<Category> e = NavigatorUI.getDBProvider().getCategories();
        categoryList = e;
        if (!e.isEmpty()) {
            NativeSelect<String> categoriesSelect = new NativeSelect(Words.TXT_CATEGORIES, e.stream().map((t) -> {
                return t.name;
            }).collect(Collectors.toList()));

            categoriesSelect.setVisibleItemCount(e.size() != 1 ? e.size() : e.size() + 1);
            categoriesSelect.setEmptySelectionAllowed(false);
            categoriesSelect.addSelectionListener((event) -> {
                deleteButton.setEnabled(true);
                selectedCategory = e.stream().filter((t) -> {
                    return t.name.equals(event.getValue());
                }).collect(Collectors.toList()).get(0);
                refreshSubCategoriesPanel();
            });
            lay.addComponent(categoriesSelect);
        } else {
            lay.addComponent(new Label(Words.TXT_NO_CATEGORIES));
        }

        HorizontalLayout horLay = new HorizontalLayout();
        Button addBut = new Button(Words.TXT_ADD_NEW, (event) -> {
            com.vaadin.ui.Window subWindow = new Window(Words.TXT_INSERT_NEW_CATEGORY_DATA);
            VerticalLayout subContent = new VerticalLayout();

            final String[] categoryName = new String[1];
            final String[] categoryDescription = new String[1];

            TextArea nameArea = new TextArea(Words.TXT_CATEGORY_NAME, (newEvent) -> {
                categoryName[0] = newEvent.getValue();
            });

            TextArea descriptionArea = new TextArea(Words.TXT_CATEGORY_DESCRIPTION, (newEvent) -> {
                categoryDescription[0] = newEvent.getValue();
            });

            Button addButton = new Button("Dodaj nową", (newEvent) -> {
                NavigatorUI.getDBProvider().addNewCategory(categoryName[0], categoryDescription[0]);
                Notification.show(Words.TXT_CORRECTRLY_SAVED, Notification.Type.TRAY_NOTIFICATION);
                refreshCategoriesPanel();
                subWindow.close();
            });

            subContent.addComponent(nameArea);
            subContent.addComponent(descriptionArea);
            subContent.addComponent(addButton);
            subWindow.setContent(subContent);
            subWindow.center();
            getModel().currentUI.addWindow(subWindow);
        });

        horLay.addComponent(addBut);
        horLay.addComponent(deleteButton);

        lay.addComponent(horLay);

        return lay;
    }

    private Component generateSubCategoriesPanel() {
        VerticalLayout lay = new VerticalLayout();
        lay.setSizeFull();

        List<SubCategory> categories = NavigatorUI.getDBProvider().getSubCategories();

        if (selectedCategory != null) {
            Collection e = categories.stream().filter((t) -> {
                return t.getCategories().stream().anyMatch(category -> category.id.equals(selectedCategory.id));
            }).map((t) -> {
                return t.name;
            }).collect(Collectors.toList());
            if (e.isEmpty()) {
                lay.addComponent(new Label(Words.TXT_NO_SUBCATEGORIES));
            } else {
                NativeSelect<String> categoriesSelect = new NativeSelect(Words.TXT_SUB_CATEGORIES, e);

                categoriesSelect.setVisibleItemCount(e.size() != 1 ? e.size() : e.size() + 1);
                categoriesSelect.setEmptySelectionAllowed(false);

                categoriesSelect.addSelectionListener((event) -> {
                    selectedSubCategory = categories.stream().filter((t) -> {
                        return t.name.equals(event.getValue());
                    }).collect(Collectors.toList()).get(0);
                });
                lay.addComponent(categoriesSelect);
            }
        } else {
            lay.addComponent(new Label(Words.TXT_NO_SUBCATEGORIES));
        }

        return lay;
    }

    private void refreshSubCategoriesPanel() {
        Component newPanel = generateSubCategoriesPanel();
        ((HorizontalLayout) this.getContent()).replaceComponent(subCategoriesPanel, newPanel);
        subCategoriesPanel = newPanel;
    }

    private void refreshCategoriesPanel() {
        Component newPanel = generateCategoriesPanel();
        ((HorizontalLayout) this.getContent()).replaceComponent(categoriesPanel, newPanel);
        categoriesPanel = newPanel;
        refreshSubCategoriesPanel();
    }

}
