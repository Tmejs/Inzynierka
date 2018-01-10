/*
 * AWS PROJECT (c) 2017 by tmejs (mateusz.rzad@gmail.com)
 * --------------------------------------------------------
 * Niniejszy program chroniony jest prawem autorskim. Jego rozpowszechnianie bez wyraźnej zgody autora
 * jest zabronione. Jakakolwiek ingerencja w oprogramowanie bez upoważnienia autora,
 * w tym w szczególności jego modyfikacja lub nieuprawnione kopiowanie jest sprzeczne z prawem.
 * Wersja opracowana dla Domax Sp. z o.o. z siedzibą w Łężycach
 */
package com.pjkurs;

import com.pjkurs.domain.ArchiveCourse;
import com.pjkurs.domain.Course;
import com.pjkurs.domain.Client;
import com.pjkurs.domain.MyCourse;
import java.util.List;

/**
 *
 * @author Tmejs
 */
public interface InterfacePjkursDataProvider {

    /*
    Operacje z klientem
     */
    public Boolean registerNewClient(Client client);

    public Boolean updateClient(Client client);

    public Boolean loginClient(String login, String password);

    /*
    Operacje związane z przypisaniem klienta do kursu
     */
    public Boolean addClientToCourse(Client client, Course course);

    /*
    Operacje zwiazane z kursami
     */
    public List<Course> getAvalibleCourses();
    public List<MyCourse> getMyCourses();
    
    public List<ArchiveCourse> getArchiveCourses();

    public Boolean addnewCourse(Course newCourse);

    public Boolean updateCourse(Course course);

}
