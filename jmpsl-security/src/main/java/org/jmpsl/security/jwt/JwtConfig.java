/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: JwtConfig.java
 * Last modified: 18/05/2023, 18:53
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

package org.jmpsl.security.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;

import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Configuration;

import java.security.Key;
import javax.crypto.spec.SecretKeySpec;

import org.jmpsl.security.SecurityEnv;

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
    private final int tokenExpiredMinutes;
    private final int refreshTokenExpiredDays;

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
        tokenIssuer = SecurityEnv.__SEC_JWT_ISSUER.getProperty(env);
        tokenExpiredMinutes = SecurityEnv.__SEC_JWT_EXPIRED_MINUTES.getProperty(env, Integer.class);
        refreshTokenExpiredDays = SecurityEnv.__SEC_REFRESH_TOKEN_EXPIRED_DAYS.getProperty(env, Integer.class);
        tokenSecretBytes = DatatypeConverter.parseBase64Binary(SecurityEnv.__SEC_JWT_SECRET.getProperty(env));
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

    public int getTokenExpiredMinutes() {
        return tokenExpiredMinutes;
    }

    public int getRefreshTokenExpiredDays() {
        return refreshTokenExpiredDays;
    }
}
