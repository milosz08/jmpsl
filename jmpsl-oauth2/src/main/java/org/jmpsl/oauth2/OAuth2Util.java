/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: OAuth2Util.java
 * Last modified: 18/05/2023, 20:48
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

import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.FormHttpMessageConverter;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;

import java.util.List;
import java.util.Arrays;

import org.jmpsl.security.SecurityUtil;
import org.jmpsl.security.user.IAuthUserModel;
import org.jmpsl.security.user.IEnumerableUserRole;
import org.jmpsl.oauth2.user.OAuth2UserExtender;
import org.jmpsl.oauth2.service.OAuth2RegistrationDataDto;

/**
 * Class with utilities static methods for OAuth2 Spring services.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class OAuth2Util {

    /**
     * Static method responsible for generate, convert and authenticate OAuth2 user by self-signed token (provided in
     * request sourceParameters). For REST interfaces.
     *
     * @return response client object with self-signed token (if credentials is valid), otherwise handled exception
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public static OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> auth2AccessTokenResponseClient() {
        final var converter = new OAuth2AccessTokenResponseHttpMessageConverter();
        converter.setAccessTokenResponseConverter(new OAuth2CustomTokenConverter());
        final var template = new RestTemplate(Arrays.asList(new FormHttpMessageConverter(), converter));
        template.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
        final var responseClient = new DefaultAuthorizationCodeTokenResponseClient();
        responseClient.setRestOperations(template);
        return responseClient;
    }

    /**
     * Static method responsible for fabricate {@link OAuth2UserExtender} object from class implementing
     * {@link IAuthUserModel} interface.
     *
     * @param user {@link OAuth2UserExtender} object from class implementing {@link IAuthUserModel} interface
     * @param dto {@link OAuth2RegistrationDataDto} POJO class with additional OAuth2 registration data
     * @return {@link OAuth2UserExtender} object
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if user or dto POJO objects are null
     */
    public static OAuth2UserExtender fabricateUser(IAuthUserModel user, OAuth2RegistrationDataDto dto) {
        Assert.notNull(user, "User object cannot be null.");
        Assert.notNull(dto, "OAuth2RegistrationDataDto cannot be null.");

        final var authUser = new OAuth2UserExtender(user, flattedAllRoles(user), dto.getSupplier(),
            dto.getOidcUserToken(), dto.getOidcUserInfo());
        authUser.setAttributes(dto.getAttributes());
        return authUser;
    }

    /**
     * Static method responsible for fabricate {@link OAuth2UserExtender} object from class implementing
     * {@link IAuthUserModel} interface without {@link OAuth2RegistrationDataDto} object.
     *
     * @param user {@link OAuth2UserExtender} object from class implementing {@link IAuthUserModel} interface
     * @return {@link OAuth2UserExtender} object
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if user or supplier are null
     */
    public static OAuth2UserExtender fabricateUser(IAuthUserModel user, OAuth2Supplier supplier) {
        Assert.notNull(user, "User object cannot be null.");
        Assert.notNull(supplier, "Supplier object cannot be null.");
        return new OAuth2UserExtender(user, flattedAllRoles(user), supplier);
    }

    /**
     * Inner static method responsible for flattering roles from role enum implements {@link IEnumerableUserRole}
     * interface and returning roles in shape of authorities as {@link SimpleGrantedAuthority} objects.
     *
     * @param user {@link OAuth2UserExtender} object from class implementing {@link IAuthUserModel} interface
     * @return collection of {@link SimpleGrantedAuthority} objects
     * @author Miłosz Gilga
     * @since 1.0.2_02
     */
    private static List<SimpleGrantedAuthority> flattedAllRoles(IAuthUserModel user) {
        return SecurityUtil.convertRolesToAuthorities(user.getAuthRoles());
    }
}
