/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: ContentType.java
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

package org.jmpsl.file;

import lombok.Getter;
import lombok.AllArgsConstructor;

/**
 * Enum set of file content types in "type/notation" (ex. image/jpeg, image/png etc.).
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Getter
@AllArgsConstructor
public enum ContentType {

    /**
     * Content type for png image.
     *
     * @since 1.0.2
     */
    PNG("image/png", "png"),

    /**
     * Content type for jpeg image.
     *
     * @since 1.0.2
     */
    JPEG("image/jpeg", "jpeg"),

    /**
     * Content type for jpg image.
     *
     * @since 1.0.2
     */
    JPG("image/jpg", "jpg");

    /**
     * Content type file format (image/png, image/jpeg etc.).
     *
     * @since 1.0.2
     */
    private final String contentTypeName;

    /**
     * Regular extension name (png, jpeg, etc.).
     *
     * @since 1.0.2
     */
    private final String regularName;
}
