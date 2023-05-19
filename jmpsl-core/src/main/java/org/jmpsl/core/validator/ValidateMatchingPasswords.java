/*
 * Copyright (c) 2023 by MILOSZ GILGA <http://miloszgilga.pl>
 *
 * File name: ValidateMatchingPasswords.java
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

import jakarta.validation.Valid;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import org.springframework.web.bind.annotation.RequestBody;

import java.lang.annotation.*;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Custom javax validation annotation which can be used for checked if password and confirmed password fields are the
 * same. This annotation must be use together with {@link Valid} annotation in the {@link RequestBody} in sample
 * controller method and DTO class must implemeting {@link IPasswordValidatorModel} interface.
 *
 * @author Miłosz Gilga
 * @since 1.0.2_04
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordsMatchValidator.class)
@Documented
public @interface ValidateMatchingPasswords {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
