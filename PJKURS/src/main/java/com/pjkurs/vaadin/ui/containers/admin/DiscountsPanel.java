
package com.pjkurs.vaadin.ui.containers.admin;

import com.pjkurs.domain.Discount;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class DiscountsPanel<T extends AdminViewModel> extends MyContainer<T> {

    Discount selectedDiscount;
    Component discountInfoPanel;
    Boolean isInEditionMode;
    TextArea discountEditableArea;

    public DiscountsPanel(T model) {
        super(false, model);
        isInEditionMode = false;
        setContent(buildView());
    }

    @Override
    public Component buildView() {
        HorizontalLayout layout = new HorizontalLayout();

        Component discountsComponent = getDiscountsComponent();

        if (selectedDiscount != null) {
            if (isInEditionMode) {
                discountInfoPanel = getDiscountInfoPanel();
            } else {
                discountInfoPanel = generateEditablePanel();
            }
        } else {
            discountInfoPanel = new HorizontalLayout();
        }

        layout.addComponent(discountsComponent);
        layout.addComponent(discountInfoPanel);
        return layout;
    }

    private Component generateEditablePanel() {
        VerticalLayout horizontalLayout = new VerticalLayout();
        Discount editedDiscount = new Discount(selectedDiscount);

        TextArea nameLabe = new TextArea();
        nameLabe.setCaption(Words.TXT_DISCOUNT_NAME);
        if (editedDiscount.name != null) {
            nameLabe.setValue(editedDiscount.name);
        }

        TextArea descriptionPanel = new TextArea();
        descriptionPanel.setCaption(Words.TXT_DESCRIPTION);
        if (editedDiscount.description != null) {
            descriptionPanel.setValue(editedDiscount.description);
        }

        CheckBox isMoneyDiscount = new CheckBox(Words.TXT_IS_MONEY_DISCOUNT);
        if (editedDiscount.discount_precentage == null) {
            isMoneyDiscount.setValue(true);
        } else {
            isMoneyDiscount.setValue(false);
        }

        isMoneyDiscount.addValueChangeListener(event -> {
            TextArea discountComonent = generateDiscountComponent(editedDiscount, event.getValue());
            horizontalLayout.replaceComponent(discountEditableArea, discountComonent);
            discountEditableArea = discountComonent;
        });

        discountEditableArea = generateDiscountComponent(editedDiscount,
                isMoneyDiscount.getValue());

        Button saveButton = new Button(Words.TXT_SAVE_DATA);
        saveButton.addClickListener(event -> {
            try {
                isInEditionMode = false;
                editedDiscount.name = nameLabe.getValue();
                editedDiscount.description = descriptionPanel.getValue();
                if (isMoneyDiscount.getValue()) {
                    editedDiscount.discount_precentage = null;
                    editedDiscount.money = Double.valueOf(discountEditableArea.getValue());
                } else {
                    editedDiscount.money = null;
                    editedDiscount.discount_precentage = Double
                            .valueOf(discountEditableArea.getValue());
                }

                NavigatorUI.getDBProvider().updateDiscount(editedDiscount);
                selectedDiscount = NavigatorUI.getDBProvider().getDiscountById(selectedDiscount.id);
                isInEditionMode = false;
                refreshDiscountInfoPanel();
                Notification.show(Words.TXT_CORRECTRLY_SAVED);
            } catch (Exception e) {
                Notification.show(Words.TXT_NOT_SAVED_CHECK_DATA);
            }
        });

        horizontalLayout.addComponent(nameLabe);
        horizontalLayout.addComponent(descriptionPanel);
        horizontalLayout.addComponent(isMoneyDiscount);
        horizontalLayout.addComponent(discountEditableArea);
        horizontalLayout.addComponent(saveButton);
        return horizontalLayout;
    }

    private TextArea generateDiscountComponent(Discount discount, Boolean value) {
        TextArea editableArea = new TextArea();
        if (value) {
            editableArea.setCaption(Words.TXT_DISCOUNT_DISCOUNT_VALUE + " (PLN)");
            if (discount.money != null) {
                editableArea.setValue(discount.money.toString());
            }
        } else {
            editableArea.setCaption(Words.TXT_DISCOUNT_DISCOUNT_VALUE + " (%)");
            if (discount.discount_precentage != null) {
                editableArea.setValue(discount.discount_precentage.toString());
            }
        }
        return editableArea;
    }

    private Component getDiscountInfoPanel() {
        VerticalLayout horizontalLayout = new VerticalLayout();

        Label nameLabe = new Label();
        nameLabe.setCaption(Words.TXT_DISCOUNT_NAME);
        if (selectedDiscount.name != null) {
            nameLabe.setValue(selectedDiscount.name);
        }

        TextArea descriptionPanel = new TextArea();
        descriptionPanel.setReadOnly(true);
        descriptionPanel.setCaption(Words.TXT_DESCRIPTION);
        if (selectedDiscount.description != null) {
            descriptionPanel.setValue(selectedDiscount.description);
        }

        Label discountLabel = new Label();
        discountLabel.setCaption(Words.TXT_DISCOUNT_DISCOUNT_VALUE);
        if (selectedDiscount.discount_precentage == null) {
            if (selectedDiscount.money == null) {
                discountLabel.setValue(Words.TXT_NOT_SET);
            } else {
                discountLabel.setCaption(Words.TXT_DISCOUNT_DISCOUNT_VALUE + " (PLN)");
                discountLabel.setValue(selectedDiscount.money.toString());
            }
        } else {
            discountLabel.setCaption(Words.TXT_DISCOUNT_DISCOUNT_VALUE + " (%)");
            discountLabel.setValue(selectedDiscount.discount_precentage.toString());
        }

        Button editButton = new Button(Words.TXT_EDIT);
        editButton.addClickListener(event -> {
            isInEditionMode = true;
            refreshDiscountInfoPanel();
        });

        horizontalLayout.addComponent(nameLabe);
        horizontalLayout.addComponent(descriptionPanel);
        horizontalLayout.addComponent(discountLabel);
        horizontalLayout.addComponent(editButton);
        return horizontalLayout;
    }

    private Component getDiscountsComponent() {
        VerticalLayout lay = new VerticalLayout();
        NativeSelect<Discount> discountsSelect = new NativeSelect<>(Words.TXT_DISCOUNTS);
        discountsSelect.setEmptySelectionAllowed(false);
        discountsSelect.setItems(NavigatorUI.getDBProvider().getDicsounts());
        discountsSelect.setItemCaptionGenerator(item -> item.name);
        discountsSelect.addValueChangeListener(event -> {
            selectedDiscount = event.getValue();
            isInEditionMode = null;
            refreshDiscountInfoPanel();
        });

        Button addNew = new Button(Words.TXT_ADD);
        addNew.addClickListener(event -> {
            generateAddDiscountPopup();
        });

        lay.addComponent(discountsSelect);
        lay.addComponent(addNew);
        return lay;
    }

    //TODO
    private void generateAddDiscountPopup() {
        com.vaadin.ui.Window subWindow = new Window(Words.TXT_INSERT_NEW_CATEGORY_DATA);
        VerticalLayout subContent = new VerticalLayout();

        Discount discount = new Discount();

        TextArea nameArea = new TextArea(Words.TXT_CATEGORY_NAME, (newEvent) -> {
            discount.name = newEvent.getValue();
        });

        TextArea descriptionArea = new TextArea(Words.TXT_CATEGORY_DESCRIPTION, (newEvent) -> {
            discount.description = newEvent.getValue();
        });

        final TextArea[] discountValueArr = new TextArea[1];
        discountValueArr[0] = generateDiscountComponent(discount, true);

        CheckBox isMoney = new CheckBox(Words.TXT_IS_MONEY_DISCOUNT);
        isMoney.setValue(true);

        isMoney.addValueChangeListener(event -> {
            TextArea newText = generateDiscountComponent(discount, event.getValue());
            subContent.replaceComponent(discountValueArr[0], newText);
            discountValueArr[0] = newText;
        });

        Button addButton = new Button("Dodaj nową", (newEvent) -> {
            try {
                discount.name = nameArea.getValue();
                discount.description = descriptionArea.getValue();
                Double doubleVal = Double.valueOf(discountValueArr[0].getValue());
                if (isMoney.getValue()) {
                    discount.money = doubleVal;
                    discount.discount_precentage = null;
                } else {
                    discount.money = null;
                    discount.discount_precentage = doubleVal;
                }
                NavigatorUI.getDBProvider().addDiscount(discount);
                Notification.show(Words.TXT_CORRECTRLY_SAVED, Notification.Type.TRAY_NOTIFICATION);
                selectedDiscount = null;
                isInEditionMode = null;
                refreshView();
                subWindow.close();
            } catch (Exception e) {
                Notification
                        .show(Words.TXT_NOT_SAVED_CHECK_DATA, Notification.Type.TRAY_NOTIFICATION);
            }
        });

        subContent.addComponent(nameArea);
        subContent.addComponent(descriptionArea);
        subContent.addComponent(isMoney);
        subContent.addComponent(discountValueArr[0]);
        subContent.addComponent(addButton);
        subWindow.setContent(subContent);
        subWindow.center();
        getModel().currentUI.addWindow(subWindow);
    }

    private void refreshDiscountInfoPanel() {
        Component newPanel;
        if (selectedDiscount != null) {
            if (isInEditionMode != null && isInEditionMode) {
                newPanel = generateEditablePanel();
            } else {
                newPanel = getDiscountInfoPanel();
            }
        } else {
            newPanel = new HorizontalLayout();
        }

        ((Layout) getContent()).replaceComponent(discountInfoPanel, newPanel);
        discountInfoPanel = newPanel;
    }
}
