/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: OAuth2AutoConfigurationLoader.java
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

package org.jmpsl.oauth2;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * OAuth2 auto-configuration loader into spring context. Provide getter for returning available OAuth2 suppliers in selected
 * application. To select available OAuth2 suppliers for Spring application, put <code>jmpsl.oauth2.available-suppliers</code>
 * property in <code>application.properties</code> file. Multiple values insert with comma delimiter.
 *
 * <p>Example: jmpsl.oauth2.available-suppliers=google,facebook,github</p>
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Slf4j
@Configuration
public class OAuth2AutoConfigurationLoader {

    private static Set<OAuth2Supplier> availableOAuth2Suppliers = new HashSet<>();
    private final Environment env;

    OAuth2AutoConfigurationLoader(Environment env) {
        this.env = env;
        final String suppliers = OAuth2Env.__OAT_SUPPLIERS.getProperty(env);
        availableOAuth2Suppliers = Arrays.stream(suppliers.split(","))
                .map(OAuth2Supplier::checkIfSupplierIsValid)
                .collect(Collectors.toSet());
        log.info("Successful loaded OAuth2 available providers. Loaded providers: [ {} ]", suppliers);
    }

    public static Set<OAuth2Supplier> getAvailableOAuth2Suppliers() {
        return availableOAuth2Suppliers;
    }

    @Bean
    public CookieOAuth2ReqRepository cookieOAuth2ReqRepository() {
        return new CookieOAuth2ReqRepository(env);
    }

    @Bean
    public OAuth2SupplierPersistenceEnumConverter oAuth2SupplierPersistenceEnumConverter() {
        return new OAuth2SupplierPersistenceEnumConverter();
    }
}
