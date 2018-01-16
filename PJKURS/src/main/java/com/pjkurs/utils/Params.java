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
package com.pjkurs.utils;

import com.pjkurs.usables.Words;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Class handling configuration file with input parameters. Config file should
 * be named like (only small letters) *APP_NAME*.conf and should be in
 * *CONFIGURATION_FOLDER* or defined in ARGS[0]
 *
 * Variable Type has to be Object (Integer ok, int bad) but it don't need to be
 * defined Can have comments started by *COMMENT_CHAR*
 *
 * ConfigFile Schema: VARIABLE_NAME* *PARAMETER_VALUE_DELIMETER*
 * *VARIABLE_VALUE* *PARAMETER_TYPE_DELIMETER* *VAIRABLE_TYPE*
 *
 * Example config file: *COMMENT_CHAR* here is the comment
 * VERBOS_MODE->true;Boolean LOG_FILE_PATH->this_logger.log;String
 *
 *
 * @author Tmejs (mateusz.rzad@gmail.com)
 */
public class Params {

    //MustBe params
    //Application name
    public static final String APP_NAME = "PJKURS";

    //configuration folderss
    private static final String CONFIGURATION_FOLDER = "/WEB-INF/conf";

    //Delimeter in conf file
    private static final String PARAMETER_VALUE_DELIMETER = "->";

    //Type Delimeter in conf file
    private static final String PARAMETER_TYPE_DELIMETER = ";";

    private static final String COMMENT_CHAR = "#";

    class MyException extends Exception {

        @Override
        public String getMessage() {
            return "To jest mój błąd";
        }

    }

    //HashTable for not defined parameters
    public HashMap<String, Object> otherParams;

    /**
     * Applications params all should be public to set values from conffile cant
     * be finnal
     */
    //Path to log file
    public String LOG_FILE_PATH;

    //Path to configuration file
    private String CONF_FILE_PATH;

    //Do logs append file. If false then rewrite log
    public Boolean IS_FILE_APPEND = true;

    public String CONNECTION_STRING;

    //boolean string values
    public String TRUE_STRING_VALUE;
    public String FALSE_STRING_VALUE;

    public Params(String pathToConfigurationFile) throws Exception, IOException, MyException {

//        throw (new MyException());
        //Set path to configurationFile
        if (pathToConfigurationFile != null) {
            CONF_FILE_PATH = pathToConfigurationFile;
        }

        //Read and set AppParameters
        readAndSetParams();

        //check if setLogfile path
        checkAndSetLogFile();

    }
    
    
    //Without creating patyToConf file
    public Params() throws Exception, IOException, MyException {
        //Read and set AppParameters
        readAndSetParams();
    }
    
    /**
     * Read and set parameters by configFile by AppName
     */
    private void readAndSetParams() throws Exception, IOException {

        //Open buffered file
        BufferedReader bf = openBufferedReader();

        //Set parameters
        createAppParamsByConfigFile(bf);

    }

    /**
     * Check if defined log path file and set if not
     */
    private void checkAndSetLogFile() {

        //Check if defined logFile
        if (LOG_FILE_PATH == null) {
            //creating logFilePath
            LOG_FILE_PATH = createLogFilePath();
        }

    }

    /**
     * Set defined parameters in class or add them to "anonymous" hashmap
     *
     * @param bf BufferedFile
     */
    private void createAppParamsByConfigFile(BufferedReader bf) throws IOException {

        String txtLine;
        //Getting each line in config file
        while ((txtLine = bf.readLine()) != null) {
            try {
                //Check if line is comment
                if (!txtLine.startsWith(COMMENT_CHAR)) {
                    //Decode line
                    decodeConfigLine(txtLine);
                }
            } catch (Exception e) {
                System.err.println(Words.CONF_FILE_ERROR);
                e.printStackTrace();
            }
        }

    }

    /**
     * Decode line from config File
     *
     * @param line line from configFile
     */
    private void decodeConfigLine(String line) {

        //Check is line only Enter
        if (line.isEmpty()) {
            return;
        }

        //Patameter values
        String parameterName = null;
        String parameterValue = null;
        String parameterType = null;

        //choping line to get name,value and type
        //name
        parameterName = line.split(PARAMETER_VALUE_DELIMETER)[0];
        //System.err.println(parameterName);
        //System.err.println(line);
        //Check if type is defined
        if (line.split(PARAMETER_VALUE_DELIMETER)[1].contains(PARAMETER_TYPE_DELIMETER)) {
            //has defined type so split to get it
            parameterValue = line.split(PARAMETER_VALUE_DELIMETER)[1].split(PARAMETER_TYPE_DELIMETER)[0];
            parameterType = line.split(PARAMETER_VALUE_DELIMETER)[1].split(PARAMETER_TYPE_DELIMETER)[1];
        } else {
            //not defined type so only param vale
            parameterValue = line.split(PARAMETER_VALUE_DELIMETER)[1];
        }

        setParameter(parameterName, parameterValue, parameterType);
    }

    private void setParameter(String name, String value, String type) {

        Object convertedValue = null;

        try {
            //Check if field in ParamsClass exists
            this.getClass().getField(name);

            //try to convert to defined type
            convertedValue = tryToConvertValueToSpecifiedType(value, this.getClass().getField(name).getType());

            //Set param value in this
            this.getClass().getField(name).set(this, convertedValue);

        } catch (NoSuchFieldException e) {
            //Try to convert parameter value to defined type
            if (type != null) {
                try {
                    //Try to convert to defined type
                    Class convertedType = Class.forName("java.lang." + type);
                    convertedValue = tryToConvertValueToSpecifiedType(value, convertedType);
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
            //Not defined parameter in class.
            //Add this parameter to anonymous parameters table
            if (convertedValue != null) {
                //set converted value
                addToAnonymousParamsTable(name, convertedValue);
            } else {
                //Set String value
                addToAnonymousParamsTable(name, value);
            }

        } catch (IllegalArgumentException | IllegalAccessException ex) {
            System.err.println(Words.WRONG_DEFINED_PARAM);
            ex.printStackTrace();
        }
    }

    /**
     * Convert String to specified type
     *
     * @param value String value
     * @param type Final type to convert
     * @return
     */
    private Object tryToConvertValueToSpecifiedType(String value, Class type) {

        //Boolean
        if (type == Boolean.class) {
            return Boolean.valueOf(value);
        }

        //Integer
        if (type == Integer.class) {
            return Integer.valueOf(value);
        }

        //Double
        if (type == Double.class) {
            value = value.replace(",", ".");
            return Double.valueOf(value);
        }

        //Float 
        if (type == Float.class) {
            value = value.replace(",", ".");
            return Float.valueOf(value);
        }

        return type.cast(value);
    }

    /**
     * Add parameter to annonymous table
     *
     * @param paramName Parameter name
     * @param paramValue Parameter value
     */
    private void addToAnonymousParamsTable(String paramName, Object paramValue) {

        //Create new instance of params table
        if (this.otherParams == null) {
            otherParams = new HashMap<>();
        }

        //setParameter
        otherParams.put(paramName, paramValue);
    }

    /**
     * Open conf file BufferedReader
     *
     * @return Buffered reader
     */
    private BufferedReader openBufferedReader() throws Exception {

        //Open conf File
        File confFile = new File(getConfFilePath());

        //Check if conf file exists
        if (!confFile.exists()) {
            //No file
            throw new Exception(Words.NO_PARAMS_FILE + confFile.getAbsolutePath());
        }

        //everything ok, open buffered Reader
        return new BufferedReader(new FileReader(confFile));
    }

    /**
     * Get Config file name
     *
     * @return
     */
    private String getConfFilePath() {

        //if new conf is set then use it
        if (CONF_FILE_PATH != null) {
            return CONF_FILE_PATH;
        }

        return "./" + CONFIGURATION_FOLDER + "/" + APP_NAME.toLowerCase() + ".conf";
    }

    /**
     * Create log file Path
     *
     * @return Path to logFile
     */
    private String createLogFilePath() {
        return "./" + CONFIGURATION_FOLDER + "/" + APP_NAME.toLowerCase() + ".log";
    }

    /**
     * Get parameter value by Parameter name
     *
     * @param name parameter name
     * @return parameter value;
     */
    public Object getParameterValueByName(String name) {
        try {
            return this.getClass().getField(name).get(this);
        } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
            //No field like this defined in Class
        }

        return otherParams.get(name);
    }

}
