/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pjkurs.utils;

import com.pjkurs.usables.Words;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class to log activities in application into predefined file
 *
 * @author Tmejs (mateusz.rzad@gmail.com)
 */
public class Logger {

    //Object to write to log file
    private BufferedWriter printWriter;

    private final Boolean verboseMode;

    //Columns delimeter in log file
    private final String LOG_COLUMNS_DELIMETER = "\t";

    //Date format 
    private final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public enum LogType {
        DEBUG,
        WARRNING,
        ERROR
    }

    public Logger(String logFilePath, Boolean appendFile, Boolean verboseMode) {

        try {
            //Create file writer to write logs throught it
            createFileWriter(logFilePath, appendFile);

        } catch (IOException ex) {
            //Cant open/create log file

            System.err.println(Words.CANT_OPEN_LOG_FILE);
            ex.printStackTrace();

            //Close app cause logger is important
            closeApplication();
        }
        this.verboseMode = verboseMode;
        
        addLog(this, LogType.DEBUG, " Logg initialized on " + logFilePath + "with verbose mode =" + verboseMode.toString());
        //Set verbose mode

    }

    public void closeApp() {
        closeApplication();
    }

    /**
     * Close application with error
     */
    private void closeApplication() {
        //Close printWriter
        try {
            printWriter.close();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        System.exit(-1);
    }

    /**
     * Open log file to write.
     *
     * @param pathToFile Path to log file
     * @param appendFile Do logs append file. If false then rewrite file.
     */
    private void createFileWriter(String pathToFile, Boolean appendFile) throws IOException {

        File logFile = new File(pathToFile);

        //Check if file exists
        if (!logFile.exists()) {
            //File not exists so create it
            logFile.createNewFile();
        }

        //Open File Writer
        FileWriter fw = new FileWriter(logFile, appendFile);
        printWriter = new BufferedWriter(fw);

    }

    /**
     * Add log
     *
     * @param cameFrom Object where log happend
     * @param logType LogType
     * @param exc Exception
     */
    public void addLog(Object cameFrom, LogType logType, Exception exc) {
        addLog(cameFrom, cameFrom.getClass(), logType, exc, null);
    }

    /**
     * Add log
     *
     * @param cameFrom Object where log happend
     * @param logType LogType
     * @param exc Exception
     */
    public void addLog(Class cameFrom, LogType logType, Exception exc) {
        addLog(null, cameFrom, logType, exc, null);
    }

    /**
     * Add log
     *
     * @param cameFrom Object where log happend
     * @param logType LogType
     * @param txt Info txt
     */
    public void addLog(Object cameFrom, LogType logType, String txt) {
        addLog(cameFrom, cameFrom.getClass(), logType, null, txt);
    }

    /**
     * Add log
     *
     * @param cameFrom Object where log happend
     * @param staticCameFrom Static class where log happend
     * @param logType LogType
     * @param exc Exception
     * @param txt Info txt
     */
    public void addLog(Object cameFrom, Class staticCameFrom, LogType logType, Exception exc, String txt) {
        //Debug type are not saved when verbose mode is off
        if (verboseMode == false && logType == LogType.DEBUG) {
            return;
        }

        //Create logString
        String logString = createLog(cameFrom, staticCameFrom, logType, exc, txt);

        //Write log into file
        try {
            addLogToFile(logString);
        } catch (IOException e) {
            System.err.println(Words.CANT_OPEN_LOG_FILE);
            e.printStackTrace();
        }

        //if Verbose is on then write info to console
        System.out.println(logString);

        //if error then close app
        if (logType == LogType.ERROR) {
            closeApplication();
        }

    }

    /**
     * Write log to file
     *
     * @param logTxt logTXT
     */
    synchronized private void addLogToFile(String logTxt) throws IOException {
        printWriter.write(logTxt);
        printWriter.newLine();
        printWriter.flush();

    }

    /**
     * Create log String
     *
     * @param commingFrom Object where log comes from
     * @param logType Type of log
     * @param exc Throwed exception
     * @param txt txt to debug
     * @return Created log string
     */
    private String createLog(Object commingFrom, Class staticCameFrom, LogType logType, Exception exc, String txt) {
        String logString;

        //get date
        Calendar cal = Calendar.getInstance();
        logString = DATE_FORMAT.format(cal.getTime());

        //Add log type
        logString = logString + LOG_COLUMNS_DELIMETER + logType.name();

        //Add Object from log came
        if (commingFrom != null) {
            //Empty object
            logString = logString + LOG_COLUMNS_DELIMETER + commingFrom.toString();
        } else if (staticCameFrom != null) {
            //Empty static field
            logString = logString + LOG_COLUMNS_DELIMETER + staticCameFrom.toString();
        } else {
            //No idea where log came from
            logString = logString + LOG_COLUMNS_DELIMETER + "log from  ?????";
        }

        //Add exceptions string
        if (exc != null) {
            logString = logString + LOG_COLUMNS_DELIMETER + exc.getMessage();
        }

        //Add info string
        if (txt != null) {
            logString = logString + LOG_COLUMNS_DELIMETER + txt;
        }

        return logString;

    }
}
