/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: JwtConfig.java
 * Last modified: 15/10/2022, 19:59
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

package pl.miloszgilga.lib.jmpsl.security.jwt;

import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Configuration;

import java.security.Key;

import static pl.miloszgilga.lib.jmpsl.security.SecurityEnv.*;

/**
 * Spring Bean component class responsible for create configuration for JWT used in web application. Default JWT hash
 * algorithm is HS256. To run, provide secret salt <code>jmpsl.security.jwt.secret</code> in
 * <code>application.properties</code> file. If this variable not have been initialized, application after starting
 * throw exception.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Configuration
public class JwtConfig {

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private final String tokenIssuer;
    private final byte[] tokenSecretBytes;

    /**
     * Constant for JWT authorization header key.
     *
     * @since 1.0.2
     */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * Constant for JWT authorization header value (Bearer type).
     *
     * @since 1.0.2
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    public JwtConfig(Environment env) {
        tokenIssuer = __SEC_JWT_ISSUER.getProperty(env);
        tokenSecretBytes = DatatypeConverter.parseBase64Binary(__SEC_JWT_SECRET.getProperty(env));
    }

    /**
     * Method responsible for return Key object, which contains secret key and signature algorithm information.
     *
     * @return Key object with secret key and signature algorithm information
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public Key getSignatureKey() {
        return new SecretKeySpec(tokenSecretBytes, signatureAlgorithm.getJcaName());
    }

    public String getTokenIssuer() {
        return tokenIssuer;
    }
}
