package com.pjkurs.vaadin.ui.containers.client;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.pjkurs.domain.GraduatedCourse;
import com.pjkurs.usables.Words;
import com.pjkurs.utils.FilesUitl;
import com.pjkurs.utils.PDFCreator;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.pjkurs.vaadin.views.system.MyModel;
import com.vaadin.annotations.Theme;
import com.vaadin.data.ValueProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ComponentRenderer;
import org.bouncycastle.asn1.icao.LDSVersionInfo;

@Theme("pjtheme")
public class GraduatedCoursesPanel<T extends MyModel> extends MyContainer<T> {

    public GraduatedCoursesPanel(T model) {
        super(false, model);
        setContent(buildView());
        setSizeFull();
    }

    @Override
    public Component buildView() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        List<GraduatedCourse> graduatedCourses =
                NavigatorUI.getDBProvider().getGraduatedCourses(NavigatorUI.getLoggedUser());
        if (graduatedCourses.size() > 0) {
            layout.addComponentsAndExpand(generateGraduateCoursesGrid(graduatedCourses));
        } else {
            Label label = new Label();
            label.setCaption(Words.TXT_NO_GRADUATED_COURSES);
            layout.addComponent(label);
        }

        return layout;
    }

    private Component generateGraduateCoursesGrid(List<GraduatedCourse> courses) {
        Grid<GraduatedCourse> grid = new Grid(Words.TXT_GRADUATED_COURSES);
        grid.setSizeFull();
        grid.setItems(courses);

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
                String name = null;
                try {
                    name = PDFCreator.getPdfCertificate(NavigatorUI.getLoggedUser(), course);
                } catch (Exception e) {
                    Logger.getGlobal().log(Level.WARNING, "Błąd generoania pliku");
                    e.printStackTrace();
                }

                FileResource resource = new FileResource(FilesUitl.getFile(name));
                FileDownloader download = new FileDownloader(resource);
                download.extend(fileDownlaod);
                return fileDownlaod;
            }
        }, new ComponentRenderer()).setCaption(Words.TXT_GET_CERTIFICATE);


        return grid;
    }
}
