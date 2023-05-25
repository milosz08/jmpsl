/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: SecurityAutoConfiguration.java
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

package org.jmpsl.security;

import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    private final byte passwordStrength;

    public SecurityAutoConfiguration(Environment env) {
        passwordStrength = SecurityEnv.__SEC_PSW_ENC_STRENGTH.getProperty(env, Byte.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(passwordStrength);
    }
}
