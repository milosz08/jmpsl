/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: ServletPathUtil.java
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
