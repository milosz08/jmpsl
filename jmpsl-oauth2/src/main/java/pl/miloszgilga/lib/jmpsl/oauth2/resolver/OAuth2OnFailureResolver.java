/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: OAuth2OnFailureResolver.java
 * Last modified: 19/10/2022, 21:31
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

package pl.miloszgilga.lib.jmpsl.oauth2.resolver;

import org.slf4j.*;

import org.springframework.stereotype.Component;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.http.*;
import java.io.IOException;

import java.util.*;
import java.util.stream.Collectors;

import pl.miloszgilga.lib.jmpsl.oauth2.OAuth2Cookie;
import pl.miloszgilga.lib.jmpsl.util.ServletPathUtil;
import pl.miloszgilga.lib.jmpsl.util.cookie.CookieUtil;

import static pl.miloszgilga.lib.jmpsl.oauth2.OAuth2Cookie.*;

/**
 * Custom OAuth2 resolver run on failure OAuth2 authentication. Generate redirect URL (base before created cookies
 * objects) with error message param. To use this resolver, insert in Spring Security chain in <code>filterChain</code>
 * Spring Bean method.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Component
public class OAuth2OnFailureResolver extends SimpleUrlAuthenticationFailureHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2OnFailureResolver.class);

    private static final OAuth2Cookie[] COOKIES_TO_DELETE = {
            SESSION_REMEMBER, AFTER_LOGIN_REDIRECT_URI, AFTER_SIGNUP_REDIRECT_URI
    };

    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException ex)
            throws IOException {

        final String targetUrl = CookieUtil.getCookieValue(req, AFTER_LOGIN_REDIRECT_URI.getCookieName()).orElse("/");
        LOGGER.error("OAuth2 authorization failure: Error: {}", ex.getMessage());
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
