/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: MapperConvertersInjector.java
 * Last modified: 16/10/2022, 00:50
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

package pl.miloszgilga.lib.jmpsl.util.mapper;

import ma.glasnost.orika.*;
import ma.glasnost.orika.converter.ConverterFactory;

import org.slf4j.*;
import org.reflections.util.*;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import org.springframework.context.annotation.*;
import org.springframework.context.ApplicationContext;
import pl.miloszgilga.lib.jmpsl.util.mapper.converter.*;

import java.util.Set;
import java.lang.reflect.Method;

/**
 * Auto-configuration class responsible for automatically loaded all mapper converters at application start. Class load
 * converters only with {@link MappingConverter} annotation via reflection mechanism.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Configuration
class MapperConvertersInjector {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapperConvertersInjector.class);

    private final ConverterFactory converterFactory;
    private final MapperFactory mapperFactory;
    private final ApplicationContext applicationContext;

    MapperConvertersInjector(MapperFactory mapperFactory, ApplicationContext applicationContext) {
        this.mapperFactory = mapperFactory;
        this.converterFactory = mapperFactory.getConverterFactory();
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
            final IMapperConverterLoader loader = applicationContext.getBean(IMapperConverterLoader.class);
            final CustomConverter<?, ?> converter = (CustomConverter<?, ?>) applicationContext.getBean(converterClazz);
            try {
                final Method method = converterClazz.getMethod("getConverterType");
                final String converterName = (String) method.invoke(converter);
                if (loader.loadConverters().stream().anyMatch(c -> c.getName().equalsIgnoreCase(converterName)) ||
                        converterClazz.isAnnotationPresent(ImmediatelyLoadConverter.class)) {
                    converterFactory.registerConverter(converterName, converter);
                    LOGGER.info("Successful loaded custom mapper converter: {} via reflection", converterClazz.getSimpleName());
                }
            } catch (Exception ex) {
                LOGGER.error("Failure loaded custom mapper converter: {} via reflection", converterClazz.getSimpleName());
                LOGGER.error("Error: {}", ex.getMessage());
            }
        }
    }

    @Bean
    public MapperFacade mapperFacade() {
        return mapperFactory.getMapperFacade();
    }
}
