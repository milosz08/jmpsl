/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: MapperConvertersInjector.java
 * Last modified: 07/04/2023, 13:55
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

package org.jmpsl.core.mapper;

import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.modelmapper.AbstractConverter;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * Auto-configuration class responsible for automatically loaded all mapper converters at application start. Class load
 * converters only with {@link MappingConverter} annotation via reflection mechanism.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Slf4j
@Configuration
class MapperConvertersInjector {

    private final ModelMapper modelMapper;
    private final ApplicationContext applicationContext;

    MapperConvertersInjector(ModelMapper modelMapper, ApplicationContext applicationContext) {
        this.modelMapper = modelMapper;
        this.applicationContext = applicationContext;
        loadAllConvertersByReflection();
    }

    /**
     * Method responsible for loading all custom mapper converters via Java Reflection mechanism.
     *
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    private void loadAllConvertersByReflection() {
        final org.reflections.Configuration configuration = new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath())
                .setScanners(Scanners.TypesAnnotated);
        final var reflections = new Reflections(configuration);
        final Set<Class<?>> convertersClazz = reflections.getTypesAnnotatedWith(MappingConverter.class);
        for (Class<?> converterClazz : convertersClazz) {
            final AbstractConverter<?, ?> converter = (AbstractConverter<?, ?>) applicationContext.getBean(converterClazz);
            try {
                modelMapper.addConverter(converter);
                log.info("Successful loaded custom mapper converter: {} via reflection", converterClazz.getSimpleName());
            } catch (Exception ex) {
                log.error("Failure loaded custom mapper converter: {} via reflection", converterClazz.getSimpleName());
                log.error("Error: {}", ex.getMessage());
            }
        }
    }
}
