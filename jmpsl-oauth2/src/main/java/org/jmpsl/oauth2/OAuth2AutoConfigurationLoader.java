/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: OAuth2AutoConfigurationLoader.java
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
