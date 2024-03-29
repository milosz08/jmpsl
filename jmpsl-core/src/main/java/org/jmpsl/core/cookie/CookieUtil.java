/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: CookieUtil.java
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
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited
 * to the warranties of merchantability, fitness for a particular purpose and noninfringement. In no event
 * shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an
 * action of contract, tort or otherwise, arising from, out of or in connection with the software or the use
 * or other dealings in the software.
 */

package org.jmpsl.core.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.util.SerializationUtils;

import java.util.Set;
import java.util.Base64;
import java.util.Optional;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;


/**
 * Class with static method implementing cookie basic servlet management. Method available add cookie, get cookie and
 * delete selected cookie/cookies. Also allows to serialized/deserialized more complicated cookie values.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class CookieUtil {

    private CookieUtil() {
    }

    /**
     * Method responsible for getting cookie based {@link HttpServletRequest} object and cookie name parameter.
     * Cookie name parameter cannot be null. If method not be able to find cookie based cookie name value, return empty
     * {@link Optional}. Otherwise, return {@link Optional} with {@link Cookie}.
     *
     * @param req {@link HttpServletRequest} object injected by Tomcat Servlet Container
     * @param cookieName seraching cookie name
     * @return {@link Optional} with {@link Cookie} if find, otherwise empty {@link Optional} object
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws NullPointerException if cookie name is null
     * @throws IllegalArgumentException if cookie name is blank (empty or contain only blank characters)
     */
    public static Optional<Cookie> getCookie(HttpServletRequest req, String cookieName) {
        validateCookieName(cookieName);
        final Cookie[] cookies = req.getCookies();
        if (cookies == null) return Optional.empty();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(cookieName)) return Optional.of(cookie);
        }
        return Optional.empty();
    }

    /**
     * Method responsible for adding cookie based {@link HttpServletResponse} object and {@link AddedCookiePayload} POJO
     * class (contain cookie name, cookie value and cookie maxAge). By default, path isset to base URL path "/".
     *
     * @param res {@link HttpServletResponse} object injected by Tomcat Servlet Container
     * @param payload {@link AddedCookiePayload} POJO class for storing cookie details data
     * @author Miłosz Gilga
     * @since 1.0.2                                         
     * 
     * @throws NullPointerException if cookie name is null
     * @throws IllegalArgumentException if cookie name is blank (empty or contain only blank characters)
     */
    public static void addCookie(HttpServletResponse res, AddedCookiePayload payload) {
        validateCookieName(payload.name());
        final Cookie cookie = new Cookie(payload.name(), payload.value());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(payload.maxAge());
        res.addCookie(cookie);
    }

    /**
     * Method responsible for delete cookie based cookie name and {@link HttpServletRequest} with {@link HttpServletResponse}
     * objects injected by Tomcat Servlet Container.
     * 
     * @param req {@link HttpServletRequest} object injected by Tomcat Servlet Container
     * @param res {@link HttpServletResponse} object injected by Tomcat Servlet Container
     * @param cookieName deleting cookie name
     * @author Miłosz Gilga
     * @since 1.0.2
     * 
     * @throws NullPointerException if cookie name is null
     * @throws IllegalArgumentException if cookie name is blank (empty or contain only blank characters)
     */
    public static void deleteCookie(HttpServletRequest req, HttpServletResponse res, String cookieName) {
        validateCookieName(cookieName);
        final Cookie[] cookies = req.getCookies();
        if (cookies == null) return;
        for (Cookie cookie : cookies) {
            if (!cookie.getName().equals(cookieName)) break;
            cookie.setValue("");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            res.addCookie(cookie);
        }
    }

    /**
     * Method responsible for delete multiple cookies value based cookie names in {@link Set} collection container and
     * {@link HttpServletRequest} with {@link HttpServletResponse} objects injected by Tomcat Servlet Container.
     *
     * @param req {@link HttpServletRequest} object injected by Tomcat Servlet Container
     * @param res {@link HttpServletResponse} object injected by Tomcat Servlet Container
     * @param cookiesNames {@link Set} collection representing all cookies (by names) to delete
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws NullPointerException if one of the cookie name is null
     * @throws IllegalArgumentException if one of the cookie name is blank (empty or contain only blank characters)
     */
    public static void deleteMultipleCookies(HttpServletRequest req, HttpServletResponse res, Set<String> cookiesNames) {
        for (final String cookieName : cookiesNames) {
            deleteCookie(req, res, cookieName);
        }
    }

    /**
     * Method responsible for serialization cookie value. 
     * 
     * @param data object data (json or xml) to serialized in base64 algorithm
     * @return cookie value after serialization
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public static String serializeCookieValue(Object data) {
        return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(data));
    }

    /**
     * Method responsible for deserialization cookie value.
     * 
     * @param cookie passed {@link Cookie} object
     * @param cookieClazz reflection representing cookie value object
     * @return deserialized cookie value to T type (extends {@link Object})
     * @param <T> type of deserialization object (extends {@link Object})
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws RuntimeException if object input stream throw a exception
     */
    public static <T> T deserializeCookieValue(Cookie cookie, Class<T> cookieClazz) {
        final byte[] bytes = Base64.getUrlDecoder().decode(cookie.getValue());
        try (final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return cookieClazz.cast(ois.readObject());
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Additional method responsible for getting cookie value (flattering result of {@link #getCookie} method).
     * Cookie name parameter cannot be null. If method not be able to find cookie based cookie name value, return empty
     * {@link Optional}. Otherwise, return {@link Optional} with {@link Cookie}.
     *
     * @param req {@link HttpServletRequest} object injected by Tomcat Servlet Container
     * @param cookieName seraching cookie name
     * @return {@link Optional} with {@link Cookie} if find, otherwise empty {@link Optional} object
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws NullPointerException if cookie name is null
     * @throws IllegalArgumentException if cookie name is blank (empty or contain only blank characters)
     */
    public static Optional<String> getCookieValue(HttpServletRequest req, String cookieName) {
        return getCookie(req, cookieName).map(Cookie::getValue);
    }

    /**
     * Inside private method responsible for validate cookie name. Throws exceptions if cookie name is null or is
     * blank (include only white characters).
     *
     * @param cookieName checking cookie name
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws NullPointerException if cookie name is null
     * @throws IllegalArgumentException if cookie name is blank (empty or contain only blank characters)
     */
    private static void validateCookieName(String cookieName) {
        if (cookieName == null) throw new NullPointerException("Cookie name cannot be null.");
        if (cookieName.isBlank()) {
            throw new IllegalArgumentException("Cookie name cannot be empty contains blank characters.");
        }
    }
}
