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
package com.pjkurs.usables;

import com.vaadin.server.Page;

/**
 *
 * @author Tmejs
 */
public class Words {

    /*SQL QUERYS
        Selecty do bazy danych
    
     */
    public static String SQL_SELECT_COURSES_QUERY = "select * from pjkursdb.DOSTEPNE_KURSY_V";

    public final static String SESSION_LOGIN_NAME = "SESSION_LOGIN_NAME";
    public final static String TXT_APP_NAME = "Wgl sie nie nazywa jeszcez";

    public final static String SESSION_LOGGED_LOGIN = "SESSION_LOGGED_LOGIN";
    public final static String SESSION_LOGGED_EMAIL = "SESSION_LOGGED_EMAIL";

    public final static String TXT_MY_DATA = "Moje dane";
    public final static String TXT_LOGOUT = "Wyloguj";
    public final static String TXT_LOGGED_ASS = "Zalogowany jako";
    public final static String TXT_LOGIN_BUTTON = "Zaloguj";
    public final static String TXT_NAME = "Imie:";
    public final static String TXT_EMAIL = "Email:";
    public final static String IMAGE_FOLDER_PATH = "/WEB-INF/images";
    public final static String PJURS_LOGO_IMAGE_NAME = "PJKURSLogo.png";
    public final static String TXT_LOGO_NAME = "Logo PJKURS";
    public final static String TXT_LOGIN_TO_SERWIS = "Logowanie do serwisu";
    public final static String TXT_FORGOT_PASSWORD = "Reset hasła?";
    public final static String TXT_REGISTER = "Zarejestruj";
    public static String TXT_PASSWORD = "Hasło";
    public static String WARRNING_WRONG_EMAIL = "Błędny email";
    public static String CONF_FILE_ERROR = "Błąd pliku konfiguracyjnego";
    public static String WRONG_DEFINED_PARAM = "Błędnie skonfigurowany parametr";
    public static String NO_PARAMS_FILE = "Brak pliku z parametrami";
    public static String TXT_CORRECTLY_LOGGED_OUT = "Poprawnie wylogowano";

    public final static String DB_NAME = "pjkursdb";
    public final static String LOGIN = "root";
    public final static String HASLO = "root";
    public final static String TXT_CORRECTLY_LOGGED = "Poprawnie zalogowano";
    public static String TXT_WRONG_LOGIN_DATA = "Błędne dane";
    public static String TXT_BACK = "Powrót";
    public static final String TXT_USED_EMAIL = "Już używany email";
    public static String TXT_PASSWORDS_MUST_MATCH = "Hasła muszą się zgadzać";
    public static String TXT_CORRECTLY_REGISTERED = "Poprawnie zarejestrowano";
    public static String TXT_BUTTON_COURSES = "Dostępne kursy";

    public static String TXT_NO_AVALIBLE_COURSES = "Brak dostępnych kursów";
    public static String TXT_COURSE_NAME = "Nazwa";
    public static String TXT_COURSE_DESCRIPTION = "Opis:";
    public static String TXT_COURSE_ADD_TO_COURSE = "Zapisz do kursu";
}
