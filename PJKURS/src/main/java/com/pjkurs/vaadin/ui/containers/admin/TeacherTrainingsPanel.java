package com.pjkurs.vaadin.ui.containers.admin;

import java.util.List;

import com.pjkurs.domain.Appusers;
import com.pjkurs.domain.Teachers;
import com.pjkurs.domain.Training;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.annotations.Theme;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.renderers.ComponentRenderer;
@Theme("pjtheme")
public class TeacherTrainingsPanel extends MyContainer<MainViewModel> {

    Teachers teacher;
    public TeacherTrainingsPanel(MainViewModel model) {
        super(false, model);
        Appusers appuser =NavigatorUI.getLoggedUser();
        teacher =
                NavigatorUI.getDBProvider().getTeachers().stream()
                        .filter(t -> t.getId().equals(appuser.id)).findFirst().get();
        setContent(buildView());
    }

    @Override
    public Component buildView() {
        HorizontalLayout lay = new HorizontalLayout();
        lay.addComponentsAndExpand(generateTrainingsPanel());
        return lay;
    }

    private Component generateTrainingsPanel() {
        List<Training> trainings =
                NavigatorUI.getDBProvider().getTrainingsForTeacher(teacher);
        Grid<Training> grid = new Grid<>(Words.TXT_ONGOING_COURSES);
        grid.setItems(trainings);
        trainings.forEach(t -> t.setCourse(NavigatorUI.getDBProvider().getCourse(t.course_id)));

        grid.addColumn(t ->t.getCourse().getName()).setCaption(Words.TXT_COURSE_NAME);
        grid.addColumn(t ->t.getStart_date()).setCaption(Words.TXT_START_DATE);
        grid.addColumn(t -> new Button(Words.TXT_DETAILS,
                        event -> getModel().detailedTrainingPanelClicked(t,true, true)),
                new ComponentRenderer());
        return grid;
    }


}
