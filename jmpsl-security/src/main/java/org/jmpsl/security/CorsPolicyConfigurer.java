/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: CorsPolicyConfigurer.java
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

import org.springframework.util.Assert;
import org.springframework.http.HttpMethod;
import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.Arrays;

/**
 * Configuration class storing method for CORS policy MVC spring boot application configuration.
 */
@Configuration
public class CorsPolicyConfigurer {

    private final String corsClient;
    private final long maxAge;

    public CorsPolicyConfigurer(Environment env) {
        corsClient = SecurityEnv.__SEC_CORS_CLIENT.getProperty(env);
        maxAge = SecurityEnv.__SEC_CORS_MAX_AGE.getProperty(env, Long.class);
    }

    /**
     * Configure CORS policy based passed not nullable registry object and available methods from {@link HttpMethod}
     * enum class.
     *
     * @param registry spring boot {@link CorsRegistry} object not nullable object
     * @param availableMethods available methods as string array from {@link HttpMethod} enum class
     *
     * @throws IllegalArgumentException if {@link CorsRegistry} object is null
     */
    public void configureCorsPolicy(CorsRegistry registry, String[] availableMethods) {
        Assert.notNull(registry, "Registry object cannot be null.");
        registry.addMapping("/**")
            .allowedOrigins(corsClient)
            .allowedMethods(availableMethods)
            .allowCredentials(true)
            .allowedHeaders("*")
            .maxAge(maxAge);
    }

    /**
     * Configure CORS policy based passed not nullable registry object and all available methods from
     * {@link HttpMethod} enum class.
     *
     * @param registry spring boot {@link CorsRegistry} object not nullable object
     */
    public void configureCorsPolicy(CorsRegistry registry) {
        final String[] allowedOrigins = Arrays.stream(HttpMethod.values()).map(HttpMethod::name).toArray(String[]::new);
        configureCorsPolicy(registry, allowedOrigins);
    }
}
