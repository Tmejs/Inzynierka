package com.pjkurs.vaadin.ui.containers.admin;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.pjkurs.domain.Teachers;
import com.pjkurs.domain.Training;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.annotations.Theme;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.renderers.ComponentRenderer;

@Theme("pjtheme")
public class TeachersPanel extends MyContainer<AdminViewModel> {

    Component teachersPanel;
    Teachers selectedTeacher;
    public TeachersPanel(AdminViewModel model) {
        super(false, model);
        setContent(buildView());
    }

    @Override
    public Component buildView() {
        HorizontalLayout lay = new HorizontalLayout();

        lay.addComponentsAndExpand(generateTeachersGrid());
        teachersPanel = generateDetailedTeacherPanel();
        lay.addComponentsAndExpand(teachersPanel);

        return lay;
    }

    private Component generateDetailedTeacherPanel() {
        if(selectedTeacher==null){
            Panel panel =new Panel();
            panel.setVisible(false);
            return panel;
        }

        HorizontalLayout lay = new HorizontalLayout();
        lay.addComponentsAndExpand(generateTrainingsPanel());

        return lay;
    }

    private Component generateTrainingsPanel() {
        List<Training> trainings =
                NavigatorUI.getDBProvider().getTrainingsForTeacher(selectedTeacher);
        Grid<Training> grid = new Grid<>(Words.TXT_TRAININGS_BY_TEACHER);
        grid.setItems(trainings);
        trainings.forEach(t -> t.setCourse(NavigatorUI.getDBProvider().getCourse(t.course_id)));

        grid.addColumn(t ->t.getCourse().getName()).setCaption(Words.TXT_COURSE_NAME);
        grid.addColumn(t ->t.getStart_date()).setCaption(Words.TXT_START_DATE);
        grid.addColumn(t-> NavigatorUI.getDBProvider().getTeacherTimeInTraining(selectedTeacher,
                t.getId())).setCaption(Words.TXT_TIME_IN_TRAINING);
        // grid.addColumn(t -> new Button(Words.TXT_DETAILS,
        //                 event -> getModel().detailedTrainingPanelClicked(t)),
        //         new ComponentRenderer());
        return grid;
    }

    private Component generateTeachersGrid() {
        List<Teachers> teachers = NavigatorUI.getDBProvider().getTeachers();
        Grid<Teachers> grid = new Grid(Words.TXT_TEACHERS);
        grid.setItems(teachers);
        grid.addColumn(Teachers::getName).setCaption(Words.TXT_NAME);
        grid.addColumn(Teachers::getSurname).setCaption(Words.TXT_SURRNAME);
        grid.addColumn(Teachers::getEmail).setCaption(Words.TXT_EMAIL);
        grid.addColumn(Teachers::getTypeOfWork).setCaption(Words.TXT_CONTRACT_BASE);
        grid.addItemClickListener(event -> refreshDetailedPanel(event.getItem()));

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        return grid;
    }

    private void refreshDetailedPanel(Teachers teacher) {
        if(teacher!=null) {
            selectedTeacher=teacher;
            Component newComponent = generateDetailedTeacherPanel();
            ((HorizontalLayout) getContent()).replaceComponent(teachersPanel, newComponent);
            teachersPanel = newComponent;
        }else{
            selectedTeacher=null;
            teachersPanel.setVisible(false);
        }
    }
}
