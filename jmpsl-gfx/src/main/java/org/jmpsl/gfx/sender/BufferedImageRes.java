/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: BufferedImageRes.java
 * Last modified: 01/11/2022, 16:04
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

package org.jmpsl.gfx.sender;

import lombok.*;

import org.jmpsl.file.BufferedFileRes;

/**
 * Simple POJO class extending {@link BufferedFileRes} class with additional <code>userHashCode</code> property
 * (for user images).
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BufferedImageRes extends BufferedFileRes {
    private String userHashCode;

    /**
     * Method available copy entire {@link BufferedImageRes} instance into another {@link BufferedImageRes}
     * instance.
     *
     * @param payload instance of {@link BufferedImageRes} POJO class
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public void copyObject(BufferedImageRes payload) {
        setBytesRepresentation(payload.getBytesRepresentation());
        setLocation(payload.getLocation());
        this.userHashCode = payload.userHashCode;
    }
}
