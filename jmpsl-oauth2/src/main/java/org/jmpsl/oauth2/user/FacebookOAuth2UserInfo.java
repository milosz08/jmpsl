/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: FacebookOAuth2UserInfo.java
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
 * OAuth2 user class extending abstract {@link OAuth2UserInfoBase} and provide logic for Facebook OAuth2 supplier.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class FacebookOAuth2UserInfo extends OAuth2UserInfoBase {

    FacebookOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("id");
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
    @SuppressWarnings("unchecked")
    public String getUserImageUrl() {
        if (!attributes.containsKey("picture")) return null;
        Object pictureAttributeBeforeSanitization = attributes.get("picture");
        if (!(pictureAttributeBeforeSanitization instanceof Map)) return null;

        final Map<String, Object> pictureObj = (Map<String, Object>)pictureAttributeBeforeSanitization;
        if (!pictureObj.containsKey("data")) return null;

        Object dataObjBeforeSanitization = pictureObj.get("data");
        if (!(dataObjBeforeSanitization instanceof Map)) return null;
        final Map<String, Object> dataObj = (Map<String, Object>)dataObjBeforeSanitization;
        if (!dataObj.containsKey("url")) return null;

        return (String) dataObj.get("url");
    }
}
