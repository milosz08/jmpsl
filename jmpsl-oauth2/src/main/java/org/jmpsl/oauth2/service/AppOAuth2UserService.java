/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: AppOAuth2UserService.java
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

import org.springframework.util.Assert;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;

import java.util.*;

import org.jmpsl.oauth2.OAuth2Supplier;
import org.jmpsl.oauth2.OAuth2AutoConfigurationLoader;

import static org.jmpsl.oauth2.OAuth2Exception.OAuth2AuthenticationProcessingException;

/**
 * Service for OAuth2 Spring Security verificator. To implemented in Spring Security filterChain method,
 * create bean in Spring Context class and insert in userService method.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class AppOAuth2UserService extends DefaultOAuth2UserService {

    private final Set<OAuth2Supplier> availableSuppliers = OAuth2AutoConfigurationLoader.getAvailableOAuth2Suppliers();

    private final Environment environment;
    private final IOAuth2LoaderService oAuth2LoaderService;

    public AppOAuth2UserService(Environment environment, IOAuth2LoaderService oAuth2LoaderService) {
        this.environment = environment;
        this.oAuth2LoaderService = oAuth2LoaderService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final OAuth2User oAuth2User = super.loadUser(userRequest);
        try {
            final Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
            final String supplierRaw = userRequest.getClientRegistration().getRegistrationId();
            final OAuth2Supplier supplier = OAuth2Supplier.checkIfSupplierExist(supplierRaw, availableSuppliers);
            if (supplier.equals(OAuth2Supplier.LINKEDIN)) {
                populateEmailAddressLinkedIn(userRequest, attributes);
            }

            return oAuth2LoaderService.registrationProcessingFactory(new OAuth2RegistrationDataDto(supplier, attributes));
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new OAuth2AuthenticationProcessingException();
        }
    }

    /**
     * Inner method responsible for populate email address, if OAuth2 supplier is used from LinkedIn provider.
     *
     * @param userRequest {@link OAuth2UserRequest} object from upper method
     * @param attrs user attributes stored in {@link Map} collection
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws OAuth2AuthenticationException if passed credentials not valid
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void populateEmailAddressLinkedIn(final OAuth2UserRequest userRequest, final Map<String, Object> attrs)
            throws OAuth2AuthenticationException {
        final String emailEndpointUri = environment.getProperty("linkedin.email-address-uri");
        Assert.notNull(emailEndpointUri, "LinkedIn email address end point required in application.properties file.");
        final RestTemplate restTemplate = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + userRequest.getAccessToken().getTokenValue());
        final HttpEntity<?> entity = new HttpEntity<>("", headers);
        final ResponseEntity<Map> response = restTemplate.exchange(emailEndpointUri, HttpMethod.GET, entity, Map.class);
        final List<?> list = (List<?>) Objects.requireNonNull(response.getBody()).get("elements");
        final Map map = (Map<?, ?>) ((Map<?, ?>) list.get(0)).get("handle~");
        attrs.putAll(map);
    }
}
