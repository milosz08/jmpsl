/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: OtaToken.java
 * Last modified: 15/10/2022, 11:50
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

package pl.miloszgilga.lib.jmpsl.auth;

import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;

import java.util.Random;
import java.util.regex.*;

/**
 * Spring Bean component class responsible for generated and checked One-Time-Access token. This type of token mostly
 * is used in verifications. By default token lenght is 10. To change this parameter, set <code>jmpsl.auth.ota.lenght</code>
 * parameter in <code>application.properties</code> file to selected integer value.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Service
public class OtaToken {

    private static final Random RANDOM = new Random();
    private static final Pattern PATTERN = Pattern.compile("[a-zA-Z0-9]+");
    private static final String SIGNS = "abcdefghijklmnoprstquvwxyzABCDEFGHIJKLMNOPRSTQUWXYZ0123456789";

    private final int otaTokenLenght;

    /**
     * Create instance of OtaToken service.
     *
     * @param environment Spring auto-injecting {@link Environment} object
     */
    public OtaToken(Environment environment) {
        this.otaTokenLenght = Integer.parseInt(environment.getProperty("{jmpsl.auth.ota.length}", String.valueOf(10)));
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
    public String generateToken(final int tokenLength) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tokenLength; i++) {
            builder.append(SIGNS.charAt(RANDOM.nextInt(SIGNS.length())));
        }
        return builder.toString();
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
    public boolean isValid(final String token, final int tokenLenght) {
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
    public boolean isValid(final String token) {
        return isValid(token, otaTokenLenght);
    }
}
