/*
 * Copyright (c) 2022 by MILOSZ GILGA <https://miloszgilga.pl>
 *
 * File name: StringManipulator.java
 * Last modified: 11/10/2022, 18:32
 * Project name: jmps-library
 *
 * Licensed under the GNU GPL 3.0 license; you may not use this file except in compliance with the License.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * THE ABOVE COPYRIGHT NOTICE AND THIS PERMISSION NOTICE SHALL BE INCLUDED IN ALL
 * COPIES OR SUBSTANTIAL PORTIONS OF THE SOFTWARE.
 */

package pl.miloszgilga.jmpsl.util;

import org.springframework.stereotype.Component;

/**
 * Simple testing class only for library deployment purposes.
 *
 * @author Miłosz Gilga
 * @version 1.0.0
 */

@Component
public class StringManipulator {

    /**
     * Simple method allows to concat two strings. Method only for
     * checking library deployment purposes
     *
     * @param a first string
     * @param b second string
     * @return String after concat with blank space between a and b parameters
     * @throws NullPointerException if parameter a or b is null
     */
    public String concat(String a, String b) {
        if (a == null || b == null) throw new NullPointerException("a or b parameters can not be null");
        return a + " " + b;
    }
}
