/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: OAuth2Util.java
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

import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.FormHttpMessageConverter;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;

import java.util.Set;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

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
     * @param <T> enum implements {@link IEnumerableUserRole} interface, representing available roles in application
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if user or dto POJO objects are null
     */
    public static <T extends IEnumerableUserRole> OAuth2UserExtender<T> fabricateUser(
        IAuthUserModel<T> user, OAuth2RegistrationDataDto dto
    ) {
        Assert.notNull(user, "User object cannot be null.");
        Assert.notNull(dto, "OAuth2RegistrationDataDto cannot be null.");

        final var authUser = new OAuth2UserExtender<>(user, flattedAllRoles(user), dto.getSupplier(),
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
     * @param <T> enum implements {@link IEnumerableUserRole} interface, representing available roles in application
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if user or supplier are null
     */
    public static <T extends IEnumerableUserRole> OAuth2UserExtender<T> fabricateUser(
        IAuthUserModel<T> user, OAuth2Supplier supplier
    ) {
        Assert.notNull(user, "User object cannot be null.");
        Assert.notNull(supplier, "Supplier object cannot be null.");
        return new OAuth2UserExtender<>(user, flattedAllRoles(user), supplier);
    }

    /**
     * Inner static method responsible for flattering roles from role enum implements {@link IEnumerableUserRole}
     * interface and returning roles in shape of authorities as {@link SimpleGrantedAuthority} objects.
     *
     * @param user {@link OAuth2UserExtender} object from class implementing {@link IAuthUserModel} interface
     * @return collection of {@link SimpleGrantedAuthority} objects
     * @param <T> enum implements {@link IEnumerableUserRole} interface, representing available roles in application
     * @author Miłosz Gilga
     * @since 1.0.2_02
     */
    private static <T extends IEnumerableUserRole> List<SimpleGrantedAuthority> flattedAllRoles(IAuthUserModel<T> user) {
        final Set<String> availableRoles =  user.getAuthRoles().stream()
            .map(IEnumerableUserRole::getRole).collect(Collectors.toSet());
        return SecurityUtil.convertRolesToAuthorities(availableRoles);
    }
}
