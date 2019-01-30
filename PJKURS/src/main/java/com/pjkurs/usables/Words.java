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

import com.pjkurs.domain.Appusers;
import com.pjkurs.domain.Client;
import com.pjkurs.domain.Training;
import com.pjkurs.domain.TrainingFile;
import com.vaadin.server.Page;

/**
 * @author Tmejs
 */
public class Words {

    public static final String TXT_COURSES_OF_SUBCOURSE = "Podkategoria jest przypisana do:";
    public static final String TXT_NO_SELECTED_CATEGORIES = "Podkategoria nie jest przypisana do "
            + "żadnej kategori";
    public static final String TXT_COURSE_ADDED =
            "Dodano kurs";
    public static final String TXT_BIRTH_DATE = "Data urodzenia";
    public static final String TXT_DISCARD_CHANGES = "Anuluj zmiany";
    public static final String TXT_NOT_SAVED_CHECK_DATA = "Nie udało się zapisać zmian. Sprawdź " +
            "poprawność wpisanych pól";
    public static final String TXT_UNDO_SIGN_TO_COURS = "Wypisz się z kursu";
    public static final String TXT_CORRECTLY_UNSGNED = "Poprawnie wypisano";
    public static final String TXT_NOONES = "Żaden";
    public static final String TXT_NO_STATUS = "Bez statusu";
    public static final String TXT_PRICE = "Cena";
    public static final String TXT_DISCOUNTS = "Zniżki";
    public static final String TXT_DISCOUNT_NAME = "Nazwa zniżki";
    public static final String TXT_DISCOUNT_DISCOUNT_VALUE = "Wartość zniżki";
    public static final String TXT_DISCOUNT = "Przyznana zniżka";
    public static final String TXT_NOT_SET = "Nie ustawiono";
    public static final String TXT_IS_MONEY_DISCOUNT = "Zniżka pienieżna";
    public static final String TXT_AWARDED_DISCOUNT = "Przyznana zniżka";
    public static final String TXT_AWAITING_FOR_CONFIRMATION = "Oczekuje na akceptacje";
    public static final String TXT_APPLY_FOR_DISCOUNT = "Aplikuj o zniżkę";
    public static final String TXT_APPLY = "Aplikuj";
    public static final String TXT_REASON = "Powód";
    public static final String TXT_REASON_CANT_BE_EMPTY = "Wpisz powód przyznania zniżki";
    public static final String TXT_SELECT_DISCOUNT = "Wybierz zniżkę o którą aplikujesz";
    public static final String TXT_CORRECTLY_SEND = "Poprawnie wysłano";
    public static final String TXT_SOMETHIN_WENT_WRONG = "Coś poszło nie tak";
    public static final String TXT_ADMIN_AWAITING_FOR_CONF = "Zniżki oczekujące na akceptację";
    public static final String TXT_CLIETN_EMAIL = "Email klienta";
    public static final String TXT_DISCOUNT_CONFIRMATION = "Zatwierdzenie zniżki";
    public static final String TXT_USER = "Użytkownik";
    public static final String TXT_DISCOUNT_DESCRIPITON = "Opis zniżki";
    public static final String TXT_USER_DESCRIPTION = "Powód użytkownika";
    public static final String TXT_CONF_DEC_DESCRIPTION = "Powód zatwierdzenia/odrzucenia";
    public static final String TXT_CONFIRM = "Zatwierdź";
    public static final String TXT_SET_GRANT_DESCRIPTION = "Wpisz powód";
    public static final String TXT_CORRECT_VALUE = "Wprowadz poprawną wartość zniżki";
    public static final String TXT_DECLINE = "Odrzuć";
    public static final String TXT_START_TRAINING = "Rozpocznij szkolenie";
    public static final String TXT_STATUS = "Status";
    public static final String TXT_DETAILS = "Szczegóły";
    public static final String TXT_COURSE_SUM_OF_MONEY = "Szacowany wpływ";
    public static final String TXT_NO_ACTIVE_TRAININGS = "Brak aktywnych szkoleń";
    public static final String TXT_START_DATE = "Data rozpoczęcia";
    public static final String TXT_AVALIBLE_TERMS = "Terminy kursu";
    public static final String TXT_CAPTION = "Nazwa spotkania";
    public static final String TXT_END_DATE = "Data zakończenia";
    public static final String TXT_FILL_ALL_FIELDS = "Wypełnij wszystkie pola";
    public static final String TXT_START_TIME = "Wybierz czas rozpoczęcia";
    public static final String TXT_END_TIME = "Wybierz czas zakończenia";
    public static final String TXT_TRAINING_NAME = "Nazwa szkolenia";
    public static final String TXT_SELECT_TRAINING_ITEM = "Wybierz termin który chcesz usunąć";
    public static final String TXT_MY_TRAININGS = "Prowadzone szkolenia";
    public static final String TXT_COURSES_AWAITING_START = "Kursy oczekujące na rozpoczęcie";
    public static final String TXT_DELETE_TEACHER_FROM_COURSE = "Usuń wykładowcę ze szkolenia";
    public static final String TXT_SET_TEACHER = "Wybierz nauczyciela";
    public static final String TXT_ADD_TEACHER_TO_COURSE = "Dodaj wykładowcę do szkolenia";
    public static final String TXT_SELECT_TEACHER_TO_ADD = "Wybierz nauczyciela do dodania";
    public static final String TXT_IS_PAID = "Czy opłacono";
    public static final String TXT_MODIFY = "Modyfikuj";
    public static final String TXT_CLIENTS_IN_TRAINING = "Uczestnicy";
    public static final String TXT_YES = "Tak";
    public static final String TXT_NO = "Nie";
    public static final String TXT_DELETE_CLIENT_FROM_TRAINING = "Usuń ze szkolenia";
    public static final String TXT_TEACHER_STATUS = "Status wykładowcy";
    public static final String TXT_TRAININGS_BY_TEACHER = "Szkolenia prowadzone przez wykładowcę";
    public static final String TXT_FILE_NAME = "Nazwa pliku";
    public static final String TXT_ADD_NEW_FILE = "Dodaj nowy plik do szkolenia";
    public static final String TXT_ADD_NEW_FILE_TO_COURSE = "Dodaj nowy plik do szkolenia";
    public static final String SET_SELECTED_CATEGORIES = "Wybierz kategorie w których występuje "
            + "kurs";
    public static final String TXT_WANT_DISCOUNT = "Przysługuje ci zniżka? ";
    public static final String TXT_TRAINING_ADD_NAME = "Szkolenie kursu";
    public static final String IS_PERCENTAGE = "Czy zniżka %?";
    public static final String TXT_NEW_DISCOUNT = "Podaj powód aplikacji";
    public static final String TXT_DISCOUNT_IS_AWAINTING = "Zniżka oczekuje na akceptację";
    public static final String TXT_DISCOUNT_NOT_GRANTED = "Zniżka została odrzucona";
    public static final String TXT_PLACE_OF_BIRTH = "Miejsce urodzenia";
    public static final String TXT_VISIBLE_FOR_USERS = "Czy widoczny dla klientów";
    public static final String TXT_IS_CONTRACT_SIGNED = "Czy podpisany kontrakt";
    public static final String TXT_CONTRACT_BASE = "Typ zatrudnienia";
    public static final String TXT_DEANERY_UERS = "Pracownicy dziekanatu";
    public static final String TXT_ADMIN_GRANT = "Uprawnienia admina";
    public static final String TXT_ADD_NEW_DEANEMPLYE = "Dodaj nowego pracownika dziekanatu";
    public static final String TXT_INSERT_NEW_DEAN_EMPLOYEE = "Dodaj nowego pracownika dziekanatu";
    public static final String TXT_LOGED_AS = "Zalogowany jako";
    public static final String TXT_INFORM_ABOUT_START = "Poinformuj o spotkaniu organizacyjnym";
    public static final String TXT_SET_DISCOUNT = "Nadaj zniżkę";
    public static final String TXT_DETAILED_DESCRIPTION_FILE = "Plik ze szczegółowymi danymi";
    public static final String TXT_UPLOAD_DETAILED_DESC = "Dodaj plik ze szczegółowym opisem";
    public static final String TXT_DELETE_FILE = "Usuń plik";
    public static final String TXT_IS_CYCLIC = "Spotkanie cotygodniowe";
    public static final String TXT_WEEKS_TILL_END = "Liczba tygodni";
    public static final String TXT_SUM_OF_WORKEDTIME = "Liczba zrealizowanych godzin w szkoleniu";
    public static final String TXT_ADD_HOUR = "+1";
    public static final String TXT_MINUS_HOUR = "-1";
    public static final String TXT_TIME_IN_TRAINING = "Czas pracy";
    public static final String TXT_GENERATE_TIME = "Pokaż raport godzinowy";
    public static final String TXT_TIME_RAPORT = "Raport godzinowy";
    public static final String TXT_MEETING_NAME = "Nazwa spotkania";
    public static final String TXT_TIME_IN_HOURS = "Czas (godziny)";
    public static final String TXT_SUM = "Suma";
    public static final String TXT_END_TRAINING = "Zakończ szkolenie";
    public static final String TXT_TRAINING_ENDING = "Zakończenie szkolenia";
    public static final String TXT_IS_GRADUATING = "Czy pomyślnie zakończony kurs";
    public static final String TXT_ADD_CERTIFCATE = "Dodaj certyfikat";
    public static final String TXT_DOWNLOAD_CERTIFICATE = "Pobierz certyfikat";
    public static final String TXT_DELETE_CERTIFICATE = "Usuń certyfikat";
    public static final String TXT_IS_CLIENT_GRAD = "Ukończono";
    public static final String TXT_IS_CERTIFICATE_ADDED = "Dodany certyfikat";
    public static final String TXT_ENDED_COURSES = "Ukończone kursy";
    public static final String TXT_NO_GRADUATED_COURSES = "Brak ukończonych kursów";
    public static final String TXT_GRADUATED_COURSES = "Ukończone kursy";
    public static final String TXT_GET_CERTIFICATE = "Certyfikat";
    public static final String TXT_ADD_CERTIFICATE_FOR_ALL_GRADUATED_USERS = "Dodaj certyfikaty "
            + "dla wszytskich użytkowników którzy ukończyli kurs";
    public static final String TXT_STATISTICS = "Statystyki";
    public static final String TXT_START = "Od:";
    public static final String TXT_END = "Do:";
    public static final String TXT_UPDATE = "Odśwież";
    public static final String TXT_TRAININGS_IN_DATES = "Szkolenia uruchomione w danym okresie";
    public static final String TXT_CREATE_DATE = "Data utworzenia";
    public static final String TXT_USERS_REGISTERED_IN_TIME = "Użytkownicy zarejestrowani w danym "
            + "okresie";
    public static final String TXT_FILES = "Pliki";
    public static final String TXT_ADD_FILE = "Dodaj plik";
    public static final String TXT_INCOME = "Wpływ";
    public static final String TXT_COUNT_OF_PARTICIPANTS = "Liczba uczestników";
    public static final String TXT_COUNT_OF_GRADUATES = "Liczba absolwentów";
    public static final String TXT_ACTIVE_TRAININGS = "Trwające szkolenia";
    public static final String TXT_ACTUAL_DATA = "Dane do uruchomienia szkolenia";
    public static final String TXT_TERM = "Termin";
    public static final String TXT_SEND = "Wyślij";
    public static final String TXT_ADD_NEW_teRM = "Dodaj nowy termin do szkolenia";
    public static final String TXT_MODIFY_CLIENT_DATA = "Modyfikuj dane klienta";
    public static final String TXT_PRICE_WITHDISCOUNT = "Cena dla studentów i absolwentów PJATK";
    public static final String TXT_LOGIN = "Login";
    public static final String TXT_LOGIN_OBL = "Login*";
    public static final String TXT_RE_PASSWORD = "Powtórz hasło";
    public static final String TXT_RE_PASSWORD_OBL = "Powtórz hasło*";
    public static final String TXT_INCORECT_DATA = "Błędne dane";
    public static final String TXT_CORRECTLY_DELETED = "Poprawnie usunięto";
    public static final String TXT_TRAININGS = "Trwające szkolenia";
    public static final String TXT_IS_ACTIVE = "Konto aktywowane";
    public static final String TXT_INACTIVE = "Pokaż tylko nieaktywne konta";
    public static final String TXT_NOT_ACTIVATED = "Konto jest nieaktywne. Prosimy o kontakt z "
            + "administracją.";
    public static final String TXT_COURSE_INCOME = "Szacowany wpływ (cena - zniżki)";
    public static final String TXT_MAIL_CONTENT = "Treść maila";
    public static final String TXT_EMAIL_SENDED_TO_COURSE_INTERESTS = "Do osób "
            + "zainteresowanych szkoleniem wysłano wiadomość:";
    public static final String TXT_EMAIL_SENDED_TO_TRAINING_PARTICIPANTS = "Do członków szkolenia "
            + "wysłano email:";
    public static final String TXT_EMAIL_SENDED_TO_YOUR_MAIL = "Na skrzynkę pocztową wysłano "
            + "wiadomość:";
    public static final String TXT_NEW_EMAIL = "Wysłany email";
    public static final String TXT_ONLY_LOGGED_CAN_SIGN_TO_COURSE = "Do kursu mogą zapisywać się "
            + "tylko zarejestrowani użytkownicy!";
    public static final String TXT_SET_DATE = "Wybierz termin";
    public static final String TXT_ALREADY_ENDED_COURSES = "Zakończone szkolenia";
    public static final String TXT_CONFIRMATION_WINDOW = "Potwierdzenie";
    public static final String TXT_CANCEL = "Anuluj";
    public static final String TXT_DELETE_CATEGORY_TEXT = "Czy na pewno chcesz usunąć kategorię ";
    public static final String TXT_UNDO_SIGN_TEXT = "Czy na pewno chcesz się wypisać z "
            + "kursu?";
    public static final String TXT_DELETE_USER_TXT = "Czy na pewno chcesz usunąć użytkownika ";
    public static final String TXT_DELETE_CLIENT_FROM_COURSE_TEXT = "Czy na pewno chcesz usunąć "
            + "użytkownika z kursu?";
    public static final String TXT_DELETE_CLIENT_FROM_TRAINING_TXT = "Czy na pewno chcesz usunąć "
            + "użytkownika z treningu?";
    public static final String TXT_DELETE_TEACHER_FROM_TRAINING = "Czy na pewno chcesz usunąć "
            + "wykładowcę ze szkolenia?";
    public static final String TXT_CONFIRM_DELETE_DEANERY_USER = "Czy na pewno chcesz usunąć "
            + "pracownika dziekanatu?";
    public static final String TXT_CONFIRM_DELETE_FILE = "Czy na pewno chcesz usunąć plik ze "
            + "szkolenia?";
    public static final String TXT_CONFIRM_DELETE_DATE = "Czy na pewno chcesz usunąć termin?";
    public static final String TXT_CONFIRM_START_TRAINING = "Czy na pewno chcesz rozpocząć "
            + "szkolenie?";
    public static final String TXT_DELTE_DETAILED_DESC = "Czy na pewno chcesz usunąć plik ze "
            + "szczegółowym opisem kursu?";
    public static final String TXT_COURSE_DETAILED_DESCRIPTION = "Szczegółowy opis";
    public static final String TXT_TERM_NAME = "Nazwa spotkania";
    public static final String TXT_END_TRAINING_TEXT = "Czy na pewno chcesz zakończyć szkolenie?";
    public static final String TXT_EMAIL_OBL = "Email*";

    /*SQL QUERYS

            Selecty do bazy danych

         */
    public static String SQL_SELECT_COURSES_QUERY = "select * from pjkursdb.DOSTEPNE_KURSY_V";
    public static String SQL_SELECT_MY_COURSES_QUERY = "select * from pjkursdb.MOJE_KURSY_V";
    public static String SQL_SELECT_ARCHIVE_COURSES_QUERY = "select * from pjkursdb.KURSY_ARCH_V";
    public final static String SESSION_LOGIN_NAME = "SESSION_LOGED_USER";
    public final static String TXT_APP_NAME = "PJKURS";

    public final static String SESSION_LOGGED_LOGIN = "SESSION_LOGGED_LOGIN";
    public final static String SESSION_LOGGED_EMAIL = "SESSION_LOGGED_EMAIL";
    public static String SESSION_LOGGED_USER = "SESSION_LOGGED_USER";
    public static final String SESSION_ADMIN = "SESSION_ADMIN";

    public final static String TXT_MY_DATA = "Moje dane";
    public final static String TXT_LOGOUT = "Wyloguj";
    public final static String TXT_LOGGED_ASS = "Zalogowany jako";
    public final static String TXT_LOGIN_BUTTON = "Zaloguj";
    public final static String TXT_NAME = "Imie";
    public final static String TXT_EMAIL = "Email";
    public final static String IMAGE_FOLDER_PATH = "/WEB-INF/images";
    public final static String PJURS_LOGO_IMAGE_NAME = "PJKURSLogo.png";
    public final static String TXT_LOGO_NAME = "Logo PJKURS";
    public final static String TXT_LOGIN_TO_SERWIS = "Logowanie do serwisu";
    public final static String TXT_FORGOT_PASSWORD = "Reset hasła?";
    public final static String TXT_REGISTER = "Zarejestruj";
    public static String TXT_PASSWORD = "Hasło";
    public static String TXT_PASSWORD_OBL = "Hasło*";
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
    public static final String TXT_USED_EMAIL = "Email jest już zarejestrowany w serwisie";
    public static String TXT_PASSWORDS_MUST_MATCH = "Hasło musi zgadzać się z hasłem w polu "
            + "\"Powtórz hasło\"";
    public static String TXT_CORRECTLY_REGISTERED = "Poprawnie zarejestrowano";
    public static String TXT_BUTTON_COURSES = "Dostępne kursy";

    public static String TXT_NO_AVALIBLE_COURSES = "Brak dostępnych kursów";
    public static String TXT_COURSE_NAME = "Nazwa kursu";
    public static String TXT_COURSE_DESCRIPTION = "Krótki opis";
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
    public static String TXT_DESCRIPTION = "Opis";
    public static String TXT_ADD_NEW_COURSE = "Dodaj";
    public static String TXT_MIN_PERSON_NUMBER = "Minimalna liczba uczestników";
    public static String TXT_INSERT_NEW_TEACHER_DATA = "Wprowadź dane nowego wykładowcy";
    public static String TXT_SURRNAME = "Nazwisko";
    public static String CANT_OPEN_LOG_FILE = "Nie można otworzyć pliku do logowania";
    public static String TXT_MY_COURSES = "Moje kursy";
    public static String TXT_COURSE_LECTURER = "Wykładowca";
    public static String TXT_COURSE_PARTICIPANTS = "Liczba zapisanych osób";
    public static String TXT_SIGN_TO_COURSE = "Zapisz do kursu";
    public static String TXT_SEND_QUESTION = "Wyślij wiadomość";
    public static String TXT_MESSAGE = "Wiadomość: (minimum 20 znaków)";
    public static String TXT_CONTACT = "Kontakt";
    public static String TXT_PHONE_CONTACT = "Kontakt telefoniczny";
    public static String TXT_ARCHIVE_COURSES = "Archiwum Kursów";
    public static String TXT_CONTACT_NAME = "Polsko-Japońska Akademia Technik Komputerowych";
    public static String TXT_CONTACT_DESCRIPTION = "Zamiejscowy Wydział Informatyki i Wydział "
            + "Sztuki Nowych Mediów w Gdańsku";
    public static String TXT_CONTACT_ADDRESS = "80-045 Gdańsk, ul. Brzegi 55";
    public static String TXT_NEW_COURSES = "Nowe kursy";
    public static String TXT_ALL = "Wszystkie";
    public static String TXT_ONGOING_COURSES = "Trwające szkolenia";
    public static String TXT_ID = "ID";
    public static String TXT_COURSE_CATEGORY_NAME = "Kategoria";
    public static String TXT_COURSE_SUB_CATEGORY_NAME = "Podkategoria";
    public static String TXT_COURSE_STATUS = "Status kursu";
    public static String TXT_EDIT = "Edytuj";
    public static String TXT_CHANGED_DATA_SAVED = "Zapisano zmiany";
    public static String TXT_DURING_MODIFICATION = "Edytuj dane";
    public static String TXT_SAVE_DATA = "Zapisz";
    public static String TXT_CATEGORIES = "Kategorie";
    public static String TXT_SUB_CATEGORIES = "Podkategorie";
    public static String TXT_NO_SUBCATEGORIES = "Brak subkategorii";
    public static String TXT_INSERT_NEW_CATEGORY_DATA = "Wprowadź dane nowej kategorii";
    public static String TXT_CATEGORY_NAME = "Nazwa kategorii";
    public static String TXT_CATEGORY_DESCRIPTION = "Opis";
    public static String TXT_ADD = "Dodaj";
    public static String TXT_CORRECTRLY_SAVED = "Poprawnie zapisano";
    public static String TXT_DELETE = "Usuń";
    public static String TXT_NO_CATEGORIES = "Brak kategorii";
    public static String TXT_SELECT_CATEGORY = "Wybierz kategorię";
    public static String TXT_SUBCATEGORYNAME = "Podkategoria";
    public static String TXT_ADD_CATEGORY = "Przypisz do kategori";
    public static String TXT_SELECT_SUB_CATEGORY = "Wybierz Kategorię";
    public static String TXT_ERROR = "Błąd";

}
