package com.pjkurs.vaadin.ui.containers;

import com.pjkurs.domain.Category;
import com.pjkurs.domain.SubCategory;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.pjkurs.vaadin.views.system.MyModel;
import com.vaadin.ui.*;
import org.vaadin.addons.ComboBoxMultiselect;

import java.util.*;

public class EditSubcategoriesPanel<T extends MyModel> extends MyContainer<T> {

    private Component subCategoriesPanel;
    private SubCategory selectedSubCategory;
    private Category selectedCategory;
    private Component categoriesPanel;
    private boolean isEditing;

    public EditSubcategoriesPanel(T model) {
        super(model);
    }

    @Override
    public Component buildView() {
        HorizontalLayout lay = new HorizontalLayout();

        subCategoriesPanel = generateSubcategoriesPanel();
        lay.addComponent(subCategoriesPanel);

        categoriesPanel = generateMultiSelectPanel();

        lay.addComponent(categoriesPanel);
        return lay;
    }

    private Component generateMultiSelectPanel() {
        VerticalLayout lay = new VerticalLayout();
        lay.setSizeFull();
        if (selectedSubCategory != null) {
            lay.addComponent(getMultiSelectPanel());
        } else {
            lay.addComponent(new Label(Words.TXT_SELECT_SUB_CATEGORY));
        }
        return lay;
    }

    private Button generateModifyButon() {
        Button startModifyButton = new Button(Words.TXT_DURING_MODIFICATION);
        startModifyButton.addClickListener(event -> {
            isEditing = true;
            refreshCategoriesPanel();
        });
        return startModifyButton;
    }


    private Component getMultiSelectPanel() {
        if (isEditing) {
            return genereteEditingPanel();
        } else {
            return generateCategoriesView();
        }
    }

    private Component generateCategoriesView() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        List<Category> e = selectedSubCategory.getCategories();
        if (!e.isEmpty()) {
            NativeSelect<Category> categoriesSelect = new NativeSelect(Words.TXT_CATEGORIES,
                    e);
            categoriesSelect.setItemCaptionGenerator(item -> item.name);
            categoriesSelect.setVisibleItemCount(e.size() != 1 ? e.size() : e.size() + 1);
            categoriesSelect.setSizeFull();
            layout.addComponent(categoriesSelect);
        } else {
            layout.addComponent(new Label(Words.TXT_NO_SELECTED_CATEGORIES));
        }
        layout.addComponent(generateModifyButon());
        return layout;
    }

    private Component genereteEditingPanel() {
        VerticalLayout lay = new VerticalLayout();
        lay.setSizeFull();
        List<Category> e = NavigatorUI.getDBProvider().getCategories();
        Set<Category> includedCategories = new HashSet<>(selectedSubCategory.getCategories());
        ComboBoxMultiselect<Category> categoriesSelect =
                new ComboBoxMultiselect<>("Wybrane kategorie");
        categoriesSelect.setItemCaptionGenerator(item -> item.name);
        categoriesSelect.setItems(e);
        categoriesSelect.setValue(includedCategories);
        categoriesSelect.setSizeFull();

        Button saveChangesButton = new Button(Words.TXT_SAVE_DATA);
        saveChangesButton.addClickListener(event -> {
            includedCategories.forEach(category -> {
                if (!categoriesSelect.getValue().contains(category)) {
                    applyCategoryChange(category, false);
                }
            });
            categoriesSelect.getValue().stream()
                    .filter(category -> !includedCategories.contains(category))
                    .forEach(category -> applyCategoryChange(category, true));
            isEditing = false;
            refreshCategoriesPanel();
        });
        lay.addComponent(categoriesSelect);
        lay.addComponent(saveChangesButton);
        return lay;
    }

    private void applyCategoryChange(Category category, boolean b) {
        if (b) {
            NavigatorUI.getDBProvider().addSubCategoryToCategory(selectedSubCategory, category);
        } else {
            NavigatorUI.getDBProvider()
                    .deleteSubcategoryFromCategory(selectedSubCategory, category);
        }

    }

    private Component generateSubcategoriesPanel() {
        VerticalLayout lay = new VerticalLayout();
        lay.setSizeFull();
        Button deleteButton = generateDeleteButton();
        Button addBut = generateAddButton();

        lay.addComponent(generateSubcategoriesPanel(deleteButton));
        lay.addComponent(deleteButton);
        lay.addComponent(addBut);

        return lay;
    }

    private void refreshSubCategoriesPanel() {
        Component newPanel = generateSubcategoriesPanel();
        ((HorizontalLayout) this.getContent()).replaceComponent(subCategoriesPanel, newPanel);
        subCategoriesPanel = newPanel;
    }

    private void refreshCategoriesPanel() {
        selectedSubCategory = NavigatorUI.getDBProvider().getSubCategory(selectedSubCategory.id);
        Component newPanel = generateMultiSelectPanel();
        ((HorizontalLayout) this.getContent()).replaceComponent(categoriesPanel, newPanel);
        categoriesPanel = newPanel;
    }

    private Component generateSubcategoriesPanel(Button deleteButton) {
        List<SubCategory> e = NavigatorUI.getDBProvider().getSubCategories();
        if (!e.isEmpty()) {
            NativeSelect<SubCategory> categoriesSelect = new NativeSelect(Words.TXT_CATEGORIES,
                    e);
            categoriesSelect.setItemCaptionGenerator(item -> item.name);
            categoriesSelect.setVisibleItemCount(e.size() != 1 ? e.size() : e.size() + 1);
            categoriesSelect.setEmptySelectionAllowed(false);
            categoriesSelect.addSelectionListener((event) -> {
                deleteButton.setEnabled(true);
                selectedSubCategory = event.getValue();
                refreshCategoriesPanel();
            });
            categoriesSelect.setSizeFull();
            return categoriesSelect;
        } else {
            return new Label(Words.TXT_NO_SUBCATEGORIES);
        }
    }

    private Button generateAddButton() {
        return new Button(Words.TXT_ADD_NEW, (event) -> {
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
                NavigatorUI.getDBProvider()
                        .addNewSubCategory(categoryName[0], categoryDescription[0]);
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
    }

    private Button generateDeleteButton() {
        return new Button(Words.TXT_DELETE, (event) -> {
            NavigatorUI.getDBProvider().deleteSubCategory(selectedSubCategory);
            refreshSubCategoriesPanel();
        });
    }
}
