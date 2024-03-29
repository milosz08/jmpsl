/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: MapperConvertersInjector.java
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
