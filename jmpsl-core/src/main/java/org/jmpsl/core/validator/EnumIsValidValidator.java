/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: EnumIsValidValidator.java
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
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited
 * to the warranties of merchantability, fitness for a particular purpose and noninfringement. In no event
 * shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an
 * action of contract, tort or otherwise, arising from, out of or in connection with the software or the use
 * or other dealings in the software.
 */

package org.jmpsl.core.validator;

import lombok.extern.slf4j.Slf4j;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.Collectors;

/**
 * Custom validator class implementing javax constraint validator interface for checking, if string value passed in DTO
 * req objects is the part of declared enum value.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Slf4j
class EnumIsValidValidator implements ConstraintValidator<EnumIsValid, String> {

    private Set<String> availableValues;

    /**
     * Override javax constraint validator method for load all available enum values into {@link Set} collection.
     *
     * @param constraintAnnotation annotation instance for a given constraint declaration
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    @Override
    public void initialize(EnumIsValid constraintAnnotation) {
        this.availableValues = Stream.of(constraintAnnotation.enumClazz().getEnumConstants())
            .map(v -> v.name().toLowerCase(Locale.ROOT))
            .collect(Collectors.toSet());
    }

    /**
     * Override javax constraint validator method for determinate valid string value based passed enum class. If string
     * value is valid, return true otherwise return false.
     *
     * @param value string to validate with enum values
     * @param context context in which the constraint is evaluated
     * @return false, if string value is not the part of enum, otherwise true
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(value) || !availableValues.contains(value.toLowerCase(Locale.ROOT))) {
            log.error("Attept to add not existing enum value (malformed enum string data for enum parser)." +
                "Available values: {}, passed value: {}", availableValues, value);
            return false;
        }
        return true;
    }
}
