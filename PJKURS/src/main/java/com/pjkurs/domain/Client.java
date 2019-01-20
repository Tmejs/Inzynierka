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

/**
 *
 * @author Tmejs
 */
public class Client extends Appusers {

    public Boolean is_paid;
    public Boolean is_contract_signed;

    public Boolean getIs_contract_signed() {
        return is_contract_signed;
    }

    public void setIs_contract_signed(Boolean is_contract_signed) {
        this.is_contract_signed = is_contract_signed;
    }

    public Boolean getPaid() {
        return is_paid;
    }

    public void setPaid(Boolean paid) {
        is_paid = paid;
    }
}
