package com.pjkurs.vaadin.ui.containers.admin;

import com.pjkurs.domain.DeaneryUser;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.ConfirmationPopup;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.annotations.Theme;
import com.vaadin.data.ValueProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ComponentRenderer;
@Theme("pjtheme")
public class DeansEmployesPanel<T extends AdminViewModel> extends MyContainer<T> {

    public DeansEmployesPanel(T model) {
        super(model);
    }

    @Override
    public Component buildView() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        Button addNewButton = new Button(Words.TXT_ADD_NEW_DEANEMPLYE);
        addNewButton.addClickListener(event -> showAddDeanEmployeePopup(null));

        layout.addComponentsAndExpand(generateDeansPanel());
        layout.addComponentsAndExpand(addNewButton);
        return layout;
    }

    private void showAddDeanEmployeePopup(DeaneryUser deaneryUser) {
        com.vaadin.ui.Window subWindow = new Window(Words.TXT_INSERT_NEW_DEAN_EMPLOYEE);
        VerticalLayout subContent = new VerticalLayout();

        TextArea nameArea = new TextArea(Words.TXT_LOGIN);
        TextArea password = new TextArea(Words.TXT_PASSWORD);
        CheckBox adminGrant = new CheckBox(Words.TXT_ADMIN_GRANT);

        Button addButton = new Button(Words.TXT_ADD, (newEvent) -> {
            if (nameArea.getValue() != null && password.getValue() != null) {
                if (deaneryUser != null) {
                    deaneryUser.setEmail(nameArea.getValue());
                    deaneryUser.setPassword(password.getValue());
                    deaneryUser.setAdmin_grant(adminGrant.getValue());
                    NavigatorUI.getDBProvider().updateDeaneryUser(deaneryUser);
                } else {
                    if(nameArea.getValue()!=null && nameArea.getValue().trim().length()>0 && password.getValue()!=null && password.getValue().trim().length()>0){
                        NavigatorUI.getDBProvider().addNewDeaneryEployee(nameArea.getValue(),
                                password.getValue(), adminGrant.getValue());
                        Notification
                                .show(Words.TXT_CORRECTRLY_SAVED, Notification.Type.TRAY_NOTIFICATION);
                        refreshView();
                        subWindow.close();
                    } else {
                        Notification
                                .show(Words.TXT_FILL_ALL_FIELDS, Notification.Type.TRAY_NOTIFICATION);
                    }
                }
            } else {
                Notification.show(Words.TXT_FILL_ALL_FIELDS, Notification.Type.TRAY_NOTIFICATION);
            }
        });

        if(deaneryUser!=null){
            nameArea.setValue(deaneryUser.getEmail());
            password.setValue(deaneryUser.password);
            adminGrant.setValue(deaneryUser.admin_grant);
            addButton.setCaption(Words.TXT_SAVE_DATA);
            subWindow.setCaption(Words.TXT_MODIFY);
        }

        subContent.addComponent(nameArea);
        subContent.addComponent(password);
        subContent.addComponent(adminGrant);
        subContent.addComponent(addButton);
        subWindow.setContent(subContent);
        subWindow.center();
        getModel().currentUI.addWindow(subWindow);
    }

    private Component generateDeansPanel() {
        Grid<DeaneryUser> grid = new Grid<>();
        grid.setSizeFull();

        grid.setItems(NavigatorUI.getDBProvider().getDeaneryUsers());

        grid.addColumn(DeaneryUser::getEmail).setCaption(Words.TXT_LOGIN);
        grid.addColumn(new ValueProvider<DeaneryUser, Object>() {
            @Override
            public Object apply(DeaneryUser deaneryUser) {
                if (deaneryUser.admin_grant) {
                    return "TAK";
                } else {
                    return "NIE";
                }
            }
        }).setCaption(Words.TXT_ADMIN_GRANT);
        grid.addColumn(p -> new Button(Words.TXT_MODIFY, e -> {
            showAddDeanEmployeePopup(p);
            refreshView();
        }),new ComponentRenderer());

        grid.addColumn(p -> new Button(Words.TXT_DELETE, e -> {
            ConfirmationPopup.showPopup(getModel().getUi(),
                    Words.TXT_CONFIRM_DELETE_DEANERY_USER, new Runnable() {
                        @Override
                        public void run() {
                            NavigatorUI.getDBProvider().deleteDeaneryUser(p);
                            refreshView();
                            Notification.show(Words.TXT_CORRECTLY_DELETED);
                        }
                    });
        }), new ComponentRenderer());

        return grid;
    }

    private void setAdminGrant(DeaneryUser p, Boolean value) {
        if (p.email != NavigatorUI.PARAMS.ADMIN_LOGIN) {
            NavigatorUI.getDBProvider().setAdminGrant(p, value);
        }
    }

}
