/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: ServletPathUtil.java
 * Last modified: 12/03/2023, 10:11
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
 * The software is provided “as is”, without warranty of any kind, express or implied, including but not limited
 * to the warranties of merchantability, fitness for a particular purpose and noninfringement. In no event
 * shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an
 * action of contract, tort or otherwise, arising from, out of or in connection with the software or the use
 * or other dealings in the software.
 */

package org.jmpsl.core;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Class storing static methods for utilities path servlet actions. Most of methods in this class include auto-inserting
 * {@link HttpServletRequest} and {@link HttpServletResponse} parameters by Tomcat Servlet Container.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class ServletPathUtil {

    private ServletPathUtil() {
    }

    /**
     * Static method responsible for building redirect message URI. Returning {@link URI} object, which contains
     * message query parameter with error query parameter.
     *
     * @param message redirect message parameter
     * @param redirectPageUri redirect address <i>(ex. http://localhost:8080/example)</i>
     * @param error parameter identifing redirect error
     * @return {@link URI} object <i>(ex. http://localhost:8080/example?message=test&amp;error=false)</i>
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if message or redirectPageUri parameters are null
     */
    public static URI redirectMessageUri(String message, String redirectPageUri, boolean error) {
        final List<QueryMap> queryParams = List.of(
            new QueryMap("message", message),
            new QueryMap("error", error));
        return generateBasicUri(queryParams, redirectPageUri);
    }

    /**
     * Static method responsible for building redirect token URI. Returning {@link URI} object, which contains
     * token query parameter with supplier token query parameter.
     *
     * @param token generated bearer token
     * @param redirectPageUri redirect address <i>(ex. http://localhost:8080/example)</i>
     * @param supplier token credentials supplier (ex. google, facebook, local etc.)
     * @return {@link URI} object <i>(ex. http://localhost:8080/example?token=fagg3g&amp;supplier=google)</i>
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if token, supplier or redirectPageUri parameters are null
     */
    public static URI redirectTokenUri(String token, String redirectPageUri, String supplier) {
        final List<QueryMap> queryParams = List.of(
            new QueryMap("token", token),
            new QueryMap("supplier", supplier));
        return generateBasicUri(queryParams, redirectPageUri);
    }

    /**
     * Static method responsible for building redirect error URI. Returning {@link URI} object, which contains only
     * error message parameter.
     *
     * @param message error message as string value
     * @param redirectPageUri redirect address <i>(ex. http://localhost:8080/example)</i>
     * @return {@link URI} object <i>(ex. http://localhost:8080/example?error=thisissampleerrormessage)</i>
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if message or redirectPageUri parameters are null
     */
    public static URI redirectErrorUri(String message, String redirectPageUri) {
        final List<QueryMap> queryParams = List.of(new QueryMap("error", message));
        return generateBasicUri(queryParams, redirectPageUri);
    }

    /**
     * Inside-class private method responsible for generate basic {@link URI} object from queryParams (as list of tuples)
     * and redirectPageUri string value.
     *
     * @param queryParams redirect uri query parameters
     * @param redirectPageUri redirect uri string value
     * @return builded {@link URI} based passed parameters
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if redirectPageUri string value are null
     */
    private static URI generateBasicUri(List<QueryMap> queryParams, String redirectPageUri) {
        Assert.notNull(redirectPageUri, "RedirectPageUri cannot be null.");
        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(redirectPageUri);
        for(final QueryMap param : queryParams) {
            Assert.notNull(param.value(), "Query parameter cannot be null.");
            uriComponentsBuilder.queryParam(param.name(), param.value());
        }
        return uriComponentsBuilder.build().toUri();
    }
}
