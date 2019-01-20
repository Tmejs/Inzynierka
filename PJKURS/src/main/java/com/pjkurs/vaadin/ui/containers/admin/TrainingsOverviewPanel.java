package com.pjkurs.vaadin.ui.containers.admin;

import java.text.SimpleDateFormat;
import java.util.List;

import com.pjkurs.domain.Training;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.annotations.Theme;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ComponentRenderer;
@Theme("pjtheme")
public class TrainingsOverviewPanel<T extends AdminViewModel> extends MyContainer<T> {

    List<Training> trainings;
    public TrainingsOverviewPanel(T model) {
        super(false, model);
        trainings = NavigatorUI.getDBProvider().getOpenedTrainings();
        setContent(buildView());
    }

    @Override
    public void refreshView() {
        trainings = NavigatorUI.getDBProvider().getOpenedTrainings();
        super.refreshView();
    }

    @Override
    public Component buildView() {
        VerticalLayout layout = new VerticalLayout();

        Grid<Training> grid = new Grid();
        grid.setCaption(Words.TXT_TRAININGS);
        grid.setSizeFull();
        grid.setItems(trainings);
        grid.addColumn(t -> t.getCourse().getName()).setCaption(Words.TXT_TRAINING_NAME);
        grid.addColumn(t -> t.start_date != null ?
                new SimpleDateFormat("YYYY-MM-dd").format(t.getStart_date()) : "")
                .setCaption(Words.TXT_START_DATE);
        grid.addColumn(t -> NavigatorUI.getDBProvider().getCountOfParticipantsForCourse(t))
                .setCaption(Words.TXT_COUNT_OF_PARTICIPANTS);
        grid.addColumn(t -> NavigatorUI.getDBProvider().getIncomeForTraining(t))
                .setCaption(Words.TXT_INCOME);
        grid.addColumn(t -> new Button(Words.TXT_DETAILS,
                        event -> getModel().detailedTrainingPanelClicked(t)),
                new ComponentRenderer());


        layout.addComponentsAndExpand(grid);
        return layout;
    }
}
