/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: OtaToken.java
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
 * The software is provided “as is”, without warranty of any kind, express or implied, including but not limited
 * to the warranties of merchantability, fitness for a particular purpose and noninfringement. In no event
 * shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an
 * action of contract, tort or otherwise, arising from, out of or in connection with the software or the use
 * or other dealings in the software.
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
public class OtaToken {

    private static final Pattern PATTERN = Pattern.compile("[a-zA-Z0-9]+");
    private final byte otaTokenLenght;

    public OtaToken(Environment env) {
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
