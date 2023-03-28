/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: SimpleGrantedRole.java
 * Last modified: 28/03/2023, 13:53
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
 * The software is provided “as is”, without warranty of any kind, express or implied, including but not limited
 * to the warranties of merchantability, fitness for a particular purpose and noninfringement. In no event
 * shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an
 * action of contract, tort or otherwise, arising from, out of or in connection with the software or the use
 * or other dealings in the software.
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
    public static Set<SimpleGrantedRole> getSetCollection() {
        return Arrays.stream(values()).collect(Collectors.toSet());
    }
}
