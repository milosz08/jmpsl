/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: OAuth2UserInfoBase.java
 * Last modified: 19/10/2022, 16:27
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

import lombok.Getter;
import java.util.Map;

/**
 * Base abstract class implemented attributes field and abstract methods for OAuth2 user info.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Getter
public abstract class OAuth2UserInfoBase {

    protected final Map<String, Object> attributes;

    OAuth2UserInfoBase(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId();
    public abstract String getUsername();
    public abstract String getEmailAddress();
    public abstract String getUserImageUrl();
}
