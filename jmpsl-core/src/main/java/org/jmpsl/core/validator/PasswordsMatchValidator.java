/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: PasswordsMatchValidator.java
 * Last modified: 18/05/2023, 00:08
 * Project name: air-hub-master-server
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

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import lombok.extern.slf4j.Slf4j;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Custom validator class implementing javax constraint validator interface for checking, if passed password and
 * confirmed password both are the same.
 *
 * @author Miłosz Gilga
 * @since 1.0.2_04
 */
@Slf4j
class PasswordsMatchValidator implements ConstraintValidator<ValidateMatchingPasswords, IPasswordValidatorModel> {

    @Override
    public boolean isValid(IPasswordValidatorModel value, ConstraintValidatorContext context) {
        if (!value.getPassword().equals(value.getConfirmedPassword())) {
            log.error("Password and confirmed passwords are not the same.");
            return false;
        }
        return true;
    }
}
