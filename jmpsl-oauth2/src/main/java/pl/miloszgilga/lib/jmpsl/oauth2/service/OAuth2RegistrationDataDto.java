/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: OAuth2RegistrationDataDto.java
 * Last modified: 18/10/2022, 21:56
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

package pl.miloszgilga.lib.jmpsl.oauth2.service;

import lombok.*;
import java.util.Map;

import org.springframework.security.oauth2.core.oidc.*;

import pl.miloszgilga.lib.jmpsl.oauth2.OAuth2Supplier;

/**
 * Simple POJO class storing registration via OAuth2 data and external informations.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Data
@Builder
@AllArgsConstructor
public class OAuth2RegistrationDataDto {
    private OAuth2Supplier supplier;
    private Map<String, Object> attributes;
    private OidcIdToken oidcUserToken;
    private OidcUserInfo oidcUserInfo;

    public OAuth2RegistrationDataDto(OAuth2Supplier supplier, Map<String, Object> attributes) {
        this.supplier = supplier;
        this.attributes = attributes;
    }
}
