/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: OAuth2Util.java
 * Last modified: 18/10/2022, 20:42
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

package pl.miloszgilga.lib.jmpsl.oauth2;

import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.oauth2.client.endpoint.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;

import pl.miloszgilga.lib.jmpsl.security.user.IAuthUserModel;
import pl.miloszgilga.lib.jmpsl.oauth2.user.OAuth2UserExtender;
import pl.miloszgilga.lib.jmpsl.oauth2.service.OAuth2RegistrationDataDto;

import java.util.*;

import static pl.miloszgilga.lib.jmpsl.security.SecurityUtil.convertRolesToAuthorities;

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
        final List<SimpleGrantedAuthority> authorities = convertRolesToAuthorities(user.getAuthRoles());
        final var authUser = new OAuth2UserExtender(user, authorities, dto.getSupplier(), dto.getOidcUserToken(),
                dto.getOidcUserInfo());
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
        return new OAuth2UserExtender(user, convertRolesToAuthorities(user.getAuthRoles()), supplier);
    }
}
