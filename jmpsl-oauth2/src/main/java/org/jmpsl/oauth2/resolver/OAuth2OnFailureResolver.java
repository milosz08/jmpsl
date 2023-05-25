/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: OAuth2OnFailureResolver.java
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

package org.jmpsl.oauth2.resolver;

import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

import java.util.Set;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.jmpsl.oauth2.OAuth2Cookie;
import org.jmpsl.core.ServletPathUtil;
import org.jmpsl.core.cookie.CookieUtil;

/**
 * Custom OAuth2 resolver run on failure OAuth2 authentication. Generate redirect URL (base before created cookies
 * objects) with error message param. To use this resolver, insert in Spring Security chain in <code>filterChain</code>
 * Spring Bean method.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Slf4j
@Component
public class OAuth2OnFailureResolver extends SimpleUrlAuthenticationFailureHandler {

    private static final OAuth2Cookie[] COOKIES_TO_DELETE = {
        OAuth2Cookie.SESSION_REMEMBER, OAuth2Cookie.AFTER_LOGIN_REDIRECT_URI, OAuth2Cookie.AFTER_SIGNUP_REDIRECT_URI
    };

    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException ex)
            throws IOException {

        final String targetUrl = CookieUtil
            .getCookieValue(req, OAuth2Cookie.AFTER_LOGIN_REDIRECT_URI.getCookieName())
            .orElse("/");
        log.error("OAuth2 authorization failure: Error: {}", ex.getMessage());
        deleteOAuth2AuthorizationRequestCookies(req, res);

        final String redirectPath = ServletPathUtil.redirectErrorUri(ex.getLocalizedMessage(), targetUrl).toString();
        getRedirectStrategy().sendRedirect(req, res, redirectPath);
    }

    /**
     * Inner method responsible for deleting all OAuth2 cookies.
     *
     * @param req {@link HttpServletRequest} object auto-injected by Tomcat Servlet Container
     * @param res {@link HttpServletResponse} object auto-injected by Tomcat Servlet Container
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    private void deleteOAuth2AuthorizationRequestCookies(final HttpServletRequest req, final HttpServletResponse res) {
        final Set<String> cookiesToDelete = Arrays.stream(COOKIES_TO_DELETE)
            .map(OAuth2Cookie::getCookieName)
            .collect(Collectors.toSet());
        CookieUtil.deleteMultipleCookies(req, res, cookiesToDelete);
    }
}
