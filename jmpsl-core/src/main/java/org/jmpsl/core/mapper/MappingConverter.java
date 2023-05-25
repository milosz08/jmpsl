/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: MappingConverter.java
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
