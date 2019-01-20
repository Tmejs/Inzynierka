package com.pjkurs.vaadin.ui.containers.admin;

import com.pjkurs.domain.DeaneryUser;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.annotations.Theme;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
@Theme("pjtheme")
public class LoginAdminPanel<T extends AdminViewModel> extends MyContainer<T> {

    public LoginAdminPanel(T model) {
        super(false, model);
        setContent(buildView());
    }

    @Override
    public Component buildView() {
        VerticalLayout layout = new VerticalLayout();

        TextArea email = new TextArea(Words.TXT_LOGIN);
        PasswordField password = new PasswordField(Words.TXT_PASSWORD);
        Button loginButton = new Button(Words.TXT_LOGIN_BUTTON, (e) -> {
            if (email.getValue() != null && password.getValue() != null) {
                DeaneryUser deaneryUser = null;
                if (NavigatorUI.PARAMS.ADMIN_LOGIN.equals(email.getValue())
                        && NavigatorUI.PARAMS.ADMIN_PASSWORD.equals(password.getValue())) {
                    deaneryUser = new DeaneryUser();
                    deaneryUser.admin_grant = true;
                    deaneryUser.email = email.getValue();
                    deaneryUser.password = password.getValue().trim();
                } else  {
                    deaneryUser = NavigatorUI.getDBProvider().getDeaneryUser(email.getValue());
                    if(deaneryUser.password != password.getValue()){
                        deaneryUser=null;
                    }
                }
                if (deaneryUser != null) {
                    NavigatorUI.setLogedAdmin(deaneryUser);
                    getModel().correctlyLoged();
                }else {
                    Notification.show(Words.TXT_INCORECT_DATA);
                }
            } else {
                Notification.show(Words.TXT_FILL_ALL_FIELDS);
            }
        });

        layout.addComponents(email);
        layout.addComponents(password);
        layout.addComponents(loginButton);

        return layout;
    }
}
