/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: AddedCookiePayload.java
 * Last modified: 14/10/2022, 21:17
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

package pl.miloszgilga.lib.jmpsl.util.cookie;

/**
 * POJO payload class for storing adding cookie value. Include cookie name, cookie value and maxAge property.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class AddedCookiePayload {
    private final String name;
    private final String value;
    private final int maxAge;

    /**
     * Constructor responsible for create {@link AddedCookiePayload} POJO class.
     *
     * @param name cookie name
     * @param value cookie value
     * @param maxAge cookie max age (life)
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public AddedCookiePayload(String name, String value, int maxAge) {
        this.name = name;
        this.value = value;
        this.maxAge = maxAge;
    }

    /**
     * @return cookie name parameter
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public String getName() {
        return name;
    }


    /**
     * @return cookie value parameter
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public String getValue() {
        return value;
    }

    /**
     * @return cookie max age (life) parameter
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public int getMaxAge() {
        return maxAge;
    }
}
