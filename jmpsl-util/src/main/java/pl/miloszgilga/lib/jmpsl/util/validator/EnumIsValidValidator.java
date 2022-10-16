/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: EnumIsValidValidator.java
 * Last modified: 15/10/2022, 19:53
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
import java.util.stream.*;

import static java.util.Objects.isNull;

/**
 * Custom validator class implementing javax constraint validator interface for checking, if string value passed in DTO
 * req objects is the part of declared enum value.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
class EnumIsValidValidator implements ConstraintValidator<EnumIsValid, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnumIsValidValidator.class);

    /**
     * @hidden
     */
    private Set<String> availableValues;

    /**
     * Override javax constraint validator method for load all available enum values into {@link Set} collection.
     *
     * @param constraintAnnotation annotation instance for a given constraint declaration
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    @Override
    public void initialize(final EnumIsValid constraintAnnotation) {
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
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (isNull(value) || !availableValues.contains(value.toLowerCase(Locale.ROOT))) {
            LOGGER.error("Attept to add not existing enum value (malformed enum string data for enum parser)." +
                    "Available values: {}, passed value: {}", availableValues, value);
            return false;
        }
        return true;
    }
}
