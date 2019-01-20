package com.pjkurs.vaadin.views;

import com.pjkurs.usables.MailObject;
import com.pjkurs.usables.Words;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class PopupWithMessage {

    public static void showPopup(UI ui, String title, MailObject mail) {
        com.vaadin.ui.Window subWindow = new Window(Words.TXT_NEW_EMAIL);
        VerticalLayout subContent = new VerticalLayout();

        Label titleLabel = new Label(title);

        TextArea textArea = new TextArea();
        textArea.setSizeFull();
        textArea.setReadOnly(true);
        textArea.setCaption(Words.TXT_MAIL_CONTENT);
        textArea.setValue(removeHtml(mail.getMessageBody()));

        subContent.addComponents(titleLabel, textArea);
        subWindow.setContent(subContent);
        subWindow.center();
        ui.addWindow(subWindow);
    }

    private static String removeHtml(String message) {
        String newMessage = message;
        newMessage = newMessage.replaceAll("<html>","");
        newMessage = newMessage.replaceAll("<HTML>","");
        newMessage = newMessage.replaceAll("</HTML>","");
        newMessage = newMessage.replaceAll("</html>","");
        newMessage = newMessage.replaceAll("<br>","\n");
        return newMessage;
    }

}
