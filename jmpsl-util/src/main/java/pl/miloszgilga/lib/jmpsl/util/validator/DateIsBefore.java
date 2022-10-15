/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: DateIsBefore.java
 * Last modified: 15/10/2022, 16:18
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

import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import javax.validation.*;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Custom javax validation annotation which can be used for checked if passed date as string is valid (after parse into
 * {@link Date} object) and if this date is before the current date. This annotation must be use together with
 * {@link Valid} annotation in the {@link RequestBody} in sample controller method.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = DateIsBeforeValidator.class)
@Documented
public @interface DateIsBefore {
    String message();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
