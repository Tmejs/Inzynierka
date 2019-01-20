package com.pjkurs.usables;

import com.pjkurs.domain.Appusers;
import com.pjkurs.domain.CalendarEvent;
import com.pjkurs.domain.Course;
import com.pjkurs.domain.Training;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EmailsGenerator {

    public static MailObject getRegistrationMessage(Appusers user) {
        String title = "Witamy w serwisie PJKURS ";
        String body = "<html>Witaj " + user.getName() + "<br>" +
                "Dziekujemy za rejestrację w naszym serwisie"
                + "</html>";
        return new MailObject(user.getEmail(), title, body);
    }

    public static MailObject getAddedToCourseMessage(Appusers appuser, Course course) {
        String title = "Zapisanie do kursu w serwisie PJKURS ";
        String body = "<html>Witaj " + appuser.getName() + ".<br>" +
                "Dziękujemy za zapisanie się do kursu \"" + course.getName() + "\"<br>"
                + "Dostaniesz kolejną informację gdy kurs będzie miał się rozpocząć.<br>"
                + "Prosimy o kontakt, jeśli przysługuje tobie zniżka na kurs.<br>"
                + "</html>";

        return new MailObject(appuser.getEmail(), title, body);
    }

    public static MailObject getDeletedeFromCourseMessage(Appusers appuser, Course course) {
        return null;
    }

    public static MailObject getStartingTrainingMessage(Course course, Date startingDate,
            String time) {
        String title = "Uruchomienie kursu \"" + course.getName() + "\"";
        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");

        String body =
                "<html> Witaj,<br> Zaplanowano spotkanie organizacyjne dla kursu \"" + course
                        .getName() + "\"" +
                        " na dzień " + dt1.format(startingDate) + " " + time +
                        ".<br>Prosimy o pojawienie się na spotkaniu z wydrukowanym potwierdzeniem "
                        + "płatności.<br><br> Portal PJKURS</html>";

        return new MailObject(title, body);
    }

    public static MailObject generateContactEmail(String emailTo, String emailFrom, String body) {
        String title = "Pytanie od użytkownika portalu PJKURS";
        String bodytext = "<html> Uzytkownik o adresie email " + emailFrom + " wysłał następujące "
                + "pytanie:<br>" + body +"</html>";

        return new MailObject(emailTo, title, bodytext);

    }

    public static MailObject getAddedTrainingTermMessage(Training training,
            CalendarEvent calendarEvent) {
        return null;
    }

    public static MailObject getDeletedTrainingTermMessage(Training training,
            CalendarEvent calendarEvent) {
        return null;
    }

    public static MailObject getTrainingEndingMessage(Appusers appuser, Course course) {
        String title = "Zakończenie kursu w serwisie PJKURS ";
        String body = "<html>Witaj " + appuser.getName() + ".<br>" +
                "Zakończył się kurs  \"" + course.getName() + ".\"<br>"
                + "Certyfikat ukończenia kursu możesz znalezć w zakłądcę \"Zakończone kursy\" "
                + "w portalu.<br>"
                + "</html>";

        return new MailObject(appuser.getEmail(), title, body);
    }

    public static MailObject getCertificateAddedMessage(Appusers appuser, Course course) {
        return null;
    }
}
