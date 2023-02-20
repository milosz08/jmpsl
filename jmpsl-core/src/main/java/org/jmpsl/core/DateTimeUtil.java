/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: DateTimeUtil.java
 * Last modified: 20/02/2023, 01:57
 * Project name: jmps-library
 *
 * Licensed under the MIT license; you may not use this file except in compliance with the License.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * THE ABOVE COPYRIGHT NOTICE AND THIS PERMISSION NOTICE SHALL BE INCLUDED IN ALL
 * COPIES OR SUBSTANTIAL PORTIONS OF THE SOFTWARE.
 */

package org.jmpsl.core;

import org.slf4j.*;

import java.util.*;
import java.time.*;
import java.time.format.*;

import static org.springframework.util.Assert.notNull;
import static java.time.format.DateTimeFormatter.ofPattern;

/**
 * Utility class added additional static methods for management time in servlet applications. Include adding minutes,
 * hours to current or passed {@link LocalDate} or {@link LocalDateTime} object and convert string date notation to
 * object. Class was prepared for datetime format: <code>yyyy-MM-dd HH:mm:ss</code> and date format:
 * <code>dd/MM/yyyy</code>.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class DateTimeUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateTimeUtil.class);
    private static final DateTimeFormatter DATE_FORMATTER = ofPattern("dd/MM/yyyy");

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
        notNull(date, "Passed date string notation cannot be null.");
        try {
            return Optional.of(LocalDate.parse(date, DATE_FORMATTER));
        } catch (DateTimeParseException ex) {
            LOGGER.error("Unable to parse date string to object base date string parameter: {}" + date);
        }
        return Optional.empty();
    }
}
