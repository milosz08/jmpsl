/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: DateTimeUtil.java
 * Last modified: 06/03/2023, 17:16
 * Project name: jmps-library
 *
 * Licensed under the MIT license; you may not use this file except in compliance with the License.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * THE ABOVE COPYRIGHT NOTICE AND THIS PERMISSION NOTICE SHALL BE INCLUDED IN ALL COPIES OR
 * SUBSTANTIAL PORTIONS OF THE SOFTWARE.
 *
 * The software is provided “as is”, without warranty of any kind, express or implied, including but not limited
 * to the warranties of merchantability, fitness for a particular purpose and noninfringement. In no event
 * shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an
 * action of contract, tort or otherwise, arising from, out of or in connection with the software or the use
 * or other dealings in the software.
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
