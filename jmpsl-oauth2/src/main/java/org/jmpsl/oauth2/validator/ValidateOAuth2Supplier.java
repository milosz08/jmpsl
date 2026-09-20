/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: ValidateOAuth2Supplier.java
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

package org.jmpsl.oauth2.validator;

import jakarta.validation.Valid;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import org.springframework.web.bind.annotation.RequestBody;

import java.lang.annotation.*;

import org.jmpsl.oauth2.OAuth2Supplier;

/**
 * Custom Javax validation annotation checking, if passed OAuth2Supplier property (as string) is one of the
 * {@link OAuth2Supplier} enum class properties. This annotation must be use together with {@link Valid} annotation in
 * the {@link RequestBody} in sample controller method.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OAuth2SupplierValidator.class)
@Documented
public @interface ValidateOAuth2Supplier {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
