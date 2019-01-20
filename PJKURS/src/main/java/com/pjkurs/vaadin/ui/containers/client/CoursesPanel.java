package com.pjkurs.vaadin.ui.containers.client;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.pjkurs.domain.Category;
import com.pjkurs.domain.Course;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.ui.containers.client.CoursePanel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.pjkurs.vaadin.views.system.MyModel;
import com.vaadin.annotations.Theme;
import com.vaadin.data.HasValue;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@Theme("pjtheme")

@SuppressWarnings("Duplicates")
public class CoursesPanel<T extends MyModel> extends MyContainer<T> {

    private String filter;
    private Component coursesComponent;
    private VerticalLayout mainViewComponent;
    private Category category;

    public CoursesPanel(Category category, T model) {
        super(model);
        this.category = category;
        Logger.getGlobal().log(Level.SEVERE, "category :" + category);
        this.setContent(buildView());
    }

    @Override
    public Component buildView() {
        Logger.getGlobal().log(Level.SEVERE, "buildView()");
        this.setWidth("100%");

        VerticalLayout mainView = new VerticalLayout();
        mainViewComponent = mainView;

        mainView.setSizeFull();

        coursesComponent = buildCourses();
        mainView.addComponent(buildFiltersMenu());
        mainView.addComponent(coursesComponent);

        return mainView;
    }

    private Component buildFiltersMenu() {
        VerticalLayout mainLayout = new VerticalLayout();

        if (category != null) {
            Label label = new Label(category.name + ": " + category.description);
            mainLayout.addComponent(label);
        }
        HorizontalLayout horLay = new HorizontalLayout();

        Label filterName = new Label(Words.TXT_FIND);
        horLay.addComponent(filterName);

        TextField filteredText = new TextField(new HasValue.ValueChangeListener<String>() {
            @Override
            public void valueChange(HasValue.ValueChangeEvent<String> event) {
                if (event.getValue().length() > 2) {
                    filter = event.getValue();

                    refreshCoursesComponent();
                } else {
                    filter = null;
                    refreshCoursesComponent();
                }
            }
        });

        horLay.addComponent(filteredText);
        mainLayout.addComponent(horLay);

        return mainLayout;
    }

    private Component buildCourses() {
        if (category != null) {
            Logger.getGlobal().log(Level.SEVERE, "buildCourses()" + category.name);
        }
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setWidth("100%");
        List<Course> tempCourses = NavigatorUI.getDBProvider().getAvalibleCourses();
        List<Course> courses = tempCourses.stream().filter((t) -> {
            return checkFilter(t);
        }).collect(Collectors.toList());

        if (courses == null || courses.isEmpty()) {
            mainLayout.addComponent(new Label(Words.TXT_NO_AVALIBLE_COURSES));
            return mainLayout;
        }

        //Utworzenie komponentów
        Integer counter = 0;
        HorizontalLayout horizontalPanel = new HorizontalLayout();
        horizontalPanel.setWidth("100%");

        for (Course course : courses) {
            CoursePanel coursePanel = new CoursePanel(course, getModel());
            paintCoursePanel(coursePanel, course);
            if (counter >= 3) {
                counter = 0;
                mainLayout.addComponent(horizontalPanel);
                horizontalPanel = new HorizontalLayout();
                horizontalPanel.setWidth("100%");
            }
            horizontalPanel.addComponent(coursePanel);
            counter = counter + 1;
        }
        mainLayout.addComponent(horizontalPanel);
        return mainLayout;
    }

    private void refreshCoursesComponent() {
        Component newCourses = buildCourses();
        mainViewComponent.replaceComponent(coursesComponent, newCourses);
        coursesComponent = newCourses;
    }


    private void paintCoursePanel(CoursePanel panel, Course course) {

        if (course.statusId == 2) {
            panel.addStyleName("new-course");
        }
        if (course.statusId == 1) {
            panel.addStyleName("end-course");
        }
        if (course.statusId == 3) {
            panel.addStyleName("starting-course");
        }

    }


    private boolean checkFilter(Course course) {
        Logger.getGlobal().log(Level.SEVERE, "checkFilter()");

        if (category != null) {
            Logger.getGlobal().log(Level.SEVERE, "category w check:" + category.name);
            return course.getCategoryList().contains(category);
        }
        if (filter != null) {
            return (course.description.contains(filter)
                    || course.name.contains(filter)
                    || course.getCategoryList().stream().anyMatch((t) ->
                    t.description.contains(filter) || t.name.contains(filter)));
        } else {
            return true;
        }
    }

}
