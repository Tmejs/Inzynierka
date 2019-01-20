package com.pjkurs.vaadin.ui.containers.admin;

import java.util.ArrayList;
import java.util.List;

import com.pjkurs.domain.Appusers;
import com.pjkurs.domain.Discount;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.pjkurs.vaadin.views.system.MyModel;
import com.vaadin.annotations.Theme;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
@Theme("pjtheme")
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
        Grid<AwaitingDiscount> awatingDiscountsGrid = new Grid<>();
        awatingDiscountsGrid.setSizeFull();
        List<Discount> discounts = NavigatorUI.getDBProvider().getAwaitingDiscounts();
        List<AwaitingDiscount> awaitingDiscounts = generateAwatingDiscounts(discounts);
        awatingDiscountsGrid.setItems(awaitingDiscounts);
        awatingDiscountsGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        awatingDiscountsGrid.setColumnReorderingAllowed(true);
        awatingDiscountsGrid.setSizeFull();

        awatingDiscountsGrid.setResponsive(true);
        //Kolumny

        awatingDiscountsGrid.addColumn(AwaitingDiscount::getUserEmail)
                .setCaption(Words.TXT_CLIETN_EMAIL);
        awatingDiscountsGrid.addColumn(AwaitingDiscount::getTrainignCourse)
                .setCaption(Words.TXT_TRAINING_ADD_NAME);

        awatingDiscountsGrid.setColumnResizeMode(ColumnResizeMode.SIMPLE);
        awatingDiscountsGrid.addItemClickListener(event -> discountClickListener(event.getItem()));

        return awatingDiscountsGrid;
    }

    private void discountClickListener(AwaitingDiscount item) {
        com.vaadin.ui.Window subWindow = new Window(Words.TXT_DISCOUNT_CONFIRMATION);
        Appusers user = NavigatorUI.getDBProvider().getUser(item.userEmail);
        VerticalLayout subContent = new VerticalLayout();

        Label userEmailLabel = new Label();
        userEmailLabel.setCaption(Words.TXT_USER);
        userEmailLabel
                .setValue(user.getName() + " " + user.getSurname() + " (" + user.getEmail() +
                        ")");

        TextArea userDescription = new TextArea(Words.TXT_USER_DESCRIPTION);
        userDescription.setReadOnly(true);
        userDescription.setValue(item.discount.getUserDescription());

        CheckBox percentVal = new CheckBox(Words.IS_PERCENTAGE);

        TextArea value = new TextArea(Words.TXT_DISCOUNT_DISCOUNT_VALUE);

        TextArea confirmationDesription = new TextArea(Words.TXT_CONF_DEC_DESCRIPTION);

        Button confirmDiscount = new Button(Words.TXT_CONFIRM, (newEvent) -> {
            if (confirmationDesription.getValue() != null) {
                try {
                    item.discount.is_percentValue = percentVal.getValue();
                    item.discount.value = Integer.valueOf(value.getValue());
                    item.discount.isConfirmed = true;
                    item.discount.grantedDescription = confirmationDesription.getValue();
                    NavigatorUI.getDBProvider().updateDiscount(item.discount);
                    subWindow.close();
                    refreshView();
                    Notification.show(Words.TXT_CORRECTRLY_SAVED);
                } catch (NumberFormatException e) {
                    Notification.show(Words.TXT_CORRECT_VALUE);
                }
            } else {
                Notification.show(Words.TXT_SET_GRANT_DESCRIPTION);
            }
        });

        Button declineDiscount = new Button(Words.TXT_DECLINE, (newEvent) -> {
            if (confirmationDesription.getValue() != null) {
                item.discount.isConfirmed = false;
                item.discount.grantedDescription = confirmationDesription.getValue();
                NavigatorUI.getDBProvider().updateDiscount(item.discount);
                subWindow.close();
                refreshView();
                Notification.show(Words.TXT_CORRECTRLY_SAVED);

            } else {
                Notification.show(Words.TXT_SET_GRANT_DESCRIPTION);
            }
        });

        subContent.addComponent(userEmailLabel);
        subContent.addComponent(userDescription);
        subContent.addComponent(percentVal);
        subContent.addComponent(value);
        subContent.addComponent(confirmationDesription);
        HorizontalLayout horLay = new HorizontalLayout();
        horLay.addComponent(confirmDiscount);
        horLay.addComponent(declineDiscount);
        subContent.addComponent(horLay);
        subWindow.setContent(subContent);
        subWindow.center();
        getModel().currentUI.addWindow(subWindow);

    }

    private List<AwaitingDiscount> generateAwatingDiscounts(List<Discount> discounts) {
        List<AwaitingDiscount> awaitingDiscounts = new ArrayList<>();

        discounts.forEach(d -> {
            AwaitingDiscount awaitingDiscount = new AwaitingDiscount();
            awaitingDiscount.discountId = d.id;
            awaitingDiscount.trainingId = d.trening_id;
            awaitingDiscount.userEmail =
                    NavigatorUI.getDBProvider().getUser(d.appusers_id).getEmail();
            awaitingDiscounts.add(awaitingDiscount);
            awaitingDiscount.trainignCourse =
                    NavigatorUI.getDBProvider().getTraining(d.trening_id).getCourse().name;
            awaitingDiscount.discount = d;
        });
        return awaitingDiscounts;
    }

    private class AwaitingDiscount {
        public Integer discountId;
        public String userEmail;
        public Integer trainingId;
        public String trainignCourse;
        public Discount discount;

        public String getTrainignCourse() {
            return trainignCourse;
        }

        public Integer getDiscountId() {
            return discountId;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public Integer getTrainingId() {
            return trainingId;
        }
    }
}

