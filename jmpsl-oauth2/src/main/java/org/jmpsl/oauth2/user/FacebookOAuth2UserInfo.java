/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: FacebookOAuth2UserInfo.java
 * Last modified: 19/10/2022, 01:08
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
