/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: DateIsBeforeValidator.java
 * Last modified: 15/10/2022, 16:18
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

package pl.miloszgilga.lib.jmpsl.util.validator;

import org.slf4j.*;

import java.util.*;
import javax.validation.*;

import static java.util.Objects.isNull;

import pl.miloszgilga.lib.jmpsl.util.TimeUtil;

/**
 * Custom validator class implementing javax constraint validator interface for checking, if passed string date value
 * is valid (after parse to {@link Date} object) and if this date is before the current date.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class DateIsBeforeValidator implements ConstraintValidator<DateIsBefore, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateIsBeforeValidator.class);

    /**
     * Override javax constraint validator method for determinate valid date. if passed date is before the current date
     * and date is valid, return true otherwise return false.
     *
     * @param dateString object to validate
     * @param context context in which the constraint is evaluated
     * @return true, if passed date is valid and before the current date, otherwise false
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    @Override
    public boolean isValid(String dateString, ConstraintValidatorContext context) {
        final Optional<Date> date = TimeUtil.deserialize(dateString);
        if (date.isEmpty()) return false;
        if (isNull(dateString) || TimeUtil.isNonExpired(date.get())) {
            LOGGER.error("Attempt to add date which is after the current date.");
            return false;
        }
        return true;
    }
}
