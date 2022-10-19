/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: CustomAuthUser.java
 * Last modified: 19/10/2022, 00:25
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

package pl.miloszgilga.lib.jmpsl.oauth2.user;

import org.springframework.security.oauth2.core.oidc.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import pl.miloszgilga.lib.jmpsl.oauth2.OAuth2Supplier;
import pl.miloszgilga.lib.jmpsl.security.user.*;

import java.util.*;
import java.io.Serializable;

/**
 * POJO class extending {@link AuthUser} class from jmpsl-auth library module and added OAuth2 OIDC user informations
 * and attributes. Class inherit {@link User} from extending {@link AuthUser} class, so it could be also implemented
 * and management by Spring Security Context.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class OAuth2UserExtender extends AuthUser implements OAuth2User, OidcUser, Serializable {
    private static final long serialVersionUID = 1L;

    private final OidcIdToken oidcIdToken;
    private final OAuth2Supplier supplier;
    private final OidcUserInfo oidcUserInfo;
    private Map<String, Object> attributes;

    public OAuth2UserExtender(IAuthUserModel user, List<SimpleGrantedAuthority> authorities, OAuth2Supplier supplier) {
        this(user, authorities, supplier, null, null);
    }

    public OAuth2UserExtender(IAuthUserModel user, List<SimpleGrantedAuthority> authorities, OAuth2Supplier supplier,
                              OidcIdToken token, OidcUserInfo info) {
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
