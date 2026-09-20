/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: AuthUser.java
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

package org.jmpsl.security.user;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.io.Serial;
import java.io.Serializable;

/**
 * Spring Security User extended class with implementation of {@link IAuthUserModel} field. Use this class for
 * authorization and authentication, if you not using OAuth2 mechanisms.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class AuthUser extends User implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    private final IAuthUserModel userModel;

    public AuthUser(IAuthUserModel user, List<SimpleGrantedAuthority> authorities) {
        super(user.getAuthUsername(), user.getAuthPassword(), user.isAccountEnabled(), user.isAccountNotExpired(),
            user.isCredentialsNotExpired(), user.isAccountNonLocked(), authorities);
        this.userModel = user;
    }

    public IAuthUserModel getUserModel() {
        return userModel;
    }
}
