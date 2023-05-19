/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: HttpUtil.java
 * Last modified: 19/05/2023, 15:36
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

package org.jmpsl.core;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Utilities static class for Http context utilities methods.
 *
 * @author Miłosz Gilga
 * @since 1.0.2_04
 */
public class HttpUtil {

    private HttpUtil() {
    }

    /**
     * Static method responsible for returning full host address based {@link HttpServletRequest} object passed in
     * method argument. Sample return: <code>https://example.net:8080</code>
     *
     * @param req {@link HttpServletRequest} object
     * @return parsed base full host address path
     * @author Miłosz Gilga
     * @since 1.0.2_04
     */
    public static String getBaseReqPath(HttpServletRequest req) {
        final boolean isHttp = req.getScheme().equals("http") && req.getServerPort() == 80;
        final boolean isHttps = req.getScheme().equals("https") && req.getServerPort() == 443;
        return req.getScheme() + "://" + req.getServerName() + (isHttp || isHttps ? "" : ":" + req.getServerPort());
    }
}
