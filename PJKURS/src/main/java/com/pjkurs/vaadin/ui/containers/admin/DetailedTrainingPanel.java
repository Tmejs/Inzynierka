package com.pjkurs.vaadin.ui.containers.admin;

import static java.lang.Math.toIntExact;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.mail.MessagingException;

import com.pjkurs.domain.Appusers;
import com.pjkurs.domain.CalendarEvent;
import com.pjkurs.domain.Client;
import com.pjkurs.domain.Course;
import com.pjkurs.domain.Teachers;
import com.pjkurs.domain.Training;
import com.pjkurs.domain.TrainingFile;
import com.pjkurs.usables.EmailsGenerator;
import com.pjkurs.usables.MailObject;
import com.pjkurs.usables.Words;
import com.pjkurs.utils.FilesUitl;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.ConfirmationPopup;
import com.pjkurs.vaadin.views.PopupWithMessage;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.pjkurs.vaadin.views.system.MyModel;
import com.vaadin.annotations.Theme;
import com.vaadin.data.ValueProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.renderers.ComponentRenderer;
import org.vaadin.addon.calendar.Calendar;
import org.vaadin.addon.calendar.item.BasicItem;
import org.vaadin.addon.calendar.item.BasicItemProvider;

@Theme("pjtheme")
public class DetailedTrainingPanel extends MyContainer<MyModel> {

    final Training training;
    private final Boolean isInEditableMode;
    private final Boolean isOpenedByTeacher;
    private Teachers teacher;

    @Override
    public Component buildView() {
        VerticalLayout horizontalLayout = new VerticalLayout();
        if (getModel() instanceof AdminViewModel) {
            Button endTrainingButton = new Button(Words.TXT_END_TRAINING,
                    e -> generatEndTrainignPopup());
            horizontalLayout.addComponent(endTrainingButton);
        }
        horizontalLayout.addComponentsAndExpand(getTrainingInfo());
        if (isOpenedByTeacher) {
            horizontalLayout.addComponent(generateTeacherTime());
        }
        horizontalLayout.addComponentsAndExpand(getCalendarForTraining());
        horizontalLayout.addComponentsAndExpand(generateFileOverview());
        horizontalLayout.addComponentsAndExpand(getTrainingDetailedData());

        return horizontalLayout;
    }

    private void generatEndTrainignPopup() {
        Window subWindow = new Window(Words.TXT_TRAINING_ENDING);
        VerticalLayout subContent = new VerticalLayout();
        List<Client> clients = NavigatorUI.getDBProvider().getClientsForTraining(training);

        Grid<Client> clientsGrid = new Grid<>(Words.TXT_CLIENTS_IN_TRAINING);
        clientsGrid.setItems(clients);
        Layout graduationLayout = new VerticalLayout();
        graduationLayout.setVisible(false);
        Button endTrainignButton = new Button(Words.TXT_END_TRAINING);

        Map<Integer, Boolean> isGraduated = new HashMap<>();
        Map<Integer, String> fileMap = new HashMap<>();

        endTrainignButton.addClickListener(e -> {
            if (isGraduated.entrySet().size() > 0 &&
                    !isGraduated.entrySet().stream()
                            .filter(p -> p.getValue())
                            .allMatch(p -> fileMap.get(p.getKey()) != null)) {
                Notification.show(Words.TXT_ADD_CERTIFICATE_FOR_ALL_GRADUATED_USERS);
                return;
            }
            endTraining(clients, isGraduated, fileMap);
            subWindow.close();
            ((AdminViewModel)getModel()).statistickMenuClicked();
        });

        clientsGrid.addColumn(Client::getName).setCaption(Words.TXT_NAME);
        clientsGrid.addColumn(Client::getSurname).setCaption(Words.TXT_SURRNAME);
        clientsGrid.addColumn(Client::getEmail).setCaption(Words.TXT_EMAIL);
        clientsGrid.addColumn(
                c -> isGraduated.get(c.getId()) != null && isGraduated.get(c.getId()) ?
                        Words.TXT_YES :
                        Words.TXT_NO).setCaption(Words.TXT_IS_CLIENT_GRAD);
        clientsGrid.addColumn(
                c -> isGraduated.get(c.getId()) != null && fileMap.get(c.getId()) != null ?
                        Words.TXT_YES :
                        Words.TXT_NO).setCaption(Words.TXT_IS_CERTIFICATE_ADDED);

        clientsGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        clientsGrid.addSelectionListener(
                t -> updateUserGraduationPanel(t.getFirstSelectedItem(), isGraduated, fileMap,
                        graduationLayout));


        subContent.addComponents(new HorizontalLayout(clientsGrid, graduationLayout));
        subContent.addComponent(endTrainignButton);
        subWindow.setContent(subContent);
        subWindow.center();
        getModel().currentUI.addWindow(subWindow);
    }

    private void endTraining(List<Client> clients, Map<Integer, Boolean> isGraduated,
            Map<Integer, String> fileMap) {
        NavigatorUI.getDBProvider().endTraining(training);
        clients.forEach(c -> {
            Boolean clientGraduated = isGraduated.get(c.getId());
            String certificate = fileMap.get(c.getId());
            if (clientGraduated!=null && clientGraduated) {
                NavigatorUI.getDBProvider().addGraduation(c, training, certificate);
                Course course = NavigatorUI.getDBProvider().getCourse(training.course_id);
                MailObject m = EmailsGenerator.getTrainingEndingMessage(c, course);
                try {
                    NavigatorUI.getMailSender().sendAsHtml(m);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });
        Appusers appuser =new Appusers();
        appuser.name = "IMIE_UŻYTKOWNIKA";
        Course course = NavigatorUI.getDBProvider().getCourse(training.course_id);
        MailObject m = EmailsGenerator.getTrainingEndingMessage(appuser, course);
        PopupWithMessage.showPopup(getModel().currentUI,
                Words.TXT_EMAIL_SENDED_TO_TRAINING_PARTICIPANTS, m);
    }

    private void updateUserGraduationPanel(Optional<Client> t, Map<Integer, Boolean> isGraduated,
            Map<Integer, String> fileMap, Layout graduationLayout) {
        if(!t.isPresent()) {
            graduationLayout.setVisible(false);
            return;
        }
        Client client = t.get();
        graduationLayout.setVisible(client != null);

        graduationLayout.removeAllComponents();

        CheckBox isGraduating = new CheckBox();
        Upload uploadCertificate = new Upload();
        Button downloadCertificate = new Button(Words.TXT_DOWNLOAD_CERTIFICATE);
        Button deleteCertificate = new Button(Words.TXT_DELETE_CERTIFICATE);
        HorizontalLayout fileLayout = new HorizontalLayout();

        //Checkbox
        isGraduating.setCaption(Words.TXT_IS_GRADUATING);
        isGraduating.addValueChangeListener(event -> {
            isGraduated.put(client.getId(),
                    event.getValue());

            if (event.getValue()) {
                if (fileMap.get(client.getId()) != null) {
                    fileLayout.setVisible(true);
                    uploadCertificate.setVisible(false);
                } else {
                    fileLayout.setVisible(false);
                    uploadCertificate.setVisible(true);
                }
            } else {
                fileLayout.setVisible(false);
                uploadCertificate.setVisible(false);
            }
        });

        Boolean isClientGraduated = isGraduated.get(client.id);
        if (isClientGraduated != null) {
            isGraduating.setValue(isClientGraduated);
            if (fileMap.get(client.getId()) != null) {
                fileLayout.setVisible(true);
                uploadCertificate.setVisible(false);
            } else {
                fileLayout.setVisible(false);
                uploadCertificate.setVisible(true);
            }
        } else {
            isGraduating.setValue(false);
            fileLayout.setVisible(false);
            uploadCertificate.setVisible(false);
        }

        // uplaodFile
        uploadCertificate.setCaption(Words.TXT_ADD_CERTIFCATE);
        uploadCertificate.setReceiver(new Upload.Receiver() {
            @Override
            public OutputStream receiveUpload(String filename, String mimeType) {
                FileOutputStream fos = null; // Stream to write to
                try {
                    // Open the file for writing.
                    filename = training.id + "_" + client.getId() + "_" + filename;
                    File file = FilesUitl
                            .createFile(filename);
                    fos = new FileOutputStream(file);
                    fileMap.put(client.getId(), filename);
                    uploadCertificate.setVisible(false);
                    fileLayout.setVisible(true);
                    FileResource resource = new FileResource(FilesUitl.getFile(filename));
                    FileDownloader download = new FileDownloader(resource);
                    download.extend(downloadCertificate);
                } catch (final java.io.FileNotFoundException e) {
                    new Notification("Could not open file");
                    return null;
                }
                return fos; // Return the output stream to write to
            }
        });
        uploadCertificate.setImmediateMode(false);

        deleteCertificate.addClickListener(event -> {
            fileMap.put(client.getId(), null);
            fileLayout.setVisible(false);
            uploadCertificate.setVisible(true);
        });

        graduationLayout.addComponent(isGraduating);
        fileLayout.addComponents(downloadCertificate, deleteCertificate);
        graduationLayout.addComponent(fileLayout);
        graduationLayout.addComponent(uploadCertificate);

    }

    private Component generateTeacherTime() {
        HorizontalLayout lay = new HorizontalLayout();

        TextArea timeOfHours = new TextArea();
        timeOfHours.setReadOnly(true);
        timeOfHours.setCaption(Words.TXT_SUM_OF_WORKEDTIME);
        timeOfHours.setValue(NavigatorUI.getDBProvider()
                .getTeacherTimeInTraining(teacher, training.id).toString());

        Button minusButton = new Button(Words.TXT_MINUS_HOUR);
        minusButton.setEnabled(Integer.valueOf(timeOfHours.getValue()) > 0);
        Button addButton = new Button(Words.TXT_ADD_HOUR, event -> {
            Integer time = Integer.parseInt(timeOfHours.getValue());
            time += 1;
            NavigatorUI.getDBProvider().updateTimeOfTeacherInCourse(time, teacher, training.id);
            minusButton.setEnabled(time > 0);
            timeOfHours.setValue(time.toString());
        });

        minusButton.addClickListener(e -> {
            Integer time = Integer.parseInt(timeOfHours.getValue());
            time -= 1;
            NavigatorUI.getDBProvider().updateTimeOfTeacherInCourse(time, teacher, training.id);
            minusButton.setEnabled(time > 0);
            timeOfHours.setValue(time.toString());
        });

        lay.addComponent(timeOfHours);
        lay.addComponents(addButton, minusButton);
        return lay;
    }

    private Component generateFileOverview() {
        VerticalLayout lay = new VerticalLayout();
        lay.setSizeFull();
        List<TrainingFile> trainingFiles = NavigatorUI.getDBProvider()
                .getFilesForTraining(training);

        if (getModel() instanceof MainViewModel) {
            trainingFiles = trainingFiles.stream().filter(TrainingFile::getVisibleForUsers).collect(
                    Collectors.toList());
        }
        Grid<TrainingFile> grid = new Grid<>();
        grid.setSizeFull();
        grid.setCaption(Words.TXT_FILES);
        grid.setItems(trainingFiles);
        grid.addColumn(new ValueProvider<TrainingFile, Button>() {
            @Override
            public Button apply(TrainingFile trainingFile) {
                Button fileDownlaod = new Button(VaadinIcons.DOWNLOAD);
                FileResource resource = new FileResource(FilesUitl.getFile(trainingFile.getPath()));
                FileDownloader download = new FileDownloader(resource);
                download.extend(fileDownlaod);
                return fileDownlaod;
            }
        }, new ComponentRenderer());

        grid.addColumn(TrainingFile::getPath).setCaption(Words.TXT_FILE_NAME);
        grid.addColumn(TrainingFile::getDescription).setCaption(Words.TXT_DESCRIPTION);

        lay.addComponentsAndExpand(grid);
        if (isInEditableMode) {
            grid.addColumn(new ValueProvider<TrainingFile, Button>() {
                @Override
                public Button apply(TrainingFile trainingFile) {

                    Button deleteButton = new Button(VaadinIcons.DEL);
                    deleteButton.addClickListener(event -> {
                        ConfirmationPopup.showPopup(getModel().getUi(),
                                Words.TXT_CONFIRM_DELETE_FILE, new Runnable() {
                                    @Override
                                    public void run() {
                                        NavigatorUI.getDBProvider().deleteFile(trainingFile);

                                        FilesUitl.deleteFile(trainingFile.getPath());
                                        refreshView();
                                    }
                                });
                    });
                    return deleteButton;
                }
            }, new ComponentRenderer());

            Button fileUpload = new Button(Words.TXT_ADD_NEW_FILE);
            fileUpload.addClickListener(event -> {
                generateUploadFilePopup();
            });
            lay.addComponentsAndExpand(fileUpload);
        }
        return lay;
    }

    private void generateUploadFilePopup() {
        Window subWindow = new Window(Words.TXT_ADD_NEW_FILE_TO_COURSE);
        VerticalLayout subContent = new VerticalLayout();

        TextArea description = new TextArea();
        description.setCaption(Words.TXT_DESCRIPTION);

        CheckBox visileForUsers = new CheckBox(Words.TXT_VISIBLE_FOR_USERS);
        visileForUsers.setValue(true);

        Upload upload = new Upload(Words.TXT_ADD_FILE, new Upload.Receiver() {
            @Override
            public OutputStream receiveUpload(String filename, String mimeType) {
                FileOutputStream fos = null;
                try {

                    File file = FilesUitl.createFile(filename);
                    fos = new FileOutputStream(file);
                    TrainingFile trainingFile = new TrainingFile();
                    trainingFile.setDescription(description.getValue());
                    trainingFile.setPath(filename);
                    trainingFile.setName(filename);
                    trainingFile.setTraining_id(training.getId());
                    trainingFile.setVisibleForUsers(visileForUsers.getValue());
                    NavigatorUI.getDBProvider().addTrainingFile(trainingFile);
                    subWindow.close();
                    refreshView();
                } catch (final java.io.FileNotFoundException e) {
                    new Notification("Could not open file");
                    return null;
                }
                return fos; // Return the output stream to write to
            }
        });
        upload.setImmediateMode(false);

        subContent.addComponent(description);
        subContent.addComponent(upload);
        subWindow.setContent(subContent);
        subWindow.center();
        getModel().currentUI.addWindow(subWindow);
    }

    private Component getTrainingDetailedData() {
        VerticalLayout lay = new VerticalLayout();
        if (isOpenedByTeacher || getModel() instanceof AdminViewModel) {
            lay.addComponent(getClientsPanel());
        }
        if (!isOpenedByTeacher) {
            getTeachersPanel(lay);
        }

        return lay;
    }

    private Component getClientsPanel() {
        List<Client> clients = NavigatorUI.getDBProvider().getClientsForTraining(training);

        Grid<Client> clientsGrid = new Grid<>(Words.TXT_CLIENTS_IN_TRAINING);
        clientsGrid.setSizeFull();
        clientsGrid.setItems(clients);
        clientsGrid.addColumn(Client::getName).setCaption(Words.TXT_NAME);
        clientsGrid.addColumn(Client::getSurname).setCaption(Words.TXT_SURRNAME);
        clientsGrid.addColumn(Client::getEmail).setCaption(Words.TXT_EMAIL);
        clientsGrid.addColumn(Client::getContact_number).setCaption(Words.TXT_PHONE_CONTACT);
        if (getModel() instanceof AdminViewModel) {
            clientsGrid.addColumn(p ->
                    p.getPaid() != null && p.getPaid() ? Words.TXT_YES : Words.TXT_NO)
                    .setCaption(Words.TXT_IS_PAID);
            clientsGrid.addColumn(p ->
                    p.getIs_contract_signed() != null
                            && p.getIs_contract_signed() ? Words.TXT_YES : Words.TXT_NO)
                    .setCaption(Words.TXT_IS_CONTRACT_SIGNED);
            clientsGrid.addColumn(p -> new Button(Words.TXT_EDIT, event -> {
                generateModifyClientPopup(p);
            }), new ComponentRenderer());
        }

        return clientsGrid;
    }

    private void generateModifyClientPopup(Client client) {
        Window subWindow = new Window(Words.TXT_MODIFY_CLIENT_DATA);
        VerticalLayout subContent = new VerticalLayout();
        Label email = new Label(client.email);
        email.setCaption(Words.TXT_EMAIL);

        CheckBox isPaidCheckBox = new CheckBox(Words.TXT_IS_PAID);
        isPaidCheckBox.setValue(client.getPaid() != null && client.getPaid());
        isPaidCheckBox.addValueChangeListener(e -> {
            client.setPaid(e.getValue());
            NavigatorUI.getDBProvider().updateClientInTraining(client, training);
            refreshView();
        });

        CheckBox isContractSigned = new CheckBox(Words.TXT_IS_CONTRACT_SIGNED);
        isContractSigned
                .setValue(client.getIs_contract_signed() != null && client.getIs_contract_signed());
        isContractSigned.addValueChangeListener(e -> {
            client.setIs_contract_signed(e.getValue());
            NavigatorUI.getDBProvider().updateClientInTraining(client, training);
            refreshView();
        });

        Button delButton = new Button(Words.TXT_DELETE_CLIENT_FROM_TRAINING, event -> {
            ConfirmationPopup.showPopup(getModel().getUi(),
                    Words.TXT_DELETE_CLIENT_FROM_TRAINING_TXT, new Runnable() {
                        @Override
                        public void run() {
                            NavigatorUI.getDBProvider().deleteClientFromTraining(client, training);
                            refreshView();
                            subWindow.close();
                        }
                    });
        });

        Button okButton = new Button(Words.TXT_SAVE_DATA, event -> {
            refreshView();
            subWindow.close();
        });

        subContent.addComponent(email);
        subContent.addComponent(isPaidCheckBox);
        subContent.addComponent(isContractSigned);

        VerticalLayout verLay = new VerticalLayout();
        verLay.addComponent(okButton);
        verLay.addComponent(delButton);
        subContent.addComponent(verLay);

        subWindow.setContent(subContent);
        subWindow.center();
        getModel().currentUI.addWindow(subWindow);
    }

    private Component getTeachersPanel(VerticalLayout lay) {
        List<Teachers> teachers = NavigatorUI.getDBProvider().getTeachersForTraining(training);
        Grid<Teachers> grid = new Grid(Words.TXT_TEACHERS);
        grid.setSizeFull();
        grid.setItems(teachers);
        grid.addColumn(Teachers::getName).setCaption(Words.TXT_NAME);
        grid.addColumn(Teachers::getSurname).setCaption(Words.TXT_SURRNAME);
        grid.addColumn(Teachers::getEmail).setCaption(Words.TXT_EMAIL);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        if (getModel() instanceof AdminViewModel) {
            grid.addColumn(p -> new Button(Words.TXT_DELETE_TEACHER_FROM_COURSE, event -> {
                ConfirmationPopup.showPopup(getModel().getUi(),
                        Words.TXT_DELETE_TEACHER_FROM_TRAINING, new Runnable() {
                            @Override
                            public void run() {
                                NavigatorUI.getDBProvider()
                                        .deleteTeacherFromTraining(p, training);
                                refreshView();
                            }
                        });
            }), new ComponentRenderer());
        }
        lay.addComponent(grid);

        if (getModel() instanceof AdminViewModel) {
            Button addButton = new Button(Words.TXT_ADD_TEACHER_TO_COURSE, event -> {
                generateAddTeacherPopup();
            });
            if (isInEditableMode) {
                lay.addComponent(addButton);
            }
        }

        return lay;
    }

    private void generateAddTeacherPopup() {
        Window subWindow = new Window(Words.TXT_SELECT_TEACHER_TO_ADD);
        VerticalLayout subContent = new VerticalLayout();
        List<Teachers> teachers = NavigatorUI.getDBProvider().getTeachers();
        List<Teachers> signedTeachers =
                NavigatorUI.getDBProvider().getTeachersForTraining(training);

        teachers.stream().filter(t -> signedTeachers.stream()
                .noneMatch(a -> a.getEmail().equals(t.getEmail())));

        ComboBox<Teachers> comboBox = new ComboBox<>();
        comboBox.setItemCaptionGenerator(item -> item.name + " " + item.getSurname());
        comboBox.setItems(teachers);

        Button addButton = new Button(Words.TXT_ADD, event -> {
            Teachers selectedTeacher = comboBox.getValue();
            if (selectedTeacher != null) {
                NavigatorUI.getDBProvider().addTeacherToTraining(selectedTeacher, training);
                teachers.remove(selectedTeacher);
                comboBox.setItems(teachers);
                subWindow.close();
                refreshView();
                Notification.show(Words.TXT_CORRECTRLY_SAVED);

            }
        });

        subContent.addComponent(comboBox);
        subContent.addComponent(addButton);
        subWindow.setContent(subContent);
        subWindow.center();
        getModel().currentUI.addWindow(subWindow);
    }

    private Component getTrainingInfo() {
        VerticalLayout lay = new VerticalLayout();

        Course course = NavigatorUI.getDBProvider().getCourse(training.course_id);

        TextArea courseName = new TextArea();
        courseName.setValue(course.getName());
        courseName.setCaption(Words.TXT_COURSE_NAME);
        courseName.setReadOnly(true);

        TextArea courseDescription = new TextArea();
        courseDescription.setValue(course.getDescription());
        courseDescription.setCaption(Words.TXT_DESCRIPTION);
        courseDescription.setReadOnly(true);
        courseDescription.setSizeFull();


        lay.addComponent(courseName);
        lay.addComponent(courseDescription);
        return lay;
    }

    private Component generateModifyPane(List<CalendarEvent> calendarItems) {
        VerticalLayout lay = new VerticalLayout();

        Button addNew = new Button(Words.TXT_ADD_NEW_teRM);
        addNew.addClickListener(event -> newTermPopup());

        Grid<CalendarEvent> calendarEventGrid = new Grid<>();
        calendarEventGrid.setSizeFull();
        calendarEventGrid.setItems(NavigatorUI.getDBProvider().getCalendarTermsFor(training));
        calendarEventGrid.addColumn(CalendarEvent::getName).setCaption(Words.TXT_TRAINING_NAME);
        calendarEventGrid.addColumn(d ->
                new SimpleDateFormat("YYYY-MM-dd HH:mm").format(new Date(d.start_date.getTime()))
        ).setCaption(Words.TXT_START_DATE);
        calendarEventGrid.addColumn(d -> new SimpleDateFormat("YYYY-MM-dd HH:mm")
                .format(new Date(d.end_date.getTime()))).setCaption(Words.TXT_END_DATE);
        calendarEventGrid.addColumn(p -> new Button(Words.TXT_DELETE, e -> {
            ConfirmationPopup.showPopup(getModel().getUi(), Words.TXT_CONFIRM_DELETE_DATE,
                    new Runnable() {
                        @Override
                        public void run() {
                            NavigatorUI.getDBProvider().deteteTrainingTerm(p);
                            refreshView();
                        }
                    });
        }), new ComponentRenderer());
        calendarEventGrid.setSelectionMode(Grid.SelectionMode.SINGLE);

        lay.addComponent(calendarEventGrid);
        lay.addComponent(addNew);
        if (getModel() instanceof AdminViewModel) {
            Button showTimePopUp = new Button(Words.TXT_GENERATE_TIME,
                    event -> showTimePopup(buildTimeMap(calendarItems)));
            lay.addComponent(showTimePopUp);
            showTimePopUp.setEnabled(calendarItems.size() > 0);
        }

        return lay;
    }

    private void newTermPopup() {
        Window subWindow = new Window(Words.TXT_ADD_NEW_teRM);
        VerticalLayout subContent = new VerticalLayout();
        TextArea nameArea = new TextArea(Words.TXT_CAPTION);
        TextArea desriptionArea = new TextArea(Words.TXT_DESCRIPTION);
        HorizontalLayout startDateLay = new HorizontalLayout();
        DateField startDate =
                new DateField(Words.TXT_START_DATE);
        startDate.setDescription("format dd.MM.rr");
        ComboBox startTimeCombo = new ComboBox(Words.TXT_START_TIME);
        startTimeCombo.setItems(generateTimeSet());
        startTimeCombo.setEmptySelectionAllowed(false);
        startDateLay.addComponentsAndExpand(startDate);
        startDateLay.addComponentsAndExpand(startTimeCombo);

        HorizontalLayout endDateLay = new HorizontalLayout();
        ComboBox endTimeCombo = new ComboBox(Words.TXT_END_TIME);
        endTimeCombo.setItems(generateTimeSet());
        endTimeCombo.setEmptySelectionAllowed(false);
        endDateLay.addComponentsAndExpand(endTimeCombo);

        HorizontalLayout cyclicMeetingLayout = new HorizontalLayout();
        CheckBox cyclicMeeting = new CheckBox(Words.TXT_IS_CYCLIC);
        ComboBox<Integer> numberOfMeetings = new ComboBox(Words.TXT_WEEKS_TILL_END);
        numberOfMeetings.setItems(IntStream.range(1, 15).boxed());
        numberOfMeetings.setVisible(false);
        numberOfMeetings.setSelectedItem(1);
        cyclicMeeting.addValueChangeListener(event -> {
            numberOfMeetings.setVisible(event.getValue());
        });

        cyclicMeetingLayout.addComponent(cyclicMeeting);
        cyclicMeetingLayout.addComponent(numberOfMeetings);

        HorizontalLayout lay = new HorizontalLayout();

        Button addButton = new Button(Words.TXT_ADD);
        addButton.addClickListener(event -> {
            CalendarEvent calendarEvent = new CalendarEvent();
            try {
                calendarEvent.setName(nameArea.getValue());
                calendarEvent.setDescription(desriptionArea.getValue());
                LocalDateTime startTime = startDate.getValue().atStartOfDay();
                if (startTimeCombo.getValue() != null) {
                    startTimeCombo.getValue().toString().split(":");
                    Integer hour =
                            Integer.valueOf(startTimeCombo.getValue().toString().split(":")[0]);
                    Integer minutes =
                            Integer.valueOf(startTimeCombo.getValue().toString().split(":")[1]);
                    startTime = startTime.plusHours(hour);
                    startTime = startTime.plusMinutes(minutes);
                }
                LocalDateTime endTime = startDate.getValue().atStartOfDay();
                if (endTimeCombo.getValue() != null) {
                    endTimeCombo.getValue().toString().split(":");
                    Integer hour =
                            Integer.valueOf(endTimeCombo.getValue().toString().split(":")[0]);
                    Integer minutes =
                            Integer.valueOf(endTimeCombo.getValue().toString().split(":")[1]);
                    endTime = endTime.plusHours(hour);
                    endTime = endTime.plusMinutes(minutes);
                }

                calendarEvent.setEnd_date(Timestamp.valueOf(endTime));
                calendarEvent.setStart_date(Timestamp.valueOf(startTime));
                NavigatorUI.getDBProvider().addNewCalendarEvent(training.getId(), calendarEvent);
                if (cyclicMeeting.getValue()) {
                    Integer weeks = numberOfMeetings.getValue();
                    for (int i = 0; i < weeks - 1; i++) {
                        endTime = endTime.plusWeeks(1);
                        startTime = startTime.plusWeeks(1);
                        calendarEvent.setEnd_date(Timestamp.valueOf(endTime));
                        calendarEvent.setStart_date(Timestamp.valueOf(startTime));
                        NavigatorUI.getDBProvider()
                                .addNewCalendarEvent(training.getId(), calendarEvent);
                    }
                }
                subWindow.close();
                refreshView();
            } catch (Exception e) {
                Logger.getGlobal().log(Level.SEVERE, "Błąd przy add CalendarEent", e);
                Notification.show(Words.TXT_FILL_ALL_FIELDS);
            }
        });
        Button cancelButton = new Button(Words.TXT_DECLINE);

        cancelButton.addClickListener(event -> subWindow.close());
        lay.addComponentsAndExpand(addButton, cancelButton);
        subContent.addComponent(nameArea);
        subContent.addComponent(desriptionArea);
        subContent.addComponent(startDateLay);
        subContent.addComponent(endDateLay);
        subContent.addComponent(cyclicMeetingLayout);
        subContent.addComponent(lay);

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

    public DetailedTrainingPanel(MyModel model, Training training, Boolean inEdditableMode,
            Boolean openedByTeacher) {
        super(false, model);
        this.training = training;
        this.isInEditableMode = inEdditableMode;
        this.isOpenedByTeacher = openedByTeacher;
        if (isOpenedByTeacher) {
            Teachers teacher = NavigatorUI.getDBProvider().getTeachers().stream()
                    .filter(t -> t.id == NavigatorUI.getLoggedUser().id)
                    .collect(Collectors.toList()).get(0);
            this.teacher = teacher;
        }
        setContent(buildView());
    }

    public Component getCalendarForTraining() {
        HorizontalLayout lay = new HorizontalLayout();

        Calendar<CalendarItem> calendar = new Calendar<>(Words.TXT_AVALIBLE_TERMS);
        java.util.Calendar ccStart = java.util.Calendar.getInstance();
        ccStart.set(java.util.Calendar.DAY_OF_MONTH,1);
        java.util.Calendar ccEnd= java.util.Calendar.getInstance();
        ccEnd.set(java.util.Calendar.DAY_OF_MONTH,30);

        calendar.setStartDate(ccStart.getTime().toInstant().atZone(ZoneId.systemDefault()));
        calendar.setEndDate(ccEnd.getTime().toInstant().atZone(ZoneId.systemDefault()));
        calendar.setSizeFull();
        List<CalendarEvent> calendarItems =
                NavigatorUI.getDBProvider().getCalendarTermsFor(training);

        BasicItemProvider<CalendarItem> dataProvider = new BasicItemProvider<>();

        dataProvider.setItems(calendarItems.stream().map(CalendarItem::createFrom).collect(
                Collectors.toList()));
        calendar.setDataProvider(dataProvider);

        lay.addComponentsAndExpand(calendar);
        if (isInEditableMode) {
            lay.addComponentsAndExpand(generateModifyPane(calendarItems));
        }

        return lay;
    }

    private void showTimePopup(HashMap<String, Double> buildTimeMap) {
        Window subWindow = new Window(Words.TXT_TIME_RAPORT);
        VerticalLayout subContent = new VerticalLayout();
        buildTimeMap.entrySet();
        Grid<Map.Entry<String, Double>> description = new Grid<>();
        description.setItems(buildTimeMap.entrySet());

        Grid.Column one = description.addColumn(m -> m.getKey()).setCaption(Words.TXT_MEETING_NAME);
        Grid.Column two =
                description.addColumn(m -> new Double(m.getValue()/60)).setCaption(Words.TXT_TIME_IN_HOURS);
        FooterRow footerRow = description.prependFooterRow();
        footerRow.getCell(one).setText(Words.TXT_SUM);
        Double summed = buildTimeMap.values().stream().mapToDouble(f->f).sum();
        footerRow.getCell(two).setText(
                String.valueOf(new Double(summed/60)));
        subContent.addComponent(description);
        subWindow.setContent(subContent);
        subWindow.center();
        getModel().currentUI.addWindow(subWindow);
    }

    private static HashMap<String, Double> buildTimeMap(List<CalendarEvent> calendarItems) {
        HashMap<String, Double> timeMap = new HashMap<>();

        calendarItems.forEach(f -> {
            String name = f.getName();
            Double newTime = new Double(compareTwoTimeStamps(f.end_date, f.start_date));
            if (timeMap.containsKey(name)) {
                Double oldTime = new Double( timeMap.get(name));
                newTime += oldTime;
            }
            timeMap.put(name, newTime);
        });
        return timeMap;
    }

    public static long compareTwoTimeStamps(java.sql.Timestamp currentTime,
            java.sql.Timestamp oldTime) {
        long milliseconds1 = oldTime.getTime();
        long milliseconds2 = currentTime.getTime();

        long diff = milliseconds2 - milliseconds1;
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return diffMinutes;
    }

    private static final class CalendarItem extends BasicItem {
        public static CalendarItem createFrom(CalendarEvent item) {
            return new CalendarItem(item.getName(), item.getDescription(),
                    item.getStart_date().toInstant().atZone(ZoneId.systemDefault()),
                    item.getEnd_date().toInstant().atZone(ZoneId.systemDefault()));
        }

        public CalendarItem(String caption, String description, ZonedDateTime startDate,
                ZonedDateTime endDate) {
            super(caption, description, startDate, endDate);
        }
    }

}
