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
package com.pjkurs.vaadin.ui.containers.menu;

import com.pjkurs.domain.Category;
import com.pjkurs.vaadin.views.system.MyModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.vaadin.ui.Component;
import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.models.MainViewModel;
import com.vaadin.ui.MenuBar;
import java.util.List;

/**
 *
 * @author Tmejs
 */
public class MainMenuPanel<T extends MyModel> extends MyContainer<T> {

    public MainMenuPanel(T model) {
        super(model);
    }

    @Override
    public Component buildView() {
        this.setSizeFull();

        MenuBar menu = new MenuBar();
        menu.setAutoOpen(true);

        //Kursy 
        //Przeglądaj
        //Dodaj nowy
        MenuBar.MenuItem kursyMenuItem = menu.addItem(Words.TXT_COURSES, (selectedItem) -> {
            ((MainViewModel) getModel()).coursesButtonClicked(null, selectedItem);
        });

        List<Category> categories = NavigatorUI.getDBProvider().getCategories();

        categories.forEach((category) -> {
            kursyMenuItem.addItem(category.name, (selectedItem) -> {
                ((MainViewModel) getModel()).coursesButtonClicked(category, selectedItem);
            });

        });

        if (NavigatorUI.getLoginStatus()) {
            if(NavigatorUI.getDBProvider().isUserTeacher(NavigatorUI.getLoggedUser())){
                //Okno prowadzonych szkoleń
                MenuBar.MenuItem myCoursyMenuItem = menu.addItem(Words.TXT_MY_TRAININGS,
                        (selectedItem) -> {
                    ((MainViewModel) getModel()).myTrainingsButtonClicked(selectedItem);
                });
            }

            //Okno moich kursów
            MenuBar.MenuItem myCoursyMenuItem = menu.addItem(Words.TXT_MY_COURSES, (selectedItem) -> {
                ((MainViewModel) getModel()).myCoursesButtonClicked(selectedItem);
            });

            MenuBar.MenuItem graduatedCourses = menu.addItem(Words.TXT_ENDED_COURSES, s -> {
                ((MainViewModel) getModel()).graduatedCourses();
            });


            //Okno moich danych
            MenuBar.MenuItem myData = menu.addItem(Words.TXT_MY_DATA, (selectedItem) -> {
                ((MainViewModel) getModel()).myDataButtonClicked();
            });


        }

        //Zakładka z kontaktem 
        MenuBar.MenuItem contactMenuItem = menu.addItem(Words.TXT_CONTACT, (selectedItem) -> {
            ((MainViewModel) getModel()).contactDataButtonClicked(selectedItem);
        });



        return menu;
    }

}
