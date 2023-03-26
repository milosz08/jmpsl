/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: JwtService.java
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

package org.jmpsl.security.jwt;

import lombok.extern.slf4j.Slf4j;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * Spring Bean component class provide basic methods for managed JWT (which be more detailed in methods in custom
 * Spring class component). Before run the application, set <code>jmpsl.security.jwt.issuer</code> value in
 * <code>application.properties</code> file.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Slf4j
@Service
public class JwtService {

    private final JwtConfig jwtConfig;

    public JwtService(JwtConfig jwtConfig) {
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
        if (Objects.isNull(subject)) throw new NullPointerException("Passed subject parameter cannot be null.");
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
        if (isValid(token).isValid()) {
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
        if (Objects.isNull(userIdClaimName)) throw new NullPointerException("Passed user id claim name cannot be null.");
        final JwtValidPayload checkedTokenValid = isValid(expiredToken);
        if (checkedTokenValid.isValid() || !checkedTokenValid.checkType(JwtValidationType.EXPIRED)) return Optional.empty();
        final ValidateJwtPayload unsafeClaims = insideValidateToken(expiredToken);
        if (unsafeClaims.getClaims().isEmpty()) return Optional.empty();
        return Optional.of(unsafeClaims.getClaims().get().get(userIdClaimName, Long.class));
    }

    /**
     * Method responsible for extracting claims from JWT. This method is UNSAFE (may throw exception from jjwts library).
     * To extract claims with full safety, use {@link #extractClaims} method from {@link JwtService} class.
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
        if (Objects.isNull(token)) throw new NullPointerException("Passed token value cannot be null.");
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
    public JwtValidPayload isValid(final String token) {
        final ValidateJwtPayload tokenAfterValidation = insideValidateToken(token);
        if (!tokenAfterValidation.getType().equals(JwtValidationType.GOOD)) {
            log.error(tokenAfterValidation.getType().getMessage() + " Token: {}", token);
            return new JwtValidPayload(false, tokenAfterValidation.getType());
        }
        return new JwtValidPayload(true, tokenAfterValidation.getType());
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
        if (Objects.isNull(token)) throw new NullPointerException("Passed token value cannot be null.");
        try {
            final Claims extractedClaims = unsafeExtractClaims(token);
            return new ValidateJwtPayload(JwtValidationType.GOOD, Optional.of(extractedClaims));
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
