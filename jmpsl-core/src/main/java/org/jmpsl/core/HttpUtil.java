/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: HttpUtil.java
 * Last modified: 19/05/2023, 16:31
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
