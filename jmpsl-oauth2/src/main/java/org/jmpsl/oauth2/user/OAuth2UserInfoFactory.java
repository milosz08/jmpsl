/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: OAuth2UserInfoFactory.java
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

package org.jmpsl.oauth2.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.Map;

import org.jmpsl.oauth2.OAuth2Supplier;

import static org.jmpsl.oauth2.OAuth2Exception.OAuth2SupplierNotImplementedException;

/**
 * Factory pattern class responsible for generate {@link OAuth2UserInfoBase} objects based passed supplier and
 * attributes in static method.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Slf4j
public class OAuth2UserInfoFactory {

    /**
     * Static factory method responsible for generate and return {@link OAuth2UserInfoBase} user info object.
     *
     * @param supplier {@link OAuth2Supplier} enum type
     * @param attrs {@link Map} collection of OAuth2 user attributes
     * @return {@link OAuth2UserInfoBase} extended object
     *
     * @throws IllegalArgumentException if supplier object is null
     */
    public static OAuth2UserInfoBase getInstance(final OAuth2Supplier supplier, final Map<String, Object> attrs) {
        Assert.notNull(supplier, "Supplier object cannot be null.");
        return switch (supplier) {
            case FACEBOOK -> new FacebookOAuth2UserInfo(attrs);
            case GITHUB -> new GithubOAuth2UserInfo(attrs);
            case GOOGLE -> new GoogleOAuth2UserInfo(attrs);
            case LINKEDIN -> new LinkedInOAuth2UserInfo(attrs);
            default -> {
                log.error("Attempt to login via unsupported supplier. Unsupported supplier: {}", supplier);
                throw new OAuth2SupplierNotImplementedException();
            }
        };
    }
}
