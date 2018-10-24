package com.pjkurs.vaadin.ui.containers.admin;

import java.util.List;

import com.pjkurs.domain.Appusers;
import com.pjkurs.domain.GrantedDiscount;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.pjkurs.vaadin.views.system.MyModel;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class AwaitingDiscounstsPanel<T extends MyModel> extends MyContainer<T> {

    public AwaitingDiscounstsPanel(T model) {
        super(false, model);
        refreshView();
    }

    @Override
    public Component buildView() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.addComponent(generateAwaitingDiscountsPanel());

        return layout;
    }

    private Component generateAwaitingDiscountsPanel() {
        Grid<GrantedDiscount> awatingDiscountsGrid = new Grid<>();
        awatingDiscountsGrid.setSizeFull();
        List<GrantedDiscount> discounts = NavigatorUI.getDBProvider().getAwaitingDiscounts();
        awatingDiscountsGrid.setItems(discounts);
        awatingDiscountsGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        awatingDiscountsGrid.setColumnReorderingAllowed(true);
        awatingDiscountsGrid.setSizeFull();

        awatingDiscountsGrid.setResponsive(true);
        //Kolumny

        awatingDiscountsGrid.addColumn(GrantedDiscount::getUserEmail)
                .setCaption(Words.TXT_CLIETN_EMAIL);
        awatingDiscountsGrid.addColumn(GrantedDiscount::getName)
                .setCaption(Words.TXT_DISCOUNT_DISCOUNT_VALUE);

        awatingDiscountsGrid.setColumnResizeMode(ColumnResizeMode.SIMPLE);
        awatingDiscountsGrid.addItemClickListener(event -> {
            GrantedDiscount grantedDisc = event.getItem();
            Appusers user = NavigatorUI.getDBProvider().getUser(grantedDisc.email);
            com.vaadin.ui.Window subWindow = new Window(Words.TXT_DISCOUNT_CONFIRMATION);
            VerticalLayout subContent = new VerticalLayout();

            Label userEmailLabel = new Label();
            userEmailLabel.setCaption(Words.TXT_USER);
            userEmailLabel
                    .setValue(user.getName() + " " + user.getSurname() + " (" + user.getEmail() +
                            ")");

            Label discountName = new Label();
            discountName.setCaption(Words.TXT_DISCOUNT);
            discountName.setValue(grantedDisc.getName());

            TextArea discountDescription = new TextArea(Words.TXT_DISCOUNT_DESCRIPITON);
            discountDescription.setReadOnly(true);
            if (grantedDisc.getDescription() != null) {
                discountDescription.setValue(grantedDisc.getDescription());
            }

            TextArea userDescription = new TextArea(Words.TXT_USER_DESCRIPTION);
            userDescription.setReadOnly(true);
            userDescription.setValue(grantedDisc.getUserDescription());

            TextArea confirmationDesription = new TextArea(Words.TXT_CONF_DEC_DESCRIPTION);

            Button confirmDiscount = new Button(Words.TXT_CONFIRM, (newEvent) -> {
                if (confirmationDesription.getValue() != null) {
                    grantedDisc.isConfirmed = true;
                    grantedDisc.grantedDescription = confirmationDesription.getValue();
                    if(NavigatorUI.getDBProvider().updateUserDiscount(grantedDisc)){
                        subWindow.close();
                        refreshView();
                        Notification.show(Words.TXT_CORRECTRLY_SAVED);
                    }else{
                        Notification.show(Words.TXT_SOMETHIN_WENT_WRONG);
                    }
                }else{
                    Notification.show(Words.TXT_SET_GRANT_DESCRIPTION);
                }
            });

            Button declineDiscount = new Button(Words.TXT_DECLINE, (newEvent) -> {
                if (confirmationDesription.getValue() != null) {
                    grantedDisc.isConfirmed = false;
                    grantedDisc.grantedDescription = confirmationDesription.getValue();
                    if(NavigatorUI.getDBProvider().updateUserDiscount(grantedDisc)){
                        subWindow.close();
                        refreshView();
                        Notification.show(Words.TXT_CORRECTRLY_SAVED);
                    }else{
                        Notification.show(Words.TXT_SOMETHIN_WENT_WRONG);
                    }
                }else{
                    Notification.show(Words.TXT_SET_GRANT_DESCRIPTION);
                }
            });

            subContent.addComponent(userEmailLabel);
            subContent.addComponent(userDescription);
            subContent.addComponent(confirmationDesription);
            HorizontalLayout horLay = new HorizontalLayout();
            horLay.addComponent(confirmDiscount);
            horLay.addComponent(declineDiscount);
            subContent.addComponent(horLay);
            subWindow.setContent(subContent);
            subWindow.center();
            getModel().currentUI.addWindow(subWindow);
        });

        return awatingDiscountsGrid;
    }
}

