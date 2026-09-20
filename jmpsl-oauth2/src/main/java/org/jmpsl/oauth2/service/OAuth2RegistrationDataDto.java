/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: OAuth2RegistrationDataDto.java
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

package org.jmpsl.oauth2.service;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;

import java.util.Map;

import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import org.jmpsl.oauth2.OAuth2Supplier;

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
