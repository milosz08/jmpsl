/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: UserPrincipalAuthenticationToken.java
 * Last modified: 18/05/2023, 17:38
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
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited
 * to the warranties of merchantability, fitness for a particular purpose and noninfringement. In no event
 * shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an
 * action of contract, tort or otherwise, arising from, out of or in connection with the software or the use
 * or other dealings in the software.
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
