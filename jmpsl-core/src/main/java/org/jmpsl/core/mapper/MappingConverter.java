/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: MappingConverter.java
 * Last modified: 02/11/2022, 14:32
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

package org.jmpsl.core.mapper;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * Insert this annotation on custom converter class to automatically load at application start (and insert in Spring
 * context). CLASS MUST BE PUBLIC!
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface MappingConverter {
}
