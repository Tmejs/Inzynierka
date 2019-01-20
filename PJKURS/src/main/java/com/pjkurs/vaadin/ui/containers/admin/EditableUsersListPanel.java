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
package com.pjkurs.vaadin.ui.containers.admin;

import com.pjkurs.domain.Appusers;
import com.pjkurs.domain.GraduatedCourse;
import com.pjkurs.usables.Words;
import com.pjkurs.utils.FilesUitl;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.ConfirmationPopup;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.vaadin.annotations.Theme;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValueProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.renderers.ComponentRenderer;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Tmejs
 */
@Theme("pjtheme")
public class EditableUsersListPanel<T extends AdminViewModel> extends MyContainer<T> {

    public EditableUsersListPanel(T model) {
        super(model);
    }

    private Component usersPanel;
    private Appusers selectedUser;
    private String filter;
    private List<Appusers> usersList;
    List<Appusers> filteredUserList;

    @Override
    public Component buildView() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        usersList = NavigatorUI.getDBProvider().getUsers();
        if (selectedUser == null) {
            usersPanel = generateOverwieComponent();
        } else {
            usersPanel = generateEditablePanel();
        }
        layout.addComponent(usersPanel);
        return layout;
    }

    private void refreshPanel() {
        Component newPanel;
        usersList = NavigatorUI.getDBProvider().getUsers();
        if (selectedUser == null) {
            newPanel = generateOverwieComponent();
        } else {
            newPanel = generateEditablePanel();
        }
        ((VerticalLayout) this.getContent()).replaceComponent(usersPanel, newPanel);
        usersPanel = newPanel;
    }

    private Component generateEditablePanel() {
        VerticalLayout layout = new VerticalLayout();
        Appusers editedUser = new Appusers(selectedUser);

        CheckBox isActive = new CheckBox(Words.TXT_IS_ACTIVE);
        if (selectedUser.isActive != null) {
            isActive.setValue(selectedUser.isActive);
        }
        TextArea emailArea = new TextArea(Words.TXT_EMAIL, selectedUser.getEmail());
        emailArea.setReadOnly(true);
        TextArea nameArea = new TextArea(Words.TXT_NAME);
        if (selectedUser.getName() != null) {
            nameArea.setValue(selectedUser.getName());
        }
        TextArea surnameArea = new TextArea(Words.TXT_SURRNAME);
        if (selectedUser.getSurname() != null) {
            surnameArea.setValue(selectedUser.getSurname());
        }
        com.vaadin.ui.DateField birth_dateDatePicker =
                new DateField(Words.TXT_BIRTH_DATE);
        if (selectedUser.getBirth_date() != null) {
            birth_dateDatePicker.setValue(selectedUser.getBirth_date().toLocalDate());
        }
        TextArea contactNumberArea = new TextArea(Words.TXT_PHONE_CONTACT);
        if (selectedUser.getContact_number() != null) {
            contactNumberArea.setValue(selectedUser.getContact_number());
        }

        TextArea placeOfBirth = new TextArea(Words.TXT_PLACE_OF_BIRTH);
        if (selectedUser.getPlace_of_birth() != null) {
            placeOfBirth.setValue(selectedUser.getPlace_of_birth());
        }

        NativeSelect<String> typeOfWork = new NativeSelect<>(Words.TXT_CONTRACT_BASE);
        String[] types = {"Etat", "Umowa", "Faktura"};

        typeOfWork.setItems(types);
        typeOfWork.setEmptySelectionAllowed(false);

        CheckBox isTeacher = new CheckBox(Words.TXT_TEACHER_STATUS);
        isTeacher.addValueChangeListener(event -> {
            typeOfWork.setVisible(event.getValue());
            typeOfWork.setSelectedItem(
                    NavigatorUI.getDBProvider().getTeacherBaseContrat(selectedUser));
        });
        isTeacher.setValue(NavigatorUI.getDBProvider().isUserTeacher(selectedUser));
        typeOfWork.setVisible(isTeacher.getValue());
        HorizontalLayout newL = new HorizontalLayout();
        Button saveButton = new Button(Words.TXT_SAVE_DATA);
        saveButton.addClickListener(event -> {
            try {
                editedUser.name = nameArea.getValue();
                editedUser.surname = surnameArea.getValue();
                editedUser.birth_date = birth_dateDatePicker.getValue() == null ? null :
                        Date.valueOf(birth_dateDatePicker.getValue());
                editedUser.contact_number = contactNumberArea.getValue();
                editedUser.place_of_birth = placeOfBirth.getValue();
                editedUser.isActive = isActive.getValue();
                NavigatorUI.getDBProvider().updateAppuser(editedUser);
                NavigatorUI.getDBProvider().setTeacherStatus(editedUser, isTeacher.getValue(),
                        typeOfWork.getValue());
                selectedUser = null;
                refreshPanel();
            } catch (Exception e) {
                Notification.show(Words.TXT_NOT_SAVED_CHECK_DATA);
                Logger.getGlobal().log(Level.SEVERE, "Błąd przy updateAppuser", e);
            }
        });
        Button undoButton = new Button(Words.TXT_DISCARD_CHANGES);
        undoButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                selectedUser = null;
                refreshPanel();
            }
        });
        newL.addComponent(saveButton);
        newL.addComponent(undoButton);

        layout.addComponent(isActive);
        layout.addComponent(emailArea);
        layout.addComponent(nameArea);
        layout.addComponent(surnameArea);
        layout.addComponent(birth_dateDatePicker);
        layout.addComponent(placeOfBirth);
        layout.addComponent(contactNumberArea);
        layout.addComponent(isTeacher);
        layout.addComponent(typeOfWork);
        layout.addComponent(newL);
        return layout;
    }

    private Component generateOverwieComponent() {
        VerticalLayout lay = new VerticalLayout();
        lay.setSizeFull();
        Grid<Appusers> userGrid = new Grid<>();
        CheckBox inActive = new CheckBox(Words.TXT_INACTIVE);
        TextArea filterArea = new TextArea(Words.TXT_FIND,
                new HasValue.ValueChangeListener<String>() {
                    @Override
                    public void valueChange(HasValue.ValueChangeEvent<String> event) {
                        filter = event.getValue();
                        Boolean val = inActive.getValue();
                        if (val != null && val) {
                            filteredUserList = usersList.stream()
                                    .filter(u -> (u.isActive == null || !u.isActive)).collect(
                                            Collectors.toList());
                        }
                        if (filter != null && !filter.isEmpty()) {
                            filteredUserList = filteredUserList.stream()
                                    .filter(appusers -> checkFilter(appusers,
                                            filter)).collect(Collectors.toList());
                        }
                        userGrid.setItems(filteredUserList);
                    }
                });
        if (filter != null) {
            filterArea.setValue(filter);
        }

        inActive.addValueChangeListener(v -> {
            Boolean val = v.getValue();
            if (val != null && val) {
                filteredUserList = filteredUserList.stream()
                        .filter(u -> (u.isActive == null || !u.isActive)).collect(
                                Collectors.toList());
                userGrid.setItems(filteredUserList);
            } else {
                filteredUserList = usersList;
                filter = filterArea.getValue();
                if (filter != null && !filter.isEmpty()) {
                    filteredUserList = filteredUserList.stream()
                            .filter(appusers -> checkFilter(appusers,
                                    filter)).collect(Collectors.toList());
                }
                userGrid.setItems(filteredUserList);

            }
        });
        VerticalLayout filterLay = new VerticalLayout();
        filterLay.addComponents(filterArea, inActive);
        lay.addComponent(filterLay);
        filteredUserList = usersList;
        userGrid.setItems(filteredUserList);
        userGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        userGrid.setColumnReorderingAllowed(true);
        userGrid.setSizeFull();

        userGrid.setResponsive(true);
        //Kolumny

        userGrid.addColumn(Appusers::getEmail).setCaption(Words.TXT_COURSE_NAME);
        userGrid.addColumn(
                c -> c.isActive != null && c.isActive ?
                        Words.TXT_YES :
                        Words.TXT_NO).setCaption(Words.TXT_IS_ACTIVE);
        userGrid.addColumn(Appusers::getName).setCaption(Words.TXT_NAME);
        userGrid.addColumn(Appusers::getSurname).setCaption(Words.TXT_SURRNAME);
        userGrid.addColumn(t -> t.create_date != null ?
                new SimpleDateFormat("YYYY-MM-dd").format(t.create_date) : "")
                .setCaption(Words.TXT_CREATE_DATE);
        userGrid.setColumnResizeMode(ColumnResizeMode.SIMPLE);
        userGrid.addColumn(p -> {
            List<GraduatedCourse> graduatedCourses =
                    NavigatorUI.getDBProvider().getGraduatedCourses(p);
            if (graduatedCourses.size() > 0) {
                return new Button(Words.TXT_ENDED_COURSES, e -> generateEndedCoursesPopup(p));
            }else {
                return new Label(Words.TXT_NO_GRADUATED_COURSES);
            }
        }, new ComponentRenderer());
        userGrid.addColumn(p -> new Button(Words.TXT_EDIT, event -> {
            selectedUser = p;
            refreshPanel();
        }), new ComponentRenderer());
        userGrid.addColumn(p -> new Button(Words.TXT_DELETE, event -> {
            ConfirmationPopup.showPopup(getModel().getUi(), Words.TXT_DELETE_USER_TXT + p.email,
                    new Runnable() {
                        @Override
                        public void run() {
                            NavigatorUI.getDBProvider().deleteUser(p);
                            refreshPanel();
                        }
                    });
        }), new ComponentRenderer());

        lay.addComponentsAndExpand(userGrid);
        return lay;
    }

    private void generateEndedCoursesPopup(Appusers user) {
        Window subWindow = new Window(Words.TXT_GRADUATED_COURSES +"użytkownika "+ user.email);
        VerticalLayout lay = new VerticalLayout();
        Grid<GraduatedCourse> grid = new Grid();
        List<GraduatedCourse> graduatedCourses =
                NavigatorUI.getDBProvider().getGraduatedCourses(user);
        grid.setItems(graduatedCourses);
        grid.addColumn(GraduatedCourse::getName).setCaption(Words.TXT_COURSE_NAME);
        grid.addColumn(GraduatedCourse::getEndDate).setCaption(Words.TXT_END_DATE);
        grid.addColumn(new ValueProvider<GraduatedCourse, Button>() {
            @Override
            public Button apply(GraduatedCourse course) {
                Button fileDownlaod = new Button(VaadinIcons.DOWNLOAD);
                if(course.description_file==null){
                    fileDownlaod.setEnabled(false);
                }
                FileResource resource =
                        new FileResource(FilesUitl.getFile(course.id+course.description_file));
                FileDownloader download = new FileDownloader(resource);
                download.extend(fileDownlaod);
                return fileDownlaod;
            }
        }, new ComponentRenderer()).setCaption(Words.TXT_COURSE_DETAILS);

        grid.addColumn(new ValueProvider<GraduatedCourse, Button>() {
            @Override
            public Button apply(GraduatedCourse course) {
                Button fileDownlaod = new Button(VaadinIcons.DOWNLOAD);
                if(course.certificateFile==null){
                    fileDownlaod.setEnabled(false);
                }
                FileResource resource = new FileResource(FilesUitl.getFile(course.certificateFile));
                FileDownloader download = new FileDownloader(resource);
                download.extend(fileDownlaod);
                return fileDownlaod;
            }
        }, new ComponentRenderer()).setCaption(Words.TXT_GET_CERTIFICATE);
        lay.addComponent(grid);
        subWindow.setContent(lay);
        subWindow.center();
        getModel().currentUI.addWindow(subWindow);
    }

    private boolean checkFilter(Appusers appusers, String filter) {
        if (appusers.getName() != null) {
            if (appusers.getName().contains(filter)) {
                return true;
            }
        }
        if (appusers.getSurname() != null) {
            if (appusers.getSurname().contains(filter)) {
                return true;
            }
        }

        if (appusers.getEmail() != null) {
            if (appusers.getEmail().contains(filter)) {
                return true;
            }
        }
        return false;

    }
}
