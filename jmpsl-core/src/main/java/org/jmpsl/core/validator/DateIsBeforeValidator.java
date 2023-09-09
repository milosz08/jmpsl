/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: DateIsBeforeValidator.java
 * Last modified: 18/05/2023, 01:14
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
class DateIsBeforeValidator implements ConstraintValidator<DateIsBefore, String> {

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
