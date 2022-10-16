/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: MapperFacadesInjector.java
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

import org.slf4j.*;
import org.reflections.util.*;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.lang.reflect.Method;

/**
 * Auto-configuration class responsible for automatically loaded all mapper facades at application start. Class load
 * facades only with {@link MappingFacade} annotation via reflection mechanism.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Configuration
class MapperFacadesInjector {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapperFacadesInjector.class);
    private final ApplicationContext applicationContext;

    MapperFacadesInjector(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        loadMapperFacadeImplByReflection();
    }

    /**
     * Method responsible for loading all mapper facades via Java Reflection mechanism.
     *
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    private void loadMapperFacadeImplByReflection() {
        final org.reflections.Configuration configuration = new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath())
                .setScanners(Scanners.MethodsAnnotated);
        final var reflections = new Reflections(configuration);
        final Set<Method> annotatedMethods = reflections.getMethodsAnnotatedWith(MappingFacade.class);
        for (Method method : annotatedMethods) {
            try {
                method.invoke(applicationContext.getBean(method.getDeclaringClass()));
                LOGGER.info("Successful loaded custom mapping facade: {} via reflection", method.getName());
            } catch (Exception ex) {
                LOGGER.error("Failure loaded custom mapping facade: {} via reflection", method.getName());
                LOGGER.error("Error: {}", ex.getMessage());
            }
        }
    }
}
