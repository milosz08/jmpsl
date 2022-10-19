/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: OAuth2CustomTokenConverter.java
 * Last modified: 18/10/2022, 20:28
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

package pl.miloszgilga.lib.jmpsl.oauth2;

import org.springframework.util.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;

import java.util.*;
import java.util.stream.*;

import static org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.*;

/**
 * OAuth2 custom converter available to convert source parameters with extracting self-signed JWT token and place in
 * {@link OAuth2AccessTokenResponse} object.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class OAuth2CustomTokenConverter implements Converter<Map<String, Object>, OAuth2AccessTokenResponse> {

    private final TokenType defAccessToken = TokenType.BEARER;
    private final String[] tokenParameters = { ACCESS_TOKEN, TOKEN_TYPE, EXPIRES_IN, REFRESH_TOKEN, SCOPE };

    /**
     * Overrided method responsible for generating OAuth2 {@link OAuth2AccessTokenResponse} object with self-signed
     * token extracted from sourceParameters {@link Map} collection.
     *
     * @param sourceParameters the source object to convert, which must be an instance of {@code S} (never {@code null})
     * @return {@link OAuth2AccessTokenResponse} object with passed self-signed Bearer token
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    @Override
    public OAuth2AccessTokenResponse convert(final Map<String, Object> sourceParameters) {
        final String accessToken = (String) sourceParameters.get(ACCESS_TOKEN);
        final int expiredAfter = (Integer) sourceParameters.get(EXPIRES_IN);

        Set<String> scopes = Collections.emptySet();
        if (sourceParameters.containsKey(SCOPE)) {
            final String scope = (String) sourceParameters.get(SCOPE);
            scopes = Arrays.stream(StringUtils.delimitedListToStringArray(scope, " ")).collect(Collectors.toSet());
        }

        final Map<String, Object> additionalParameters = new LinkedHashMap<>();
        sourceParameters.entrySet().stream()
                .filter(e -> !getTokenResponseParameters().contains(e.getKey()))
                .forEach(e -> additionalParameters.put(e.getKey(), e.getValue()));

        return OAuth2AccessTokenResponse.withToken(accessToken)
                .tokenType(defAccessToken)
                .expiresIn(expiredAfter)
                .scopes(scopes)
                .additionalParameters(additionalParameters)
                .build();
    }

    /**
     * @return {@link Set} collection of token parameters (convert from simple string array to {@link Set} collection.
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    private Set<String> getTokenResponseParameters() {
        return Stream.of(tokenParameters).collect(Collectors.toSet());
    }
}
