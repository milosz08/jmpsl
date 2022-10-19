/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: OAuth2OnSuccessfulResolver.java
 * Last modified: 19/10/2022, 21:32
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
import javax.servlet.http.*;

import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.util.*;
import java.net.URI;
import java.io.IOException;
import java.util.stream.Collectors;

import pl.miloszgilga.lib.jmpsl.oauth2.OAuth2Cookie;
import pl.miloszgilga.lib.jmpsl.util.ServletPathUtil;
import pl.miloszgilga.lib.jmpsl.util.cookie.CookieUtil;
import pl.miloszgilga.lib.jmpsl.oauth2.user.OAuth2UserExtender;
import pl.miloszgilga.lib.jmpsl.security.user.IAuthUserModel;

import static pl.miloszgilga.lib.jmpsl.oauth2.OAuth2Cookie.*;
import static pl.miloszgilga.lib.jmpsl.oauth2.OAuth2Exception.OAuth2UriNotSupportedException;

import static java.util.Objects.isNull;

/**
 * Custom OAuth2 resolver run on successfull OAuth2 authentication. Generate redirect URL (base before created cookies
 * objects) with token and supplier param. To use this resolver, insert in Spring Security chain in <code>filterChain</code>
 * Spring Bean method. Before use this resolver, implement {@link IOAuth2TokenGenerator} interface and override method
 * to generate token. NOTE: This resolver is not Spring bean. Create bean in Spring Context via {@link Bean} annotation.
 * Before run application, declare <code>jmpsl.oauth2.redirect-uris</code> property in
 * <code>application.properties</code> file.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class OAuth2OnSuccessfulResolver extends SimpleUrlAuthenticationSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2OnFailureResolver.class);

    private final String[] redirectUris;
    private final IOAuth2TokenGenerator tokenGenerator;

    private static final OAuth2Cookie[] COOKIES_TO_DELETE = {
            SESSION_REMEMBER, AFTER_LOGIN_REDIRECT_URI, AFTER_SIGNUP_REDIRECT_URI
    };

    public OAuth2OnSuccessfulResolver(Environment environment, IOAuth2TokenGenerator tokenGenerator) {
        final String redirectUris = environment.getProperty("jmpsl.oauth2.redirect-uris");
        if (isNull(redirectUris)) throw new NullPointerException("Property jmpsl.oauth2.redirect-uris cannot be null");
        this.redirectUris = redirectUris.split(",");
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth)
            throws IOException {

        final String targetUrl = determineTargetUrl(req, res, auth);
        if (res.isCommitted()) {
            LOGGER.info("Response has already been committed. Unable to redirect to address: {}", targetUrl);
            return;
        }
        clearAuthenticationAttributes(req);
        final Set<String> cookiesToDelete = Arrays.stream(COOKIES_TO_DELETE)
                .map(OAuth2Cookie::getCookieName)
                .collect(Collectors.toSet());
        CookieUtil.deleteMultipleCookies(req, res, cookiesToDelete);
        getRedirectStrategy().sendRedirect(req, res, targetUrl);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest req, HttpServletResponse res, Authentication auth) {
        final OAuth2UserExtender localAuthUser = (OAuth2UserExtender) auth.getPrincipal();
        final IAuthUserModel userModel = localAuthUser.getUserModel();
        final String token = tokenGenerator.generateToken(auth);
        final String credentialsSupplier = localAuthUser.getSupplier().getSupplierName();

        if (userModel.isAccountEnabled()) {
            return ServletPathUtil.redirectTokenUri(token,checkIfRedirectUriIsValidAndReturn(req,
                    AFTER_LOGIN_REDIRECT_URI.getCookieName()), credentialsSupplier).toString();
        }
        return ServletPathUtil.redirectTokenUri(token, checkIfRedirectUriIsValidAndReturn(req,
                AFTER_SIGNUP_REDIRECT_URI.getCookieName()), credentialsSupplier).toString();
    }

    /**
     * Inner method checking, if user is authorized via passed request URI in method parameters.
     *
     * @param uri string representation of request {@link URI}
     * @return true, if user is authorized, otherwise return false
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    private boolean checkIfUserIsAuthorizedViaRequestUri(final String uri) {
        final URI redirectClientUri = URI.create(uri);
        return Arrays.stream(redirectUris).noneMatch(reqUri -> {
            final URI authorizedUri = URI.create(reqUri);
            return authorizedUri.getHost().equalsIgnoreCase(redirectClientUri.getHost()) &&
                    authorizedUri.getPort() == redirectClientUri.getPort();
        });
    }

    /**
     * Inner method responsible for checking if redirect URI is valid (declared in <code>application.properties</code>)
     * file. If is declare, return redirect URI, otherwise return default target URL.
     *
     * @param req {@link HttpServletRequest} object auto-injected by Tomcat Servlet Container
     * @param cookieName name of cookie with redirect URI value
     * @return redirect URI if valid, otherwise return default target URL
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    private String checkIfRedirectUriIsValidAndReturn(final HttpServletRequest req, final String cookieName) {
        final Optional<String> redirectUri = CookieUtil.getCookieValue(req, cookieName);
        if (redirectUri.isEmpty() || checkIfUserIsAuthorizedViaRequestUri(redirectUri.get())) {
            LOGGER.error("Attempt to authenticate via OAuth2 by not supported URI.");
            throw new OAuth2UriNotSupportedException();
        }
        return redirectUri.orElse(getDefaultTargetUrl());
    }
}
