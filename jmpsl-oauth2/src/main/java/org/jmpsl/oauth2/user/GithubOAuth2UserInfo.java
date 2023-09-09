/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: GithubOAuth2UserInfo.java
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

import java.util.Map;

/**
 * OAuth2 user class extending abstract {@link OAuth2UserInfoBase} and provide logic for Github OAuth2 supplier.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class GithubOAuth2UserInfo extends OAuth2UserInfoBase {

    public GithubOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return ((Integer) attributes.get("id")).toString();
    }

    @Override
    public String getUsername() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmailAddress() {
        return (String) attributes.get("email");
    }

    @Override
    public String getUserImageUrl() {
        return (String) attributes.get("avatar_url");
    }
}
