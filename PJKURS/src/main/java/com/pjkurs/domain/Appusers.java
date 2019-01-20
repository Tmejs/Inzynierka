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

import java.sql.Date;

/**
 *
 * @author Tmejs
 */
public class Appusers extends DBObject{

    public Integer id;
    public String password ;
    public String email;
    public Date create_date;
    public String name;
    public String surname;
    public Date birth_date;
    public String contact_number;
    public Boolean isActive;

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public String place_of_birth;


    public Appusers() {
    }

    public Appusers(Appusers user) {
        this.id = user.id;
        this.password  = user.password;
        this.email = user.email;
        this.create_date = user.create_date;
        this.name = user.name;
        this.surname = user.surname;
        this.birth_date = user.birth_date;
        this.contact_number = user.contact_number;
    }

    public Integer getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public String getContact_number() {
        return contact_number;
    }
}
