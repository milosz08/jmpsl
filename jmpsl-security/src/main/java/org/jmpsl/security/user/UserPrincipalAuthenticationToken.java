/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: UserPrincipalAuthenticationToken.java
 * Last modified: 19/05/2023, 00:51
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

package org.jmpsl.security.user;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.jmpsl.security.jwt.AbstractJwtRequestFilter;

/**
 * Custom spring security user principal authorities class extending {@link AbstractAuthenticationToken}. Use this class
 * together with {@link UserDetails} interface and {@link AbstractJwtRequestFilter}
 *
 * @author Miłosz Gilga
 * @since 1.0.2_04
 * @see UserDetails
 * @see AbstractJwtRequestFilter
 */
public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {

    private final UserDetails principal;

    public UserPrincipalAuthenticationToken(HttpServletRequest req, UserDetails principal) {
        super(principal.getAuthorities());
        this.principal = principal;
        setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
