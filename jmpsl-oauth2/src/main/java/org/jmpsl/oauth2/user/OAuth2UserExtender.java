/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: OAuth2UserExtender.java
 * Last modified: 15/02/2023, 02:06
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

package org.jmpsl.oauth2.user;

import org.springframework.security.oauth2.core.oidc.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.*;
import java.util.*;

import org.jmpsl.security.user.*;
import org.jmpsl.oauth2.OAuth2Supplier;

/**
 * POJO class extending {@link AuthUser} class from jmpsl-auth library module and added OAuth2 OIDC user informations
 * and attributes. Class inherit {@link User} from extending {@link AuthUser} class, so it could be also implemented
 * and management by Spring Security Context.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class OAuth2UserExtender extends AuthUser implements OAuth2User, OidcUser, Serializable {
    @Serial private static final long serialVersionUID = 1L;

    private final OidcIdToken oidcIdToken;
    private final OAuth2Supplier supplier;
    private final OidcUserInfo oidcUserInfo;
    private Map<String, Object> attributes;

    public OAuth2UserExtender(IAuthUserModel user, List<SimpleGrantedAuthority> authorities, OAuth2Supplier supplier) {
        this(user, authorities, supplier, null, null);
    }

    public OAuth2UserExtender(
        IAuthUserModel user, List<SimpleGrantedAuthority> authorities, OAuth2Supplier supplier, OidcIdToken token,
        OidcUserInfo info
    ) {
        super(user, authorities);
        this.oidcIdToken = token;
        this.oidcUserInfo = info;
        this.supplier = supplier;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public OAuth2Supplier getSupplier() {
        return supplier;
    }

    @Override
    public Map<String, Object> getClaims() {
        return attributes;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return oidcUserInfo;
    }

    @Override
    public OidcIdToken getIdToken() {
        return oidcIdToken;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return getUserModel().getAuthUsername();
    }
}
