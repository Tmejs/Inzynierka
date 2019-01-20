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
package com.pjkurs.vaadin.ui.containers.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;

import com.pjkurs.domain.Course;
import com.pjkurs.domain.MyCourse;
import com.pjkurs.usables.EmailsGenerator;
import com.pjkurs.usables.MailObject;
import com.pjkurs.usables.Words;
import com.pjkurs.utils.FilesUitl;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.PopupWithMessage;
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.pjkurs.vaadin.views.system.MyModel;
import com.vaadin.annotations.Theme;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Tmejs
 */
@Theme("pjtheme")
public class DetailedCoursePanel<T extends MyModel> extends MyContainer<T> {

    Course course;

    public DetailedCoursePanel(T model, Course course) {
        super(false, model);
        this.course = course;
        this.setContent(buildView());
    }

    @Override
    public Component buildView() {
        if (course.description_file != null) {
            HorizontalLayout lay = new HorizontalLayout();
            lay.addComponent(getMainInfoPanel());
            lay.addComponent(detailDesrciptionPanel());
            lay.setSizeFull();
            return lay;
        } else {
            return getMainInfoPanel();
        }
    }

    private Component getMainInfoPanel() {
        VerticalLayout layout = new VerticalLayout();
        TextArea courseName = new TextArea(Words.TXT_COURSE_NAME, this.course.name);
        courseName.setReadOnly(true);
        layout.addComponent(courseName);

        TextArea courseDesc = new TextArea(Words.TXT_COURSE_DESCRIPTION,
                this.course.description);
        courseDesc.setReadOnly(true);
        courseDesc.setSizeFull();
        layout.addComponentsAndExpand(courseDesc);

        if (NavigatorUI.getLoggedUser() != null) {
            List<MyCourse> courses =
                    NavigatorUI.getDBProvider()
                            .getMyCourses(NavigatorUI.getLoggedUser().email);
            Optional<MyCourse> myc = courses.stream().filter(c -> c.id == course.id).findFirst();
            if (myc.isPresent()) {
                if (myc.get().discount != null) {
                    course.price = course.price - myc.get().discount;
                }
            }
        }

        TextArea price = new TextArea(Words.TXT_PRICE);
        price.setValue(String.valueOf(course.getPrice()));
        price.setReadOnly(true);
        layout.addComponent(price);
        //Discount

        if(course.discountPrice!=null) {
            TextArea discountPrice = new TextArea(Words.TXT_PRICE_WITHDISCOUNT);
            discountPrice.setReadOnly(true);
            discountPrice.setValue(String.valueOf(course.discountPrice));
            layout.addComponent(discountPrice);
        }

        Label newLabel = new Label(course.paricipants.toString());
        newLabel.setCaption(Words.TXT_COURSE_PARTICIPANTS);
        layout.addComponent(newLabel);

        //Sprawdzenie czy już zapisany
        if (NavigatorUI.getLoggedUser() != null) {
            if (!NavigatorUI.getDBProvider()
                    .isUserSignedToCourse(NavigatorUI.getLoggedUser().email, course.id)) {
                layout.addComponent(new Button(Words.TXT_SIGN_TO_COURSE, (event) -> {
                    Logger.getGlobal().log(Level.SEVERE, "TXT_SIGN_TO_COURSE:");
                    if (NavigatorUI.getDBProvider()
                            .addClientToCourse(NavigatorUI.getLoggedUser().email, course)) {
                        MailObject email =
                                EmailsGenerator.getAddedToCourseMessage(NavigatorUI.getLoggedUser(),course);
                        try {
                            NavigatorUI.getMailSender().sendAsHtml(email);
                            PopupWithMessage.showPopup(getModel().currentUI,
                                    Words.TXT_EMAIL_SENDED_TO_YOUR_MAIL, email);
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                        Notification.show("Poprawnie zapisano do kursu");
                        ((MainViewModel) getModel()).getView()
                                .setDetailedCourseAsMainPanel(course.id);
                    } else {
                        Notification.show("Nie można zapisać do kursu");
                    }
                }));

            } else {
                Button undoCoursSign = new Button(Words.TXT_UNDO_SIGN_TO_COURS);

                undoCoursSign.addClickListener(event -> {
                    NavigatorUI.getDBProvider()
                            .deleteCientFromCourse(NavigatorUI.getLoggedUser(), course);
                    ((MainViewModel) getModel()).myCoursesButtonClicked(null);
                    Notification.show(Words.TXT_CORRECTLY_UNSGNED);
                });
                layout.addComponent(undoCoursSign);
            }
        } else {
            layout.addComponent(new Label("Zaloguj aby zapisać się do kursu"));
        }
        return layout;
    }

    public Component detailDesrciptionPanel() {
        VerticalLayout lay = new VerticalLayout();
        StreamResource resource = new StreamResource(new StreamResource.StreamSource() {
            @Override
            public InputStream getStream() {
                try {
                    return new FileInputStream(
                            FilesUitl.getFile(course.id + course.description_file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }, "");
        resource.setMIMEType("application/pdf");

        BrowserFrame e = new BrowserFrame(Words.TXT_COURSE_DESCRIPTION, resource);
        e.setWidth("100%");
        e.setHeight(800, Unit.PIXELS);
        lay.addComponentsAndExpand(e);
        lay.setWidth("100%");
        lay.setHeight(800, Unit.PIXELS);
        return lay;
    }
}
