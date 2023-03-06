/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: OAuth2SupplierValidator.java
 * Last modified: 19/10/2022, 22:42
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

package org.jmpsl.oauth2.validator;

import lombok.extern.slf4j.Slf4j;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.stream.Collectors;

import org.jmpsl.oauth2.OAuth2Supplier;
import org.jmpsl.oauth2.OAuth2AutoConfigurationLoader;

/**
 * Custom Javax Validator responsible for validate OAuth2 supplier passed in DTOs (as string). Validator checking
 * passed supplierName based available OAuth2 suppliers declared in <code>application.properties</code> file.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Slf4j
public class OAuth2SupplierValidator implements ConstraintValidator<ValidateOAuth2Supplier, String> {

    private static final Set<OAuth2Supplier> SUPPLIERS = OAuth2AutoConfigurationLoader.getAvailableOAuth2Suppliers();

    private Set<String> availableSuppliers;

    @Override
    public void initialize(ValidateOAuth2Supplier constraintAnnotation) {
        availableSuppliers = SUPPLIERS.stream().map(OAuth2Supplier::getSupplierName).collect(Collectors.toSet());
    }

    /**
     * Override javax constraint validator method for determinate valid OAuth2 supplier name. if passed supplier which
     * not include in application properties suppliers array, return false otherwise return true.
     *
     * @param supplierName object to validate
     * @param context context in which the constraint is evaluated
     * @return true, if passed supplier exist, otherwise false
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    @Override
    public boolean isValid(String supplierName, ConstraintValidatorContext context) {
        if (!availableSuppliers.contains(supplierName)) {
            log.error("Attempt refer to unexisting OAuth2 credentials supplier name. Supplier name: {}", supplierName);
            return false;
        }
        return true;
    }
}
