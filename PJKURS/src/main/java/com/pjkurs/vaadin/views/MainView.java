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
package com.pjkurs.vaadin.views;

import com.pjkurs.domain.Appusers;
import com.pjkurs.domain.Category;
import com.pjkurs.domain.Course;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.ui.containers.ArchiveCoursesPanel;
import com.pjkurs.vaadin.ui.containers.ContactDataPanel;
import com.pjkurs.vaadin.ui.containers.CoursesPanel;
import com.pjkurs.vaadin.ui.containers.DetailedCoursePanel;
import com.pjkurs.vaadin.ui.containers.MyCoursesPanel;
import com.pjkurs.vaadin.ui.containers.PersonalDataPanel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.pjkurs.vaadin.ui.containers.TopPanel;
import com.pjkurs.vaadin.ui.menu.MainMenuPanel;
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tmejs
 */
@Theme("pjtheme")
public class MainView extends MyContainer<MainViewModel> implements View, InterfacePJKURSView {

    private Component topPanel;
    private Component menuPanel;
    private Component mainPanel;

    public MainView(MainViewModel model) {
        super(model, true);
    }

    //Zbudowanie widoku głównej aplikacji
    @Override
    public Component buildView() {
        this.setSizeFull();
        VerticalLayout mainLayout = new VerticalLayout();
//        mainLayout.addStyleName("horrizontaly-full-view");
        topPanel = generateTopPanel();
        mainLayout.addComponent(topPanel);
        menuPanel = generateMenu();
        mainLayout.addComponent(menuPanel);
        mainPanel = generateMainAppPanel();
        mainLayout.addComponent(mainPanel);
//        setCoursesAsMainPanel();
        return mainLayout;
    }

    @Override
    public final Component generateTopPanel() {
        //zwracamy top panel
        TopPanel topPanel = new TopPanel<>(getModel());
        return topPanel;
    }

    @Override
    public Component generateMenu() {
        MainMenuPanel<MainViewModel> menuPanel = new MainMenuPanel<>(getModel());
        return menuPanel;
    }

    @Override
    public Component generateMainAppPanel() {
        return new CoursesPanel<>(null,getModel());
    }

    public void setCoursesAsMainPanel(Category category, MenuBar.MenuItem selectedItem) {
        CoursesPanel panel = new CoursesPanel(category,getModel());
        ((VerticalLayout) this.getContent()).replaceComponent(mainPanel, panel);
        mainPanel = panel;
    }

    public void setDetailedCoursePanel(Course course) {
        DetailedCoursePanel panel = new DetailedCoursePanel(getModel(), course);
    }

    public void setMyCoursesAsMainPanel(MenuBar.MenuItem item) {
        MyCoursesPanel panel = new MyCoursesPanel(getModel());
        ((VerticalLayout) this.getContent()).replaceComponent(mainPanel, panel);
        mainPanel = panel;
    }

    public void setMyDataAsPanel() {
        PersonalDataPanel panel = new PersonalDataPanel(getModel());
        ((VerticalLayout) this.getContent()).replaceComponent(mainPanel, panel);
        mainPanel = panel;
    }

    public void setDetailedCourseAsMainPanel(Integer id) {
        Logger.getGlobal().log(Level.SEVERE, "setDetailedCourseAsMainPanel:" + id);

        Course course = NavigatorUI.getDBProvider().getCourse(id);
        Logger.getGlobal().log(Level.SEVERE, course.name);
        DetailedCoursePanel panel = new DetailedCoursePanel(getModel(), course);
        ((VerticalLayout) this.getContent()).replaceComponent(mainPanel, panel);
        mainPanel = panel;
    }

    public void setContactPanelAsMainPanel(MenuBar.MenuItem selectedItem) {
        ContactDataPanel panel = new ContactDataPanel(getModel());
        ((VerticalLayout) this.getContent()).replaceComponent(mainPanel, panel);
        mainPanel = panel;
    }

    public void setArchiveCoursesPanelAsMainPanel(MenuBar.MenuItem selectedItem) {
        ArchiveCoursesPanel panel = new ArchiveCoursesPanel(getModel());
        ((VerticalLayout) this.getContent()).replaceComponent(mainPanel, panel);
        mainPanel = panel;
    }

}
