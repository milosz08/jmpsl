/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: SecurityAutoConfiguration.java
 * Last modified: 19/10/2022, 18:13
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

package pl.miloszgilga.lib.jmpsl.security;

import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Spring auto-configuration class for JMPSL Security module. Load {@link Environment} object, set BCrypt strength
 * and create {@link BCryptPasswordEncoder} Spring Bean.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Configuration
public class SecurityAutoConfiguration {

    private static Environment env;
    private final int passwordStrength;

    public SecurityAutoConfiguration(Environment environment) {
        env = environment;
        passwordStrength = Integer.parseInt(environment.getProperty("jmpsl.security.password-encoder-strength", "10"));
    }

    public static Environment getEnv() {
        return env;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(passwordStrength);
    }
}
