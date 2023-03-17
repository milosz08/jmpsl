/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: LocaleConfigurerExtender.java
 * Last modified: 19/02/2023, 17:24
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

package org.jmpsl.communication.locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

import org.springframework.core.env.Environment;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.util.List;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;
import java.nio.charset.StandardCharsets;

import org.jmpsl.core.CoreEnv;

/**
 * Configuration class responsible for generate basic configuration for locale REST services. To configure locale, set
 * this following elements in <code>application.properties</code> file:
 *
 * <ul>
 *     <li><code>jmpsl.communication.locale.available-locales</code> - avaialble application locales (defined as array
 *     list, for ex. <i>fr,pl,en_GB,en_US</i>). By default it is en_US.</li>
 *     <li><code>jmpsl.communication.locale.default-locale</code> - default selected application locale (defined as
 *     locale string, for ex. <i>en_US</i>). By default it is en_US.</li>
 * </ul>
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Configuration
public class LocaleConfigurerExtender {

    private final Locale defaultLocale;
    private final List<Locale> availableLocales;

    LocaleConfigurerExtender(Environment env) {
        this.defaultLocale = new Locale(CoreEnv.__CORE_AVAILABLE_LOCALES.getProperty(env));
        this.availableLocales = Arrays.stream(CoreEnv.__CORE_AVAILABLE_LOCALES.getProperty(env).split(","))
            .map(Locale::new).collect(Collectors.toList());
    }

    @Bean("jmpslMessageSource")
    public MessageSource messageSource() {
        final ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasenames("org.jmpsl.i18n.messages", "locale/messages");
        resourceBundleMessageSource.setDefaultEncoding(String.valueOf(StandardCharsets.UTF_8));
        return resourceBundleMessageSource;
    }

    @Bean("jmpslCustomLocalResolver")
    public LocaleResolver localeResolver() {
        LocaleContextHolder.setDefaultLocale(defaultLocale);
        final CustomLocaleResolver customLocaleResolver = new CustomLocaleResolver(availableLocales);
        customLocaleResolver.setDefaultLocale(defaultLocale);
        customLocaleResolver.setSupportedLocales(availableLocales);
        return customLocaleResolver;
    }

    @Bean("jmpslMethodValidationPostProcessor")
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
