/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: OAuth2CustomTokenConverter.java
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

package org.jmpsl.oauth2;

import org.springframework.util.StringUtils;
import org.springframework.core.convert.converter.Converter;

import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;

/**
 * OAuth2 custom converter available to convert source parameters with extracting self-signed JWT token and place in
 * {@link OAuth2AccessTokenResponse} object.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class OAuth2CustomTokenConverter implements Converter<Map<String, Object>, OAuth2AccessTokenResponse> {

    private final OAuth2AccessToken.TokenType defAccessToken = OAuth2AccessToken.TokenType.BEARER;
    private final String[] tokenParameters = {
        OAuth2ParameterNames.ACCESS_TOKEN, OAuth2ParameterNames.TOKEN_TYPE, OAuth2ParameterNames.EXPIRES_IN,
        OAuth2ParameterNames.REFRESH_TOKEN, OAuth2ParameterNames.SCOPE
    };

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
        final String accessToken = (String) sourceParameters.get(OAuth2ParameterNames.ACCESS_TOKEN);
        final int expiredAfter = (Integer) sourceParameters.get(OAuth2ParameterNames.EXPIRES_IN);

        Set<String> scopes = Collections.emptySet();
        if (sourceParameters.containsKey(OAuth2ParameterNames.SCOPE)) {
            final String scope = (String) sourceParameters.get(OAuth2ParameterNames.SCOPE);
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
