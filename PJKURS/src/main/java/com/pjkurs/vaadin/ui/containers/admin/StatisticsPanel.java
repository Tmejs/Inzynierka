package com.pjkurs.vaadin.ui.containers.admin;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import com.pjkurs.domain.Appusers;
import com.pjkurs.domain.Training;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.annotations.Theme;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@Theme("pjtheme")
public class StatisticsPanel<T extends AdminViewModel> extends MyContainer<T> {

    LocalDate startDate;
    LocalDate endDate;

    public StatisticsPanel(T model) {
        super(false, model);
        setContent(buildView());
    }

    @Override
    public Component buildView() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();

        layout.addComponent(generateDatePanel());
        layout.addComponentsAndExpand(generateTrainingsPanel());
        layout.addComponentsAndExpand(generateUsersPanel());
        return layout;
    }

    private Component generateTrainingsPanel() {
        String strinStartDate = startDate != null ?
                new SimpleDateFormat("YYYY-MM-dd").format(Date
                        .from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant())) : null;
        String strinEndDate = endDate != null ?
                new SimpleDateFormat("YYYY-MM-dd").format(Date
                        .from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant())) : null;
        List<Integer> trainignsIds =
                NavigatorUI.getDBProvider().getTrainingsIds(strinStartDate, strinEndDate);
        List<Training> trainigns =
                trainignsIds.stream().map(p -> NavigatorUI.getDBProvider().getTraining(p)).collect(
                        Collectors.toList());

        Grid<Training> grid = new Grid(Words.TXT_TRAININGS_IN_DATES);
        grid.setSizeFull();
        grid.setItems(trainigns);
        grid.addColumn(t -> t.getCourse().getName()).setCaption(Words.TXT_TRAINING_NAME);
        grid.addColumn(t -> t.start_date != null ?
                new SimpleDateFormat("YYYY-MM-dd").format(t.getStart_date()) : "")
                .setCaption(Words.TXT_START_DATE);
        grid.addColumn(t -> t.end_date != null ?
                new SimpleDateFormat("YYYY-MM-dd").format(t.end_date) : "")
                .setCaption(Words.TXT_END_DATE);
        grid.addColumn(t -> NavigatorUI.getDBProvider().getCountOfParticipantsForCourse(t))
                .setCaption(Words.TXT_COUNT_OF_PARTICIPANTS);
        grid.addColumn(t -> NavigatorUI.getDBProvider().getCountOfGraduates(t))
                .setCaption(Words.TXT_COUNT_OF_GRADUATES);
        grid.addColumn(t -> NavigatorUI.getDBProvider().getIncomeForTraining(t))
                .setCaption(Words.TXT_INCOME);

        return grid;
    }

    private Component generateDatePanel() {
        VerticalLayout lay = new VerticalLayout();

        Label startLabel = new Label(Words.TXT_START);
        DateField startDateField = new DateField();
        if (startDate != null) {
            startDateField.setValue(startDate);
        }
        startDateField.addValueChangeListener(e -> {
            startDate = e.getValue();
            refreshView();
        });

        Label endLabel = new Label(Words.TXT_END);
        DateField endDateField = new DateField();

        if (endDate == null) {
            endDate = LocalDate.now();
        }
        endDateField.setValue(endDate);
        endDateField.addValueChangeListener(e -> {
            endDate = e.getValue();
            refreshView();
        });

        lay.addComponentsAndExpand(new HorizontalLayout(startLabel, startDateField),
                new HorizontalLayout(endLabel, endDateField));
        return lay;
    }

    public Component generateUsersPanel() {
        Grid<Appusers> grid = new Grid<>(Words.TXT_USERS_REGISTERED_IN_TIME);
        grid.setSizeFull();
        String strinStartDate = startDate != null ?
                new SimpleDateFormat("YYYY-MM-dd").format(Date
                        .from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant())) : null;
        String strinEndDate = endDate != null ?
                new SimpleDateFormat("YYYY-MM-dd").format(Date
                        .from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant())) : null;
        List<Integer> ids = NavigatorUI.getDBProvider()
                .getUsersIdsCreated(strinStartDate, strinEndDate);

        List<Appusers> users = ids.stream().map(i -> NavigatorUI.getDBProvider()
                .getUser(i))
                .collect(Collectors.toList());

        grid.setItems(users);

        grid.addColumn(Appusers::getName).setCaption(Words.TXT_NAME);
        grid.addColumn(Appusers::getSurname).setCaption(Words.TXT_SURRNAME);
        grid.addColumn(Appusers::getEmail).setCaption(Words.TXT_EMAIL);
        grid.addColumn(t -> t.create_date != null ?
                new SimpleDateFormat("YYYY-MM-dd").format(t.create_date) : "")
                .setCaption(Words.TXT_CREATE_DATE);

        return grid;
    }

}
