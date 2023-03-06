/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: CookieOAuth2ReqRepository.java
 * Last modified: 18/11/2022, 02:53
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

package org.jmpsl.oauth2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;

import java.util.Set;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jmpsl.core.cookie.CookieUtil;
import org.jmpsl.core.cookie.AddedCookiePayload;

import static org.jmpsl.oauth2.OAuth2Exception.OAuth2AuthenticationProcessingException;

/**
 * Custom Spring auto-injected class responsible for managed cookies after load, save and remove OAuth2 user details.
 * Class implements {@link AuthorizationRequestRepository} parametrized interface with {@link OAuth2AuthorizationRequest}
 * class type.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class CookieOAuth2ReqRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private final OAuth2Cookie[] allCookies = {
        OAuth2Cookie.SESSION_REMEMBER, OAuth2Cookie.AFTER_LOGIN_REDIRECT_URI, OAuth2Cookie.AFTER_SIGNUP_REDIRECT_URI
    };

    private final int cookieExiredInSec;

    CookieOAuth2ReqRepository(Environment env) {
        this.cookieExiredInSec = OAuth2Env.__OAT_COOKIE_EXP.getProperty(env, Integer.class) * 60;
    }

    /**
     * Overrided method responsible for load authorization data from {@link HttpServletRequest} object injected b
     * Tomcat Embed Container and returning {@link OAuth2AuthorizationRequest} object if cookie exist, otherwise
     * throw exception.
     *
     * @param req {@link HttpServletRequest} object auto-inserted by Tomcat Embeded Container
     * @return {@link OAuth2AuthorizationRequest} object, if cookie exist, otherwise throw exception
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws OAuth2AuthenticationProcessingException if cookie does not exist or unable make connect with OAuth2 server
     */
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest req) {
        return CookieUtil.getCookie(req, OAuth2Cookie.SESSION_REMEMBER.getCookieName())
            .map(c -> CookieUtil.deserializeCookieValue(c, OAuth2AuthorizationRequest.class))
            .orElseThrow(() -> { throw new OAuth2AuthenticationProcessingException(); });
    }

    /**
     * Override method responsible for save OAuth2 authorization request. Is auto-inserted {@link OAuth2AuthorizationRequest}
     * is null, remove all OAuth2 session cookies. Otherwise create cookies for session remember and redirect URI for
     * login and register frontend path.
     *
     * @param authReq {@link OAuth2AuthorizationRequest} object auto-inserted by Spring OAuth2 context supplier
     * @param req {@link HttpServletRequest} object auto-inserted by Tomcat Embeded Container
     * @param res {@link HttpServletResponse} object auto-inserted by Tomcat Embeded Container
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authReq, HttpServletRequest req, HttpServletResponse res) {
        if (Objects.isNull(authReq)) {
            final Set<String> flattedCookies = Arrays.stream(allCookies)
                .map(OAuth2Cookie::getCookieName)
                .collect(Collectors.toSet());
            CookieUtil.deleteMultipleCookies(req, res, flattedCookies);
            return;
        }
        CookieUtil.addCookie(res, buildCookiePayload(OAuth2Cookie.SESSION_REMEMBER, CookieUtil.serializeCookieValue(authReq)));
        if (StringUtils.isNoneBlank(req.getParameter(OAuth2Cookie.AFTER_LOGIN_REDIRECT_URI.getCookieName()))) {
            CookieUtil.addCookie(res, buildCookiePayload(OAuth2Cookie.AFTER_LOGIN_REDIRECT_URI, req));
        }
        if (StringUtils.isNoneBlank(req.getParameter(OAuth2Cookie.AFTER_SIGNUP_REDIRECT_URI.getCookieName()))) {
            CookieUtil.addCookie(res, buildCookiePayload(OAuth2Cookie.AFTER_SIGNUP_REDIRECT_URI, req));
        }
    }

    /**
     * Override method refer to {@link #loadAuthorizationRequest} method after remove authorization request (throw
     * exception).
     *
     * @param req {@link HttpServletRequest} object passed from overrided method
     * @return refer to {@link #loadAuthorizationRequest} method
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest req, HttpServletResponse res) {
        return loadAuthorizationRequest(req);
    }

    /**
     * Inner method responsible for generate cookie payload POJO.
     *
     * @param cookie {@link OAuth2Cookie} enum type (with name)
     * @param value cookie value
     * @return {@link AddedCookiePayload} object with inserted parameters from method declaration
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    private AddedCookiePayload buildCookiePayload(final OAuth2Cookie cookie, final String value) {
        return AddedCookiePayload.builder()
            .name(cookie.getCookieName())
            .value(value)
            .maxAge(cookieExiredInSec)
            .build();
    }

    /**
     * Inner method responsible for generate cookie payload POJO with value as redirect uri string value.
     *
     * @param cookie {@link OAuth2Cookie} enum type (with name)
     * @param req {@link HttpServletRequest} object auto-inserted by Tomcat Embeded Container
     * @return {@link AddedCookiePayload} object with inserted parameters from method declaration
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    private AddedCookiePayload buildCookiePayload(final OAuth2Cookie cookie, final HttpServletRequest req) {
        return buildCookiePayload(cookie, req.getParameter(OAuth2Cookie.BASE_REDIRECT_URI.getCookieName()) + "/" +
            req.getParameter(cookie.getCookieName()));
    }
}
