/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: LocaleConfigurerExtender.java
 * Last modified: 17/03/2023, 16:12
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

package org.jmpsl.core.i18n;

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
 *     <li><code>jmpsl.core.locale.available-locales</code> - avaialble application locales (defined as array
 *     list, for ex. <i>fr,pl,en_GB,en_US</i>). By default it is en_US.</li>
 *     <li><code>jmpsl.core.locale.default-locale</code> - default selected application locale (defined as
 *     locale string, for ex. <i>en_US</i>). By default it is en_US.</li>
 *     <li><code>jmpsl.core.locale.default-locale</code> - application locale bundle path.
 *     Default location is in classpath: 'i18n/messages'.</li>
 * </ul>
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Configuration
public class LocaleConfigurerExtender {

    private final Locale defaultLocale;
    private final String localeBundlePath;
    private final List<Locale> availableLocales;

    LocaleConfigurerExtender(Environment env) {
        this.defaultLocale = new Locale(CoreEnv.__CORE_AVAILABLE_LOCALES.getProperty(env));
        this.localeBundlePath = CoreEnv.__CORE_LOCALE_BUNDLE_PATH.getProperty(env);
        this.availableLocales = Arrays.stream(CoreEnv.__CORE_AVAILABLE_LOCALES.getProperty(env).split(","))
            .map(Locale::new).collect(Collectors.toList());
    }

    @Bean("jmpslMessageSource")
    public MessageSource messageSource() {
        final ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasenames("org.jmpsl.i18n.messages", localeBundlePath);
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
