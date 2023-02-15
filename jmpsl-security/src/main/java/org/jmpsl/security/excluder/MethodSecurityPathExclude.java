/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: MethodSecurityPathExclude.java
 * Last modified: 14/02/2023, 20:59
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

package org.jmpsl.security.excluder;

import org.springframework.web.bind.annotation.*;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Implement this annotation on REST method in Spring Controller, when you can exclude mapped path by this method from
 * Spring Security (also excluded from main JWT Spring Filter). This annotation MUST BE TOGETHER with one of the REST
 * annotation: {@link GetMapping}, {@link PostMapping}, {@link PutMapping}, {@link PatchMapping}, {@link DeleteMapping}.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Target(METHOD)
@Retention(CLASS)
public @interface MethodSecurityPathExclude {
}
