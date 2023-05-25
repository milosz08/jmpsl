/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: LocaleConfigurerExtender.java
 * Last modified: 19/05/2023, 23:59
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

import org.apache.commons.lang3.ArrayUtils;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

import org.springframework.core.env.Environment;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.util.List;
import java.util.Arrays;
import java.util.Locale;
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
 *     <li><code>jmpsl.core.locale.messages-paths</code> - application locale bundle path.
 *     Default location is in classpath: 'i18n/messages'.</li>
 * </ul>
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Configuration
public class LocaleConfigurerExtender {

    private final Locale defaultLocale;
    private final List<String> localeBundlePaths;
    private final List<Locale> availableLocales;

    LocaleConfigurerExtender(Environment env) {
        this.defaultLocale = new Locale(CoreEnv.__CORE_DEFAULT_LOCALE.getProperty(env));
        this.localeBundlePaths = Arrays.stream(CoreEnv.__CORE_LOCALE_BUNDLE_PATH.getProperty(env).split(",")).toList();
        this.availableLocales = Arrays.stream(CoreEnv.__CORE_AVAILABLE_LOCALES.getProperty(env).split(","))
            .map(Locale::new).toList();
    }

    @Primary
    @Bean("jmpslMessageSource")
    public MessageSource messageSource() {
        final ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        String[] basenames = localeBundlePaths.toArray(new String[localeBundlePaths.size() + 1]);
        ArrayUtils.shift(basenames, 1);
        basenames[0] = "org.jmpsl.i18n.messages";
        resourceBundleMessageSource.addBasenames(basenames);
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
