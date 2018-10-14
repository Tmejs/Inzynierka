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

import java.awt.datatransfer.StringSelection;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author Tmejs
 */
public class CourseSubCategory extends SubCategory {

    public Integer course_Id;

    public String getCategoryName() {
        return getCategories().stream().map(category -> category.name).collect(Collectors.joining(","));
    }

}
