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

import com.pjkurs.usables.Words;
import com.pjkurs.vaadin.NavigatorUI;
import com.pjkurs.vaadin.views.models.AdminViewModel;
import com.pjkurs.vaadin.views.system.MyContainer;
import com.pjkurs.vaadin.views.system.MyModel;
import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
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
        menu.addItem(Words.TXT_TRAININGS, (selectedItem) -> {
            ((AdminViewModel) getModel()).menuTrainigsClicked();
        });

        //Kategorie
        menu.addItem(Words.TXT_CATEGORIES, (selectedItem) -> {
            ((AdminViewModel) getModel()).menuCategoriesClicked();
        });

        // menu.addItem(Words.TXT_ADMIN_AWAITING_FOR_CONF, (selectedItem) -> {
        //     ((AdminViewModel) getModel()).menuAwaitingDicountsClicked();
        // });

        menu.addItem(Words.TXT_TEACHERS, (selectedItem) -> {
            ((AdminViewModel) getModel()).menuTeachersOvervievClicked();
        });

        menu.addItem(Words.TXT_USERS, (selectedItem) -> {
            ((AdminViewModel) getModel()).menuUsersClicked();
        });

        if (NavigatorUI.getAdminLoginStatus() && NavigatorUI.getLoggedAdmin().admin_grant) {
            menu.addItem(Words.TXT_DEANERY_UERS, (s) -> {
                ((AdminViewModel) getModel()).deaneryUsersClicked();
            });

            menu.addItem(Words.TXT_STATISTICS, s -> {
                ((AdminViewModel) getModel()).statistickMenuClicked();
            });
        }

        return menu;
    }

}
