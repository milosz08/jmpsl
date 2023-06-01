/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: SimpleGrantedRole.java
 * Last modified: 28/03/2023, 23:14
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

import lombok.Getter;
import lombok.AllArgsConstructor;

import java.util.Set;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Basic enum class for simple granted single role. Use this enum, if your app has not any special roles and all users
 * has the same privileges.
 *
 * @author Miłosz Gilga
 * @since 1.0.2_02
 * @see IEnumerableUserRole
 */
@Getter
@AllArgsConstructor
public enum SimpleGrantedRole implements IEnumerableUserRole {
    USER("USER");

    /**
     * Field represents role name.
     *
     * @since 1.0.2_02
     */
    private final String role;

    /**
     * Generate set collection of all granted roles.
     *
     * @return {@link Set} collection of granted roles
     * @author Miłosz Gilga
     * @since 1.0.2_02
     */
    public static Set<IEnumerableUserRole> getSetCollection() {
        return Arrays.stream(values()).collect(Collectors.toSet());
    }
}
