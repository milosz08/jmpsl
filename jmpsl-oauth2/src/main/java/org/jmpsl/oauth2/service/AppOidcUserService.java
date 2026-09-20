/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: AppOidcUserService.java
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

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;

import java.util.Set;

import org.jmpsl.oauth2.OAuth2Supplier;
import org.jmpsl.oauth2.OAuth2AutoConfigurationLoader;

import static org.jmpsl.oauth2.OAuth2Exception.OAuth2AuthenticationProcessingException;

/**
 * Service for Oidc Spring Security verificator. To implemented in Spring Security filterChain method,
 * create bean in Spring Context class and insert in userService method.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class AppOidcUserService extends OidcUserService {

    private final Set<OAuth2Supplier> availableSuppliers = OAuth2AutoConfigurationLoader.getAvailableOAuth2Suppliers();
    private final IOAuth2LoaderService oAuth2LoaderService;

    public AppOidcUserService(IOAuth2LoaderService oAuth2LoaderService) {
        this.oAuth2LoaderService = oAuth2LoaderService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        final OidcUser oidcUser = super.loadUser(userRequest);
        try {
            final String supplierRaw = userRequest.getClientRegistration().getRegistrationId();
            final OAuth2Supplier supplier = OAuth2Supplier.checkIfSupplierExist(supplierRaw, availableSuppliers);
            final var registrationData = OAuth2RegistrationDataDto.builder()
                .supplier(supplier)
                .oidcUserToken(oidcUser.getIdToken())
                .attributes(oidcUser.getAttributes())
                .oidcUserInfo(oidcUser.getUserInfo())
                .build();
            return oAuth2LoaderService.registrationProcessingFactory(registrationData);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OAuth2AuthenticationProcessingException();
        }
    }
}
