package com.pjkurs.vaadin.ui.containers;

import com.pjkurs.domain.Category;
import com.pjkurs.domain.SubCategory;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.pjkurs.vaadin.views.system.MyModel;
import com.vaadin.ui.*;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class EditSubcategoriesPanel<T extends MyModel> extends MyContainer<T> {

    private Component categoriesPanel;
    private SubCategory selectedSubCategory;
    private Category selectedCategory;
    private List<SubCategory> subCategoryList;
    private Component multiSelect;
    public EditSubcategoriesPanel(T model) {
        super(model);
    }

    @Override
    public Component buildView() {
        HorizontalLayout lay = new HorizontalLayout();

        categoriesPanel = generateSubcategoriesPanel();
        lay.addComponent(categoriesPanel);

        multiSelect = generateMultiSelectPanel();

        return lay;
    }

    private Component generateMultiSelectPanel() {
        VerticalLayout lay = new VerticalLayout();
        List<Category> e = NavigatorUI.getDBProvider().getCategories();
        if (!e.isEmpty()) {
            NativeSelect<String> categoriesSelect = new NativeSelect(Words.TXT_CATEGORIES, e.stream().map((t) -> {
                return t.name;
            }).collect(Collectors.toList()));

            categoriesSelect.setVisibleItemCount(e.size() != 1 ? e.size() : e.size() + 1);
            categoriesSelect.setEmptySelectionAllowed(false);
            categoriesSelect.addSelectionListener((event) -> {
                selectedCategory = e.stream().filter((t) -> {
                    return t.name.equals(event.getValue());
                }).collect(Collectors.toList()).get(0);
            });
            lay.addComponent(categoriesSelect);
        } else {
            lay.addComponent(new Label(Words.TXT_COURSES_OF_SUBCOURSE));
        }
        return lay;
    }

    private Component generateSubcategoriesPanel() {
        VerticalLayout lay = new VerticalLayout();
        Button deleteButton;
        deleteButton = new Button(Words.TXT_DELETE, (event) -> {
            NavigatorUI.getDBProvider().deleteSubCategory(selectedSubCategory);
            refreshSubCategoriesPanel();
        });

        lay.setSizeFull();

        List<SubCategory> e = NavigatorUI.getDBProvider().getSubCategories();
        subCategoryList = e;
        if (!e.isEmpty()) {
            NativeSelect<String> categoriesSelect = new NativeSelect(Words.TXT_CATEGORIES, e.stream().map((t) -> {
                return t.name;
            }).collect(Collectors.toList()));

            categoriesSelect.setVisibleItemCount(e.size() != 1 ? e.size() : e.size() + 1);
            categoriesSelect.setEmptySelectionAllowed(false);
            categoriesSelect.addSelectionListener((event) -> {
                deleteButton.setEnabled(true);
                selectedSubCategory = e.stream().filter((t) -> {
                    return t.name.equals(event.getValue());
                }).collect(Collectors.toList()).get(0);
            });
            lay.addComponent(categoriesSelect);
        } else {
            lay.addComponent(new Label(Words.TXT_NO_SUBCATEGORIES));
        }

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

            Button addButton = new Button(Words.TXT_ADD, (newEvent) -> {
                NavigatorUI.getDBProvider().addNewSubCategory(categoryName[0], categoryDescription[0]);
                refreshSubCategoriesPanel();
                Notification.show(Words.TXT_CORRECTRLY_SAVED, Notification.Type.TRAY_NOTIFICATION);
                subWindow.close();
            });

            subContent.addComponent(nameArea);
            subContent.addComponent(descriptionArea);
            subContent.addComponent(addButton);
            subWindow.setContent(subContent);
            subWindow.center();
            getModel().currentUI.addWindow(subWindow);
        });

        lay.addComponent(deleteButton);
        lay.addComponent(addBut);

        return lay;
    }

    private void refreshSubCategoriesPanel() {
        Component newPanel = generateSubcategoriesPanel();
        ((HorizontalLayout) this.getContent()).replaceComponent(categoriesPanel, newPanel);
        categoriesPanel = newPanel;
    }
}
