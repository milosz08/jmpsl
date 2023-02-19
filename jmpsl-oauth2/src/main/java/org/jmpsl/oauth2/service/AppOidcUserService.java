/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: AppOidcUserService.java
 * Last modified: 19/10/2022, 23:14
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

package org.jmpsl.oauth2.service;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.oidc.userinfo.*;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import org.jmpsl.oauth2.OAuth2Supplier;

import java.util.Set;

import static org.jmpsl.oauth2.OAuth2Supplier.checkIfSupplierExist;
import static org.jmpsl.oauth2.OAuth2Exception.OAuth2AuthenticationProcessingException;
import static org.jmpsl.oauth2.OAuth2AutoConfigurationLoader.getAvailableOAuth2Suppliers;

/**
 * Service for Oidc Spring Security verificator. To implemented in Spring Security filterChain method,
 * create bean in Spring Context class and insert in userService method.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class AppOidcUserService extends OidcUserService {

    private final Set<OAuth2Supplier> availableSuppliers = getAvailableOAuth2Suppliers();
    private final IOAuth2LoaderService oAuth2LoaderService;

    public AppOidcUserService(IOAuth2LoaderService oAuth2LoaderService) {
        this.oAuth2LoaderService = oAuth2LoaderService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        final OidcUser oidcUser = super.loadUser(userRequest);
        try {
            final String supplierRaw = userRequest.getClientRegistration().getRegistrationId();
            final OAuth2Supplier supplier = checkIfSupplierExist(supplierRaw, availableSuppliers);
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
