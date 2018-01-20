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
package com.pjkurs.domain;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tmejs
 */
public abstract class DBObject {

    //Klasy bazodanowe powinny dziedziczyć po tej klasie
    //Parametry ustawiane według java.sql.* 
    //Parametry powinny być public aby były widoczne dla Reflection
    /**
     * Funkcja mapująca Resultset uzyskany z bazy danych na obiekt klasy
     * dziedziczącej po DBObject
     *
     * @param <T> Klasa zwracanego obiektu
     * @param clazz Klasa zwracanaego obiektu
     * @param resultSet wynikowy resultset z bazy danych
     * @return Obiekt utworzony na podstawie Resultsetu
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public <T extends DBObject> T mapObject(Class clazz, ResultSet resultSet) throws SQLException, InstantiationException, IllegalAccessException {
        T object = (T) clazz.newInstance();
        for (Field field : object.getClass().getFields()) {
            try {
                field.set(object, resultSet.getObject(field.getName()));
            } catch (Exception e) {
                Logger.getLogger(this.getClass().toString()).log(Level.SEVERE, "Błąd mapowania pola w resultsecie", e);
            }
        }
        return object;
    }

}
