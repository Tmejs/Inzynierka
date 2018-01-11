/*
 * AWS PROJECT (c) 2017 by tmejs (mateusz.rzad@gmail.com)
 * --------------------------------------------------------
 * Niniejszy program chroniony jest prawem autorskim. Jego rozpowszechnianie bez wyraźnej zgody autora
 * jest zabronione. Jakakolwiek ingerencja w oprogramowanie bez upoważnienia autora,
 * w tym w szczególności jego modyfikacja lub nieuprawnione kopiowanie jest sprzeczne z prawem.
 * Wersja opracowana dla Domax Sp. z o.o. z siedzibą w Łężycach
 */
package com.pjkurs.vaadin.views.models;

import com.pjkurs.db.DbDataProvider;
import com.pjkurs.domain.Client;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.system.MyModel;
import com.pjkurs.vaadin.views.MainView;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import java.io.File;

/**
 *
 * @author Tmejs
 */
public class MainViewModel extends MyModel<MainView> {

    public MainViewModel(UI ui) {
        this.currentUI = ui;
    }

    public void coursesButtonClicked(Button.ClickEvent event) {

    }

    public void archiveButtonClicked(Button.ClickEvent event) {

    }

    public void detailCourseButtonClickd(Button.ClickEvent event) {

    }

    public void registerButtonClicked(Button.ClickEvent event) {
        registerNewUser();
    }

    public void myCoursesButtonClicked(Button.ClickEvent event) {

    }

    public void personalDataButtonClicked(Button.ClickEvent event) {

    }

    public void registerToCourseButtonClicked(Button.ClickEvent event) {

    }

    public void logoutButtonClick(Button.ClickEvent event) {

    }

    public void loginButtonClick(Button.ClickEvent event) {

    }

    public void notifButtonClicked(Button.ClickEvent clickEvent) {
        getView().setLoginButtonClicked();
    }

    public void niezlogowanyButtonClicked(Button.ClickEvent event) {
        getUi().getNavigator().navigateTo(NavigatorUI.View.REGISTER_VIEW.getName());
    }

    private void registerNewUser() {
        DbDataProvider dataProvider = new DbDataProvider();
        if (dataProvider.registerNewClient(new Client())) {
            Notification.show("Poprawnie zalogowano");
        } else {
            Notification.show("Zle zalogowano");
        }
    }

    public void myDataButtonClicked(Button.ClickEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Resource getLogoResource() {
        // Find the application directory
        String basepath = VaadinService.getCurrent()
                .getBaseDirectory().getAbsolutePath();

// Image as a file resource
        FileResource resource = new FileResource(new File(basepath
                + Words.IMAGE_FOLDER_PATH + "/" + Words.PJURS_LOGO_IMAGE_NAME));

        return resource;
    }
}
