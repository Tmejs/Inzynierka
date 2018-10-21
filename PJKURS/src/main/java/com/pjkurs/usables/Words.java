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
 * @author Tmejs
 */
public class Words {

    public static final Integer INTEGER_MINIMUM_USER_NUMBBER_AVALIBLE = 20;
    public static final String TXT_COURSES_OF_SUBCOURSE = "Podkategoria jest przypisana do:";
    public static final String TXT_NO_SELECTED_CATEGORIES = "Podkategoria nie jest przypisana do żadnej kategori";
    public static final String TXT_COURSE_ADDED =
            "Dodano kurs";
    public static final String TXT_BIRTH_DATE = "Data urodzenia:";
    public static final String TXT_DISCARD_CHANGES = "Anuluj zmiany";
    public static final String TXT_NOT_SAVED_CHECK_DATA = "Nie udało się zapisać zmian. Sprawdz " +
            "poprawność wpisanych pól";
    public static final String TXT_UNDO_SIGN_TO_COURS = "Wypisz się z kursu";
    public static final String TXT_CORRECTLY_UNSGNED = "Poprawnie wypisano";
    public static final String TXT_NOONES = "Żaden";
    public static final String TXT_NO_STATUS = "Bez statusu";
    public static final String TXT_PRICE = "Cena";
    /*SQL QUERYS

            Selecty do bazy danych

         */
    public static String SQL_SELECT_COURSES_QUERY = "select * from pjkursdb.DOSTEPNE_KURSY_V";
    public static String SQL_SELECT_MY_COURSES_QUERY = "select * from pjkursdb.MOJE_KURSY_V";
    public static String SQL_SELECT_ARCHIVE_COURSES_QUERY = "select * from pjkursdb.KURSY_ARCH_V";
    public final static String SESSION_LOGIN_NAME = "SESSION_LOGED_USER";
    public final static String TXT_APP_NAME = "Wgl sie nie nazywa jeszcez";

    public final static String SESSION_LOGGED_LOGIN = "SESSION_LOGGED_LOGIN";
    public final static String SESSION_LOGGED_EMAIL = "SESSION_LOGGED_EMAIL";
    public static String SESSION_LOGGED_USER = "SESSION_LOGGED_USER";

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
        public final static String LOGIN = "pjkurs";
    public final static String HASLO = "pjkurs";

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
    public static String TXT_COURSE_DETAILS = "Szczegóły";
    public static String TXT_ADMINISTRATION = "Administracja";
    public static String TXT_COURSES = "Kursy";
    public static String TXT_OVERVIEW = "Przegląd";
    public static String TXT_ADD_NEW = "Dodaj nowy";
    public static String TXT_TEACHERS = "Wykładowcy";
    public static String TXT_USERS = "Użytkownicy";
    public static String TXT_FIND = "Szukaj";
    public static String TXT_INSERT_NEW_COURSE_DATA = "Wprowadz dane nowego kursu";
    public static String TXT_DESCRIPTION = "Opis:";
    public static String TXT_ADD_NEW_COURSE = "Dodaj";
    public static String TXT_MIN_PERSON_NUMBER = "Minimalna ilość uczestników";
    public static String TXT_INSERT_NEW_TEACHER_DATA = "Wprowadz dane nowego wykładowcy";
    public static String TXT_SURRNAME = "Nazwisko";
    public static String CANT_OPEN_LOG_FILE = "Nie mozna otworzyć pliku do logowania";
    public static String TXT_MY_COURSES = "Moje kursy";
    public static String TXT_COURSE_LECTURER = "Wykładowca";
    public static String TXT_COURSE_PARTICIPANTS = "Ilość członków";
    public static String TXT_SIGN_TO_COURSE = "Zapisz do kursu";
    public static String TXT_SEND_QUESTION = "Wyslij wiadomość";
    public static String TXT_MESSAGE = "Wiadomość: (minimum 20 znaków)";
    public static String TXT_CONTACT = "Kontakt";
    public static String TXT_PHONE_CONTACT = "Kontakt";
    public static String TXT_ARCHIVE_COURSES = "Archiwum Kursów";
    public static String TXT_CONTACT_NAME = "Polsko-Japońska Akademia Technik Komputerowych";
    public static String TXT_CONTACT_DESCRIPTION = "Zamiejscowy Wydział Informatyki i Wydział Sztuki Nowych Mediów w Gdańsku";
    public static String TXT_CONTACT_ADDRESS = "80-045 Gdańsk, ul. Brzegi 55";
    public static String TXT_NEW_COURSES = "Nowe kursy";
    public static String TXT_ALL = "Wszystkie";
    public static String TXT_ONGOING_COURSES = "Trwające";
    public static String TXT_ID = "ID";
    public static String TXT_COURSE_CATEGORY_NAME = "Kategoria";
    public static String TXT_COURSE_SUB_CATEGORY_NAME = "Podkategoria";
    public static String TXT_COURSE_STATUS = "Status kursu";
    public static String TXT_EDIT = "Edytuj";
    public static String TXT_CHANGED_DATA_SAVED = "Zapisano zmiany";
    public static String TXT_DURING_MODIFICATION = "Edytuj dane";
    public static String TXT_SAVE_DATA = "ZAPISZ";
    public static String TXT_CATEGORIES = "Kategorie";
    public static String TXT_SUB_CATEGORIES = "Podkategorie";
    public static String TXT_NO_SUBCATEGORIES = "Brak subkategorii";
    public static String TXT_INSERT_NEW_CATEGORY_DATA = "Wprowadz dane nowej kategorii";
    public static String TXT_CATEGORY_NAME = "Nazwa kategorii";
    public static String TXT_CATEGORY_DESCRIPTION = "Opis";
    public static String TXT_ADD = "Dodaj";
    public static String TXT_CORRECTRLY_SAVED = "Poprawnie zapisano";
    public static String TXT_DELETE = "Usuń";
    public static String TXT_NO_CATEGORIES = "Brak kategori";
    public static String TXT_SELECT_CATEGORY = "Wybierz kategorię";
    public static String TXT_SUBCATEGORYNAME = "Podkategoria";
    public static String TXT_ADD_CATEGORY = "Przypisz do podkategorii";
    public static String TXT_SELECT_SUB_CATEGORY = "Wybierz podkategorie";
    public static String TXT_ERROR = "Błąd";


}
