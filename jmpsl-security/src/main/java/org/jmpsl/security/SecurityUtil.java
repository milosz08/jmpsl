/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: SecurityUtil.java
 * Last modified: 06/03/2023, 17:16
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

package org.jmpsl.security;

import org.springframework.util.Assert;
import org.springframework.core.env.Environment;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.util.Set;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.jmpsl.security.user.AuthUser;
import org.jmpsl.security.user.IAuthUserModel;
import org.jmpsl.security.user.IEnumerableUserRole;

/**
 * Utilities static methods for JMPSL Security module.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class SecurityUtil {

    private SecurityUtil() {
    }

    /**
     * Static method responsible for converting {@link Set} of {@link IEnumerableUserRole} interface into {@link List}
     * collection of {@link SimpleGrantedAuthority} object.
     *
     * @param roles {@link Set} of {@link IEnumerableUserRole} interfaces
     * @return {@link List} collection of {@link SimpleGrantedAuthority} objects
     * @author Miłosz Gilga
     * @since 1.0.2_04
     */
    public static List<SimpleGrantedAuthority> convertRolesToAuthorities(Set<IEnumerableUserRole> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList());
    }

    /**
     * Static method responsible for fabricate {@link AuthUser} object from implementation of {@link IAuthUserModel}
     * interface (for example in domain model class).
     *
     * @param user object implement {@link IAuthUserModel} interface
     * @return {@link AuthUser} object
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if user object is null
     */
    public static AuthUser fabricateUser(IAuthUserModel user) {
        Assert.notNull(user, "User object cannot be null.");
        return new AuthUser(user, convertRolesToAuthorities(user.getAuthRoles()));
    }

    /**
     * Static method responsible for enable H2 console for development purposes only.
     *
     * @param httpSecurity {@link HttpSecurity} object inserted from Spring Security filterChain bean
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws Exception if spring securitu context not exist
     * @throws IllegalArgumentException if {@link HttpSecurity} object is null
     */
    public static void enableH2ConsoleForDev(HttpSecurity httpSecurity, Environment env) throws Exception {
        Assert.notNull(httpSecurity, "http security object cannot be null.");
        final boolean inNotDev = Arrays.stream(env.getActiveProfiles())
            .noneMatch(p -> p.equals(ApplicationMode.DEV.getModeName()));
        if (inNotDev) return;
        httpSecurity
            .authorizeHttpRequests(auth -> auth.requestMatchers("/h2-console/**").permitAll())
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
            .headers(headers -> headers.frameOptions().sameOrigin());
    }
}
