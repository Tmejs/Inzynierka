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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pjkurs.domain.Appusers;
import com.pjkurs.domain.Category;
import com.pjkurs.domain.Course;
import com.pjkurs.domain.CourseStatus;
import com.pjkurs.domain.MyCourse;
import com.pjkurs.domain.Training;
import com.pjkurs.usables.EmailsGenerator;
import com.pjkurs.usables.MailObject;
import com.pjkurs.usables.Words;
import com.pjkurs.utils.FilesUitl;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.ConfirmationPopup;
import com.pjkurs.vaadin.views.PopupWithMessage;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.ComponentRenderer;

/**
 * @author Tmejs
 */
@Theme("pjtheme")
public class AdminEditCoursePanel<T extends AdminViewModel> extends MyContainer<T> {

    Course course;

    Component mainPanel;
    private boolean inEditMode = false;
    private List<Training> trainigs = new ArrayList<>();

    public AdminEditCoursePanel(Course course, T model) {
        super(false, model);
        this.course = course;
        this.course.setCategories(
                NavigatorUI.getDBProvider().getCategoiresByCourseId(course.id));
        this.setContent(buildView());
    }

    @Override
    public void refreshView() {
        this.course = NavigatorUI.getDBProvider().getCourse(course.getId());
        this.course.setCategories(
                NavigatorUI.getDBProvider().getCategoiresByCourseId(course.id));
        super.refreshView();
    }

    @Override
    public Component buildView() {
        VerticalLayout layout = new VerticalLayout();

        mainPanel = generateMainPanel();
        layout.addComponent(mainPanel);
        return layout;
    }

    private Component generateMainPanel() {
        if (inEditMode) {
            return generateEditablePanel();
        } else {
            return generateOverwievVew();
        }
    }

    private Component generateOverwievVew() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        Component courseDataPane = generateCourseDataPanel();
        Component courseManagePane = generateCourseManagePane();

        layout.addComponent(courseDataPane);
        layout.addComponent(courseManagePane);

        return layout;
    }

    private Component generateCourseManagePane() {
        VerticalLayout lay = new VerticalLayout();
        lay.setSizeFull();
        lay.addComponentsAndExpand(generateTrainigsOverViewPanel());
        lay.addComponentsAndExpand(generateCourseActualDetailsPane());

        return lay;
    }

    private Component generateCourseActualDetailsPane() {
        VerticalLayout layout = new VerticalLayout();

        Label actualData = new Label();
        actualData.setCaption(Words.TXT_ACTUAL_DATA);

        Long participantsNum = course.paricipants;
        Label participants = new Label();
        participants.setCaption(Words.TXT_COURSE_PARTICIPANTS);
        Label sumOfMoney = new Label();
        sumOfMoney.setCaption(Words.TXT_COURSE_SUM_OF_MONEY);
        if (participantsNum != null) {
            participants.setValue(participantsNum.toString());
            if (course.getPrice() != null) {
                String valToPres;
                Double value = course.paricipants * course.getPrice();
                valToPres = String.valueOf(value);
                Integer val = calculateCourseDiscount(course);
                if (val != null) {
                    valToPres =
                            String.valueOf(value.intValue() - val) + "  (" + value + " - " + val +
                                    ")";
                }
                sumOfMoney.setValue(valToPres);
            }
        }

        layout.addComponent(actualData);
        layout.addComponent(participants);
        layout.addComponent(getClientsPanel());
        layout.addComponent(sumOfMoney);
        if (participantsNum > 0) {
            layout.addComponent(generateManagementCourseButtons());
        }

        return layout;
    }

    private Integer calculateCourseDiscount(Course course) {
        Integer discount =
                NavigatorUI.getDBProvider().getCourseParticipants(course.id).stream()
                        .mapToInt(a -> {
                            List<MyCourse> courses = NavigatorUI.getDBProvider()
                                    .getMyCourses(a.getEmail());
                            MyCourse myc = courses.stream().filter(c -> c.id == course.id)
                                    .findFirst().get();
                            if (myc.discount != null) {
                                return myc.discount;
                            } else {
                                return 0;
                            }
                        }).sum();

        if (discount == null || discount == 0) {
            return null;
        }
        return discount;
    }

    private Component getClientsPanel() {
        List<Appusers> clients = NavigatorUI.getDBProvider().getCourseParticipants(course.id);

        Grid<Appusers> clientsGrid = new Grid<>(Words.TXT_CLIENTS_IN_TRAINING);
        clientsGrid.setItems(clients);
        clientsGrid.addColumn(Appusers::getName).setCaption(Words.TXT_NAME);
        clientsGrid.addColumn(Appusers::getSurname).setCaption(Words.TXT_SURRNAME);
        clientsGrid.addColumn(Appusers::getEmail).setCaption(Words.TXT_EMAIL);
        clientsGrid.addColumn(Appusers::getContact_number).setCaption(Words.TXT_PHONE_CONTACT);
        clientsGrid.addColumn(p -> {
            List<MyCourse> courses = NavigatorUI.getDBProvider().getMyCourses(p.getEmail());
            MyCourse myc = courses.stream().filter(c -> c.id == course.id).findFirst().get();
            if (myc.discount == null) {
                Button button = new Button(Words.TXT_SET_DISCOUNT);
                button.addClickListener(e -> {showAddDiscountWindow(p, course);});
                return button;
            }
            return new Label(myc.discount + "PLN");
        }, new ComponentRenderer()).setCaption(Words.TXT_DISCOUNT);

        clientsGrid.addColumn(p -> new Button(Words.TXT_DELETE, (event) -> {
            ConfirmationPopup.showPopup(getModel().getUi(),
                    Words.TXT_DELETE_CLIENT_FROM_COURSE_TEXT, new Runnable() {
                        @Override
                        public void run() {
                            NavigatorUI.getDBProvider().deleteCientFromCourse(p, course);
                            refreshView();
                        }
                    });
        }), new ComponentRenderer());

        clientsGrid.setSizeFull();
        return clientsGrid;
    }

    private void showAddDiscountWindow(Appusers p, Course course) {
        Window subWindow = new Window(Words.TXT_SET_DISCOUNT);
        VerticalLayout subContent = new VerticalLayout();

        TextArea value = new TextArea(Words.TXT_DISCOUNT);
        TextArea userName = new TextArea(Words.TXT_USER);
        userName.setEnabled(false);
        userName.setValue(p.getEmail());

        Button addButton = new Button(Words.TXT_SAVE_DATA, (newEvent) -> {
            if (value.getValue() != null) {
                Integer intValue = null;
                try {
                    intValue = Integer.valueOf(value.getValue());
                } catch (Exception e) {
                    Notification.show(Words.TXT_CORRECT_VALUE, Notification.Type.TRAY_NOTIFICATION);
                }
                NavigatorUI.getDBProvider().setDiscount(p, course, intValue);
                refreshView();
                subWindow.close();
            } else {
                Notification.show(Words.TXT_FILL_ALL_FIELDS, Notification.Type.TRAY_NOTIFICATION);
            }
        });

        subContent.addComponent(userName);
        subContent.addComponent(value);
        subContent.addComponent(addButton);
        subWindow.setContent(subContent);
        subWindow.center();
        getModel().currentUI.addWindow(subWindow);
    }

    private Component generateTrainigsOverViewPanel() {

        trainigs.clear();
        trainigs.addAll(NavigatorUI.getDBProvider().getTrainingsForCourse(course));
        if (!trainigs.isEmpty()) {

            trainigs.sort(Comparator.comparing(Training::getStart_date).reversed());
            VerticalLayout lay = new VerticalLayout();
            lay.setSizeFull();
            Label trainings = new Label();
            trainings.setValue(Words.TXT_ACTIVE_TRAININGS);

            lay.addComponent(trainings);
            lay.addComponent(generateTrainingsPanel(trainigs));
            return lay;
        } else {
            return new Label(Words.TXT_NO_ACTIVE_TRAININGS);
        }
    }

    private Component generateTrainingsPanel(List<Training> trainigs) {
        Panel panel = new Panel();
        panel.setSizeFull();
        panel.setContent(generateTrainingsListPane(trainigs));
        return panel;
    }

    private Component generateTrainingsListPane(List<Training> trainigs) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeUndefined();
        trainigs.forEach(t -> layout.addComponent(getTrainingPanel(t)));

        return layout;
    }

    private Component getTrainingPanel(Training training) {
        VerticalLayout lay = new VerticalLayout();

        Label statusLabel = new Label();
        statusLabel.setCaption(Words.TXT_STATUS);
        Label startDateLabel = new Label();
        startDateLabel.setCaption(Words.TXT_START_DATE);
        if (training.getStart_date() != null) {
            startDateLabel.setValue(training.getStart_date().toString());
        }
        if (training.getTrainingStatus() != null) {
            statusLabel.setValue(training.getTrainingStatus().getName());
        }

        lay.addComponent(startDateLabel);
        lay.addComponent(statusLabel);
        lay.addComponent(createTrainingButtonsPane(training));
        return lay;
    }

    private Component createTrainingButtonsPane(Training training) {
        VerticalLayout buttonsLayout = new VerticalLayout();

        Button detailsButton = new Button(Words.TXT_DETAILS);
        detailsButton.addClickListener(event -> {
            getModel().detailedTrainingPanelClicked(training);
        });

        buttonsLayout.addComponent(detailsButton);
        return buttonsLayout;
    }

    private Component generateManagementCourseButtons() {
        VerticalLayout lay = new VerticalLayout();

        Button informAboutStartCourse = new Button(Words.TXT_INFORM_ABOUT_START);
        informAboutStartCourse.addClickListener(event -> {
            generateStartingDatePopup();
        });

        Button startCourse = new Button(Words.TXT_START_TRAINING, event -> {
            ConfirmationPopup.showPopup(getModel().getUi(), Words.TXT_CONFIRM_START_TRAINING,
                    new Runnable() {
                        @Override
                        public void run() {
                            NavigatorUI.getDBProvider().createNewTrainingFromCourse(course);
                            NavigatorUI.getDBProvider().getTrainingsForCourse(course).stream()
                                    .sorted(Comparator.comparing(Training::getStart_date))
                                    .findFirst()
                                    .ifPresent(o -> getModel().detailedTrainingPanelClicked(o));
                        }
                    });
        });

        lay.addComponentsAndExpand(informAboutStartCourse);
        lay.addComponentsAndExpand(startCourse);
        return lay;
    }

    private void generateStartingDatePopup() {
        com.vaadin.ui.Window subWindow = new Window(Words.TXT_SET_DATE);
        VerticalLayout subContent = new VerticalLayout();
        DateField startDate =
                new DateField(Words.TXT_TERM);
        startDate.setDescription("format dd.MM.rr");
        ComboBox<String> startTimeCombo = new ComboBox(Words.TXT_START_TIME);
        startTimeCombo.setItems(generateTimeSet());
        Button inform = new Button(Words.TXT_SEND);
        inform.addClickListener(e -> {
            if (startDate.getValue() != null && startTimeCombo.getValue() != null) {
                LocalDate lDate = startDate.getValue();
                String time = startTimeCombo.getValue();
                Date date = Date.from(lDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                MailObject email = EmailsGenerator.getStartingTrainingMessage(course, date,
                        time);
                List<Appusers> appusers = NavigatorUI.getDBProvider()
                        .getCourseParticipants(course.id);
                PopupWithMessage.showPopup(getModel().getUi(),
                        Words.TXT_EMAIL_SENDED_TO_COURSE_INTERESTS, email);
                appusers.forEach(a -> {
                    MailObject mail = MailObject.copy(email);
                    mail.setReciever(a.getEmail());
                    try {
                        NavigatorUI.getMailSender().sendAsHtml(mail);
                    } catch (Exception exc) {
                        Logger.getGlobal().log(Level.WARNING, exc.getMessage());
                    }

                });
                subWindow.close();
            } else {
                Notification.show(Words.TXT_FILL_ALL_FIELDS);
            }
        });

        startTimeCombo.setEmptySelectionAllowed(false);
        subContent.addComponents(startDate, startTimeCombo, inform);
        subWindow.setContent(subContent);
        subWindow.center();
        getModel().currentUI.addWindow(subWindow);
    }

    private List<String> generateTimeSet() {
        List<String> list = new ArrayList<>();
        for (int i = 6; i < 23; i++) {
            for (int k = 0; k < 60; k = k + 15) {
                String newVal = "";
                if (i / 10 == 0) {
                    newVal = "0";
                }
                newVal += i + ":" + k;
                if (k / 10 == 0) {
                    newVal += "0";
                }
                list.add(newVal);
            }
        }
        return list;
    }

    private Component generateCourseDataPanel() {
        VerticalLayout layout = new VerticalLayout();

        TextArea courseName = new TextArea(Words.TXT_COURSE_NAME);
        courseName.setReadOnly(true);
        courseName.setValue(course.getName());
        if (course.getName() != null) {
            courseName.setValue(course.getName());
        }
        TextArea courseDesc = new TextArea(Words.TXT_COURSE_DESCRIPTION);
        courseDesc.setReadOnly(true);
        if (course.getDescription() != null) {
            courseDesc.setValue(course.getDescription());
        }
        courseDesc.setSizeFull();

        HorizontalLayout descriptionFileLayout = new HorizontalLayout();
        if (course.description_file != null) {
            Label label = new Label();
            label.setCaption(Words.TXT_DETAILED_DESCRIPTION_FILE);
            label.setValue(course.description_file);

            Button downloadFileButton = new Button(VaadinIcons.DOWNLOAD);
            FileResource resource =
                    new FileResource(FilesUitl.getFile(course.id + course.description_file));
            FileDownloader download = new FileDownloader(resource);
            download.extend(downloadFileButton);

            Button deleteButton = new Button(Words.TXT_DELETE_FILE,
                    e -> {
                        ConfirmationPopup
                                .showPopup(getModel().getUi(), Words.TXT_DELTE_DETAILED_DESC
                                        , new Runnable() {
                                            @Override
                                            public void run() {
                                                NavigatorUI.getDBProvider()
                                                        .setFileInCourse(null, course);
                                                course.description_file = null;
                                                refreshView();
                                            }
                                        });
                    });

            descriptionFileLayout.addComponent(label);
            descriptionFileLayout.addComponent(downloadFileButton);
            descriptionFileLayout.addComponent(deleteButton);

        } else {
            Upload upload = new Upload();
            upload.setCaption(Words.TXT_UPLOAD_DETAILED_DESC);
            upload.setReceiver(new Upload.Receiver() {
                @Override
                public OutputStream receiveUpload(String filename, String mimeType) {
                    FileOutputStream fos = null; // Stream to write to
                    if (!(mimeType.contains("pdf") || mimeType.contains("PDF"))) {
                        upload.interruptUpload();
                        return new ByteArrayOutputStream();
                    }
                    try {
                        // Open the file for writing.
                        File file = FilesUitl.createFile(course.id + filename);
                        fos = new FileOutputStream(file);
                        NavigatorUI.getDBProvider().setFileInCourse(filename, course);
                        course.description_file = filename;
                        refreshView();
                    } catch (final java.io.FileNotFoundException e) {
                        return new ByteArrayOutputStream();
                    }
                    return fos; // Return the output stream to write to
                }
            });
            upload.addFailedListener(
                    event -> Notification.show("Akceptowane są tylko pliki w formacie pdf ",
                            Notification.Type.TRAY_NOTIFICATION));
            upload.setImmediateMode(false);
            descriptionFileLayout.addComponent(upload);
        }

        TextArea coursePrice = new TextArea(Words.TXT_PRICE);
        coursePrice.setReadOnly(true);
        if (course.price != null) {
            coursePrice.setValue(course.getPrice().toString());
        }

        TextArea discountCoursePrice = new TextArea(Words.TXT_PRICE_WITHDISCOUNT);
        discountCoursePrice.setReadOnly(true);
        if (course.discountPrice != null) {
            discountCoursePrice.setValue(String.valueOf(course.discountPrice));
        }

        TextArea status = new TextArea(Words.TXT_COURSE_STATUS);
        status.setReadOnly(true);
        status.setValue(course.getCourseStatusAsString());

        Component categories = generateCategoriesView();

        Button editButton = new Button(Words.TXT_EDIT);

        editButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                inEditMode = true;
                refreshView();
            }
        });

        layout.addComponent(courseName);
        layout.addComponent(courseDesc);
        layout.addComponent(descriptionFileLayout);
        layout.addComponent(coursePrice);
        layout.addComponent(discountCoursePrice);
        layout.addComponent(status);
        layout.addComponent(categories);
        layout.addComponent(editButton);
        return layout;
    }

    private Component generateCategoriesView() {
        VerticalLayout lay = new VerticalLayout();
        Label label = new Label(Words.TXT_CATEGORIES);
        lay.addComponent(label);
        lay.addComponent(label);
        course.getCategoryList().forEach(category -> {
            HorizontalLayout horizontalLayout = new
                    HorizontalLayout();

            Label categoriesLabel = new Label();
            categoriesLabel.setCaption(category.name);

            horizontalLayout.addComponent(categoriesLabel);
            lay.addComponent(horizontalLayout);
        });
        return lay;

    }

    private void showSetCategoryPanel() {
        com.vaadin.ui.Window subWindow = new Window(Words.TXT_INSERT_NEW_CATEGORY_DATA);
        VerticalLayout subContent = new VerticalLayout();
        TextArea categoryLabel = new TextArea("Kategorie: ");
        categoryLabel.setReadOnly(true);
        categoryLabel.setVisible(false);
        ListSelect<Category> categorySelect = new ListSelect<Category>(
                Words.TXT_SELECT_SUB_CATEGORY) {
        };
        categorySelect.setItemCaptionGenerator(new ItemCaptionGenerator<Category>() {
            @Override
            public String apply(Category item) {
                return item.getName();
            }
        });

        categorySelect.setItems(NavigatorUI.getDBProvider().getCategories());
        course.getCategoryList().forEach(categorySelect::select);

        Button addButton = new Button(Words.SET_SELECTED_CATEGORIES, (newEvent) -> {
            updateCategoriesInCourse(course, categorySelect.getSelectedItems());
            course.setCategories(new ArrayList<>(categorySelect.getSelectedItems()));
            refreshView();
            subWindow.close();
        });

        subContent.addComponent(categoryLabel);
        subContent.addComponent(categorySelect);
        subContent.addComponent(addButton);
        subWindow.setContent(subContent);
        subWindow.center();
        getModel().currentUI.addWindow(subWindow);
    }

    private void updateCategoriesInCourse(Course course, Set<Category> selectedItems) {
        List<Category> courseCategories =
                NavigatorUI.getDBProvider().getCategoiresByCourseId(course.id);

        selectedItems.forEach(c -> {
            //Add categoreis if selected
            if (!courseCategories.contains(c)) {
                NavigatorUI.getDBProvider().addCategoryToCourse(course.id, c.id);
            }
        });

        //remove deselectedCategories
        courseCategories.forEach(c -> {
            if (!selectedItems.contains(c)) {
                NavigatorUI.getDBProvider().deleteCategoryFromCourse(course.id, c.id);
            }
        });
    }

    private Component generateEditablePanel() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        Course editedCourse = new Course(course);

        TextArea courseName = new TextArea(Words.TXT_COURSE_NAME);
        courseName.setValue(course.getName());
        if (course.getName() != null) {
            courseName.setValue(course.getName());
        }
        TextArea courseDesc = new TextArea(Words.TXT_COURSE_DESCRIPTION);
        if (course.getDescription() != null) {
            courseDesc.setValue(course.getDescription());
        }

        TextArea coursePrice = new TextArea(Words.TXT_PRICE);
        if (course.price != null) {
            coursePrice.setValue(course.getPrice().toString());
        }

        TextArea discountCoursePrice = new TextArea(Words.TXT_PRICE_WITHDISCOUNT);
        if (course.discountPrice != null) {
            discountCoursePrice.setValue(String.valueOf(course.discountPrice));
        }

        Label label = new Label(Words.TXT_CATEGORIES);
        layout.addComponent(label);

        NativeSelect<CourseStatus> statuses = new NativeSelect<>();
        statuses.setCaption(Words.TXT_COURSE_STATUS);
        statuses.setEmptySelectionAllowed(true);
        List<CourseStatus> statusesList = NavigatorUI.getDBProvider().getCourseStatuses();
        statuses.setItems(statusesList);
        if (course.getCourseStatus() != null) {
            statuses.setSelectedItem(
                    statusesList.stream().filter(c -> c.id == course.getStatusId()).findFirst()
                            .get());
        }
        statuses.setItemCaptionGenerator(item -> item.name);
        statuses.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                editedCourse.statusId = event.getValue().id;
            }
        });

        // Component categoriesEditableComponent = generateCategoriesEditableComponent();

        Button addCategoriesButton = new Button(Words.TXT_ADD_CATEGORY);

        addCategoriesButton.addClickListener((event) -> {
            showSetCategoryPanel();
        });

        Button editButton = new Button(Words.TXT_SAVE_DATA);

        editButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                try {
                    editedCourse.name = courseName.getValue();
                    editedCourse.description = courseDesc.getValue();
                    if (coursePrice.getValue() != null && !coursePrice.getValue().isEmpty()) {
                        editedCourse.price = Double.valueOf(coursePrice.getValue());
                    } else {
                        editedCourse.price = 0d;
                    }
                    if (discountCoursePrice.getValue() != null && !discountCoursePrice.getValue()
                            .isEmpty()) {
                        editedCourse.discountPrice = Double.valueOf(discountCoursePrice.getValue());
                    }
                    inEditMode = false;
                    NavigatorUI.getDBProvider().updateCourse(editedCourse);
                    Notification.show(Words.TXT_CHANGED_DATA_SAVED,
                            Notification.Type.TRAY_NOTIFICATION);
                    course = NavigatorUI.getDBProvider().getCourse(course.id);
                    refreshView();
                } catch (Exception e) {
                    Notification.show(Words.TXT_NOT_SAVED_CHECK_DATA,
                            Notification.Type.TRAY_NOTIFICATION);
                    Logger.getLogger(getClass().toString()).log(Level.WARNING, "SAVE data", e);
                }
            }
        });

        Button undoButton = new Button(Words.TXT_DISCARD_CHANGES);
        undoButton.addClickListener(event -> {
            inEditMode = false;
            refreshView();
        });

        layout.addComponent(courseName);
        layout.addComponent(courseDesc);
        layout.addComponent(coursePrice);
        layout.addComponent(discountCoursePrice);
        layout.addComponent(statuses);
        layout.addComponent(addCategoriesButton);
        layout.addComponent(editButton);
        layout.addComponent(undoButton);

        return layout;
    }

}
