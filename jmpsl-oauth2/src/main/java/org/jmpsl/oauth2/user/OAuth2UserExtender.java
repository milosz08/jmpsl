/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: OAuth2UserExtender.java
 * Last modified: 18/05/2023, 20:45
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
