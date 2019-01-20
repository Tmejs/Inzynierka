package com.pjkurs.vaadin.views;

import com.pjkurs.usables.MailObject;
import com.pjkurs.usables.Words;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class ConfirmationPopup {

    public static void showPopup(UI ui, String message, Runnable runnable) {
        com.vaadin.ui.Window subWindow = new Window(Words.TXT_CONFIRMATION_WINDOW);
        VerticalLayout subContent = new VerticalLayout();

        TextArea textArea = new TextArea();
        textArea.setSizeFull();
        textArea.setReadOnly(true);
        textArea.setValue(message);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Button cancelButton = new Button(Words.TXT_CANCEL, event -> {
            subWindow.close();
        });

        Button confirm = new Button(Words.TXT_CONFIRM, e->{
           runnable.run();
           subWindow.close();
        });

        horizontalLayout.addComponents(confirm, cancelButton);
        horizontalLayout.setSizeFull();
        subContent.addComponent(textArea);
        subContent.addComponent(horizontalLayout);
        subWindow.setContent(subContent);
        subWindow.center();
        ui.addWindow(subWindow);
    }

}
