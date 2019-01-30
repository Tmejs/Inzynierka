package com.pjkurs.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.pjkurs.domain.Appusers;
import com.pjkurs.domain.GraduatedCourse;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is to create a PDF file.
 */
public class PDFCreator {
    private final static String certificateFile = "certificateTemp.html";

    public static String getPdfCertificate(Appusers user, GraduatedCourse course) throws Exception{
        //dodać string czytania templatu
        String template = readTemplate();
        String date = new SimpleDateFormat("YYYY-MM-dd")
                .format(new Date(course.getEndDate().getTime()));
        String converted = fillFields(template, user.name, user.surname, date, course.name);

        // dodac url
        String fileName = user.id + "_" + course.id + "_cert.pdf";
        String url = "url" + fileName;
        generateFile(converted, url);
        return fileName;
    }

    private static String readTemplate() throws Exception {
        File file = FilesUitl.getFile(certificateFile);
        String content = "";
        FileReader fileStream = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileStream);
        String line = null;

        while ((line = bufferedReader.readLine()) != null) {
            content += line;
        }
        return content;
    }

    private static void generateFile(String k, String url) {
        // wykorzystac filewriter?
        try {
            OutputStream file = new FileOutputStream(new File(url));

            // step 1
            Document document = new Document();
            // step 2
            PdfWriter writer = PdfWriter.getInstance(document, file);
            // step 3
            document.open();


            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();

            InputStream is = new ByteArrayInputStream(k.getBytes());
            worker.parseXHtml(writer, document, is);
            // step 5
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String fillFields(String template, String name, String surname, String date, String certificateName) {
        String k = template;

        k = k.replaceAll("#NAME#", name);
        k = k.replaceAll("#SURNAME#", surname);
        k = k.replaceAll("#DATE#", name + " " + date);
        k = k.replaceAll("#COURSENAME#", name + " " + certificateName);
        System.out.println(k);
        return k;
    }
}