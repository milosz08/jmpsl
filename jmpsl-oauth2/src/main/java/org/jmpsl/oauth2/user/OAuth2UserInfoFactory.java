/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: OAuth2UserInfoFactory.java
 * Last modified: 19/10/2022, 23:03
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

package org.jmpsl.oauth2.user;

import org.slf4j.*;
import java.util.Map;

import org.springframework.util.Assert;

import org.jmpsl.oauth2.OAuth2Supplier;
import static org.jmpsl.oauth2.OAuth2Exception.OAuth2SupplierNotImplementedException;

/**
 * Factory pattern class responsible for generate {@link OAuth2UserInfoBase} objects based passed supplier and
 * attributes in static method.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class OAuth2UserInfoFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2UserInfoFactory.class);

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
                LOGGER.error("Attempt to login via unsupported supplier. Unsupported supplier: {}", supplier);
                throw new OAuth2SupplierNotImplementedException();
            }
        };
    }
}
