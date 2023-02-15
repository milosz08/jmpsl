/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: SecurityAutoConfiguration.java
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

package org.jmpsl.security;

import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.jmpsl.security.SecurityEnv.__SEC_PSW_ENC_STRENGTH;

/**
 * Spring auto-configuration class for JMPSL Security module. Load {@link Environment} object, set BCrypt strength
 * and create {@link BCryptPasswordEncoder} Spring Bean.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Configuration
public class SecurityAutoConfiguration {

    private final byte passwordStrength;

    public SecurityAutoConfiguration(Environment env) {
        passwordStrength = __SEC_PSW_ENC_STRENGTH.getProperty(env, Byte.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(passwordStrength);
    }
}
