/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: JwtServlet.java
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

import org.slf4j.*;
import io.jsonwebtoken.*;
import org.javatuples.Pair;

import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;
import static java.util.Objects.isNull;

import static pl.miloszgilga.lib.jmpsl.security.jwt.JwtValidationType.GOOD;

/**
 * Spring Bean component class provide basic methods for managed JWT (which be more detailed in methods in custom
 * Spring class component). Before run the application, set <code>jmpsl.security.jwt.issuer</code> value in
 * <code>application.properties</code> file.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Service
public class JwtServlet {

    private final JwtConfig jwtConfig;
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtServlet.class);

    public JwtServlet(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    /**
     * Method responsible for generate JWT token based passed passed subject and claims parameters. Before invoke this
     * method, check if file <code>application.properties</code> include <code>jmpsl.auth.jwt.issuer</code> value and
     * value is not null or empty string.
     *
     * @param subject JWT subject parameter
     * @param claims JWT claims array
     * @return compacted (stringified) JWT based passed parameters
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws NullPointerException if passed JWT subject is null
     */
    public String generateToken(String subject, Claims claims) {
        if (isNull(subject)) throw new NullPointerException("Passed subject parameter cannot be null.");
        return Jwts.builder()
                .setIssuer(jwtConfig.getTokenIssuer())
                .setSubject(subject)
                .setClaims(claims)
                .signWith(jwtConfig.getSignatureKey())
                .compact();
    }

    /**
     * Method responsible for extracting token from <code>HttpServletRequest</code> parameter auto-inserted by Tomcat
     * Servlet Container. Returning found token if exist, otherwise return empty string.
     *
     * @param req http servlet request object (auto-inserted by Tomcat Servlet Container)
     * @return extracted token (if found), otherwise return empty string
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public String extractToken(HttpServletRequest req) {
        final String bearerToken = req.getHeader(JwtConfig.TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtConfig.TOKEN_PREFIX)) {
            return bearerToken.substring(JwtConfig.TOKEN_PREFIX.length());
        }
        return org.apache.commons.lang3.StringUtils.EMPTY;
    }

    /**
     * Method responsible for extracting claims from raw JWT passed in method parameter. Method also validate token and
     * return claims if token is valid, otherwise return null value.
     *
     * @param token generated raw JWT (without "Bearer" prefix)
     * @return extracted claims object from passed JWT if optional contains data, otherwise return empty optional
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws NullPointerException if passed JWT is null
     */
    public Optional<Claims> extractClaims(String token) {
        final ValidateJwtPayload tokenAfterValidation = insideValidateToken(token);
        if (isValid(token).getValue0()) {
            return tokenAfterValidation.getClaims();
        }
        return Optional.empty();
    }

    /**
     * Method responsible for validate passed expired JWT. Firstly check if token is valid. Then, if token is
     * expired, extract claims via unsafe method and return userId claim based userIdClaimName method parameter. Otherwise
     * method return empty {@link Optional} object.
     *
     * @param expiredToken raw bearer expired token
     * @param userIdClaimName user id claim name
     * @return {@link Optional} object with user id (when token is valid), otherwise return empty {@link Optional}
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws NullPointerException if passed expired JWT or userIdClaimName is null
     */
    public Optional<Long> validateRefreshToken(String expiredToken, String userIdClaimName) {
        if (isNull(userIdClaimName)) throw new NullPointerException("Passed user id claim name cannot be null.");
        final Pair<Boolean, JwtValidationType> checkedTokenValid = isValid(expiredToken);
        if (!checkedTokenValid.getValue0() && checkedTokenValid.getValue1().equals(JwtValidationType.EXPIRED)) {
            final ValidateJwtPayload unsafeClaims = insideValidateToken(expiredToken);
            return Optional.of(unsafeClaims.getClaims().get().get(userIdClaimName, Long.class));
        }
        return Optional.empty();
    }

    /**
     * Method responsible for extracting claims from JWT. This method is UNSAFE (may throw exception from jjwts library).
     * To extract claims with full safety, use {@link #extractClaims} method from {@link JwtServlet} class.
     *
     * @param token generated raw JWT (without "Bearer" prefix)
     * @return extracted claims object from passed JWT in method parameter
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws NullPointerException if passed JWT is null
     * @throws MalformedJwtException if passed JWT is malformed
     * @throws ExpiredJwtException if passed JWT is expired
     * @throws JwtException if passed JWT is invalid
     * @throws IllegalArgumentException if passed JWT claims are invalid or malformed
     */
    public Claims unsafeExtractClaims(final String token) {
        if (isNull(token)) throw new NullPointerException("Passed token value cannot be null.");
        return Jwts.parserBuilder()
                .setSigningKey(jwtConfig.getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Method responsible for validate JWT and return tuple with validation status and validation type. If token is
     * valid, return true and GOOD validation status. If token is invalid, return false and one of validation status
     * from {@link JwtValidationType} enum.
     *
     * @param token generated raw JWT (without "Bearer" prefix)
     * @return tuple with first boolean value and second one of validation status {@link JwtValidationType}
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws NullPointerException if passed JWT is null
     */
    public Pair<Boolean, JwtValidationType> isValid(final String token) {
        final ValidateJwtPayload tokenAfterValidation = insideValidateToken(token);
        if (!tokenAfterValidation.getType().equals(GOOD)) {
            LOGGER.error(tokenAfterValidation.getType().getMessage() + " Token: {}", token);
            return new Pair<>(false, tokenAfterValidation.getType());
        }
        return new Pair<>(true, tokenAfterValidation.getType());
    }

    /**
     * Inside private method responsible for validate token and return POJO {@link ValidateJwtPayload} object with
     * validation status and optionally extracted claims.
     *
     * @param token generated raw JWT (without "Bearer" prefix)
     * @return POJO {@link ValidateJwtPayload} with validation statis and optionally extracted claims
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws NullPointerException if passed JWT is null
     */
    private ValidateJwtPayload insideValidateToken(final String token) {
        if (isNull(token)) throw new NullPointerException("Passed token value cannot be null.");
        try {
            final Claims extractedClaims = unsafeExtractClaims(token);
            return new ValidateJwtPayload(GOOD, Optional.of(extractedClaims));
        } catch (MalformedJwtException ex) {
            return new ValidateJwtPayload(JwtValidationType.MALFORMED);
        } catch (ExpiredJwtException ex) {
            return new ValidateJwtPayload(JwtValidationType.EXPIRED, Optional.of(ex.getClaims()));
        } catch (JwtException ex) {
            return new ValidateJwtPayload(JwtValidationType.INVALID);
        } catch (IllegalArgumentException ex) {
            return new ValidateJwtPayload(JwtValidationType.OTHER);
        }
    }
}
