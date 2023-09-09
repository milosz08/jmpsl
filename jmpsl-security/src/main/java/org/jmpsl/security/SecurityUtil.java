/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: SecurityUtil.java
 * Last modified: 19/05/2023, 00:47
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
