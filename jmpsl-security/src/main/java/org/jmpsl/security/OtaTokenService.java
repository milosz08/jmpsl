/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: OtaTokenService.java
 * Last modified: 18/05/2023, 19:08
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

package org.jmpsl.security;

import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Spring Bean component class responsible for generated and checked One-Time-Access token. This type of token mostly
 * is used in verifications. By default token lenght is 10. To change this parameter, set
 * <code>jmpsl.security.ota.lenght</code> parameter in <code>application.properties</code> file to selected integer value.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Service
public class OtaTokenService {

    private static final Pattern PATTERN = Pattern.compile("[a-zA-Z0-9]+");
    private final byte otaTokenLenght;

    public OtaTokenService(Environment env) {
        this.otaTokenLenght = SecurityEnv.__SEC_OTA_LENGTH.getProperty(env, Byte.class);
    }

    /**
     * Method responsible for generating One-Time-Access token based on the length passed in one of the method's
     * parameters.
     *
     * @param tokenLength length of the generated token
     * @return generated token
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public String generateToken(int tokenLength) {
        return RandomStringUtils.randomAlphanumeric(tokenLength);
    }

    /**
     * Method responsible for generating One-Time-Access token based on the length from <code>application.properties</code>
     * file.
     *
     * @return generated token
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public String generateToken() {
        return generateToken(otaTokenLenght);
    }

    /**
     * Method responsible for checking One-Time-Access token, if is valid (contains only legal characters and
     * has the correct length which is passed in one of the parameters of the method).
     *
     * @param token ota token to check
     * @param tokenLenght ota token correct length
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @return true, if token is valid, false if token is not valid
     */
    public boolean isValid(String token, int tokenLenght) {
        if (token.isBlank()) return false;
        final Matcher matcher = PATTERN.matcher(token);
        return matcher.matches() && token.length() == tokenLenght;
    }

    /**
     * Method responsible for checking One-Time-Access token, if is valid (contains only legal characters and
     * has the correct length which should be defined in the application.properties file).
     *
     * @param token ota token to check
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @return true, if token is valid, false if token is not valid
     */
    public boolean isValid(String token) {
        return isValid(token, otaTokenLenght);
    }
}
