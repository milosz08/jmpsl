/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: EnumIsValidValidator.java
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
