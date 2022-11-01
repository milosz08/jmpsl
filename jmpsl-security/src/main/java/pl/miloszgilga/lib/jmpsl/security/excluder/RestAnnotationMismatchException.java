/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: RestAnnotationMismatchException.java
 * Last modified: 22/10/2022, 02:07
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

package pl.miloszgilga.lib.jmpsl.security.excluder;

/**
 * Custom exception invoke, when reflection loader found REST annotation, but annotation is invalid in selected case.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
class RestAnnotationMismatchException extends RuntimeException {

    RestAnnotationMismatchException(String annotations, String name) {
        super(String.format("To use @%s annotation, firstly declare one of the Rest controller annotations: %s.",
                name, annotations));
    }
}
