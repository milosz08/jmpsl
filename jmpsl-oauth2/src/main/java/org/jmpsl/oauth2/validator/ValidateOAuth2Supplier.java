/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: ValidateOAuth2Supplier.java
 * Last modified: 19/10/2022, 22:40
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

import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.*;
import java.lang.annotation.*;

import org.jmpsl.oauth2.OAuth2Supplier;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Custom Javax validation annotation checking, if passed OAuth2Supplier property (as string) is one of the
 * {@link OAuth2Supplier} enum class properties. This annotation must be use together with {@link Valid} annotation in
 * the {@link RequestBody} in sample controller method.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = OAuth2SupplierValidator.class)
@Documented
public @interface ValidateOAuth2Supplier {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
