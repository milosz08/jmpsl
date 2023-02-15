/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: AuthUser.java
 * Last modified: 14/02/2023, 20:59
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

package org.jmpsl.security.user;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.*;
import java.util.List;

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
