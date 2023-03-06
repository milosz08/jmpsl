/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: DateIsBeforeValidator.java
 * Last modified: 02/11/2022, 14:32
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

package org.jmpsl.core.validator;

import lombok.extern.slf4j.Slf4j;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.time.LocalDate;

import org.jmpsl.core.DateTimeUtil;

/**
 * Custom validator class implementing javax constraint validator interface for checking, if passed string date value
 * is valid (after parse to {@link Date} object) and if this date is before the current date.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Slf4j
public class DateIsBeforeValidator implements ConstraintValidator<DateIsBefore, String> {

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
        final Optional<LocalDate> date = DateTimeUtil.deserializedLocalDate(dateString);
        if (date.isEmpty()) return false;
        if (Objects.isNull(dateString) || date.get().isAfter(LocalDate.now())) {
            log.error("Attempt to add date which is after the current date.");
            return false;
        }
        return true;
    }
}
