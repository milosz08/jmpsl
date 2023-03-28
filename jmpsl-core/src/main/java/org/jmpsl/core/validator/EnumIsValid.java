/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: EnumIsValid.java
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

import jakarta.validation.Valid;
import jakarta.validation.Payload;
import jakarta.validation.Constraint;

import org.springframework.web.bind.annotation.RequestBody;

import java.lang.annotation.*;

/**
 * Custom javax validation annotation which can be used for checked string in DTO request objects, if this string value
 * is the part of passed enum (by .class) identifier. This annotation must be use together with {@link Valid} annotation
 * in the {@link RequestBody} in sample controller method.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumIsValidValidator.class)
@Documented
public @interface EnumIsValid {
    Class<? extends Enum<?>> enumClazz();
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
