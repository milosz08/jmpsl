/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: DateTimeUtil.java
 * Last modified: 28/03/2023, 23:14
 * Project name: jmps-library
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     <http://www.apache.org/license/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the license.
 */

package org.jmpsl.core;

import lombok.extern.slf4j.Slf4j;

import org.springframework.util.Assert;

import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class added additional static methods for management time in servlet applications. Include adding minutes,
 * hours to current or passed {@link LocalDate} or {@link LocalDateTime} object and convert string date notation to
 * object. Class was prepared for datetime format: <code>yyyy-MM-dd HH:mm:ss</code> and date format:
 * <code>dd/MM/yyyy</code>.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Slf4j
public class DateTimeUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private DateTimeUtil() {
    }

    /**
     * Static method responsible for deserialize date from string value. Acceptable date format to serialized is
     * <code>dd/MM/yyyy</code>. In other formats, method return empty {@link Optional} object. Otherwise return
     * {@link LocalDate} as object.
     *
     * @param date passed string date to deserialize (pattern: <code>dd/MM/yyyy</code>)
     * @return {@link Optional} with date, if deserialization ended successful, otherwise return empty {@link Optional}
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if passed date string notation is null
     */
    public static Optional<LocalDate> deserializedLocalDate(String date) {
        Assert.notNull(date, "Passed date string notation cannot be null.");
        try {
            return Optional.of(LocalDate.parse(date, DATE_FORMATTER));
        } catch (DateTimeParseException ex) {
            log.error("Unable to parse date string to object base date string parameter: {}" + date);
        }
        return Optional.empty();
    }
}
