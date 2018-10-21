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
package com.pjkurs.vaadin.ui.menu;

import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.pjkurs.vaadin.views.system.MyModel;
import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;

/**
 *
 * @author Tmejs
 */
public class AdminMenuPanel<T extends MyModel> extends MyContainer<T> {

    public AdminMenuPanel(T model) {
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
        MenuItem kursyMenuItem = menu.addItem(Words.TXT_COURSES, null);

        kursyMenuItem.addItem(Words.TXT_OVERVIEW, (selectedItem) -> {
            ((AdminViewModel) getModel()).menuCoursesOverviewClicked();
        });

        kursyMenuItem.addItem(Words.TXT_ADD_NEW, (selectedItem) -> {
            ((AdminViewModel) getModel()).menuCoursesAddNewClicked();
        });
        
        //Kategorie
        //Podkategorie
        MenuItem categoriesMenuItem = menu.addItem(Words.TXT_CATEGORIES, (selectedItem) -> {
            ((AdminViewModel) getModel()).menuCategoriesClicked();
        });

        categoriesMenuItem.addItem(Words.TXT_SUB_CATEGORIES, (selectedItem) -> {
            ((AdminViewModel) getModel()).menuSubcategoriesClicked();
        });

//        //Wykładowcy
//        //Przegląd
//        //Dodaj nowego
//        MenuItem teachersMenuItem = menu.addItem(Words.TXT_TEACHERS, null);
//
//        teachersMenuItem.addItem(Words.TXT_OVERVIEW, (selectedItem) -> {
//            ((AdminViewModel) getModel()).menuTeachersOvervievClicked();
//        });
//        teachersMenuItem.addItem(Words.TXT_ADD_NEW, (selectedItem) -> {
//            ((AdminViewModel) getModel()).menuTeachersAddNewClicked();
//        });

        //Użytkownicy
        MenuItem users = menu.addItem(Words.TXT_USERS, (selectedItem) -> {
            ((AdminViewModel) getModel()).menuUsersClicked();
        });

        return menu;
    }

}
