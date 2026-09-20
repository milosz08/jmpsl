/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: CustomLocaleResolver.java
 * Last modified: 18/05/2023, 01:23
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

package org.jmpsl.core.i18n;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

/**
 * External configuration class for resolve locale by Http request header "Accept-Language". If header does not exist,
 * setting the default locale from property <code>jmpsl.communication.locale.default-locale</code> from
 * <code>application.properties</code> file.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class CustomLocaleResolver extends AcceptHeaderLocaleResolver {

    private final List<Locale> supportedLocales;

    public CustomLocaleResolver(List<Locale> supportedLocales) {
        this.supportedLocales = supportedLocales;
    }

    @Override
    public Locale resolveLocale(HttpServletRequest req) {
        final String acceptLang = req.getHeader("Accept-Language");
        if (StringUtils.isEmpty(acceptLang)) return Locale.getDefault();
        final List<Locale.LanguageRange> localeRangeList = Locale.LanguageRange.parse(acceptLang);
        final Locale currentLocale = Locale.lookup(localeRangeList, supportedLocales);
        LocaleContextHolder.setLocale(currentLocale);
        return currentLocale;
    }
}
