/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: SecurityPathExcluder.java
 * Last modified: 22/10/2022, 02:07
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

package pl.miloszgilga.lib.jmpsl.security.excluder;

import io.jsonwebtoken.lang.Assert;
import org.slf4j.*;
import org.reflections.util.*;
import org.reflections.Reflections;

import org.springframework.util.AntPathMatcher;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.web.util.matcher.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import javax.servlet.http.HttpServletRequest;

import java.util.*;
import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.lang.annotation.Annotation;

import static java.util.Objects.isNull;
import static org.reflections.scanners.Scanners.*;
import static org.springframework.http.HttpMethod.*;

import static pl.miloszgilga.lib.jmpsl.security.ApplicationMode.DEV;

/**
 * Spring Bean configuration class responsible for reflecting and loading all excluded routes informations into array.
 * Before use this class, add property <code>jmpsl.security.oauth2-active</code> in <code>application.properties</code>
 * file. Class is auto-loaded by Spring Context. To apply controller to exclude, put {@link ControllerSecurityPathExclude}
 * annotation. To apply method to exclude, put {@link MethodSecurityPathExclude} annotation.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Configuration
public class SecurityPathExcluder {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityPathExcluder.class);

    private static final Set<RestMethodClassWithType> REST_ANNOTATIONS = Set.of(
            new RestMethodClassWithType(GET, GetMapping.class),
            new RestMethodClassWithType(POST, PostMapping.class),
            new RestMethodClassWithType(PUT, PutMapping.class),
            new RestMethodClassWithType(PATCH, PatchMapping.class),
            new RestMethodClassWithType(DELETE, DeleteMapping.class)
    );

    private final boolean isOAuth2Active;
    private final boolean isDevelopmentModeActive;

    private final List<RestMethodTypeWithPath> disabledMethodPaths = new ArrayList<>();
    private final List<String> disabledNoMethodPaths = new ArrayList<>(List.of("/", "/error"));

    SecurityPathExcluder(Environment environment) {
        isOAuth2Active = Boolean.parseBoolean(environment.getProperty("jmpsl.security.oauth2-active", "false"));
        isDevelopmentModeActive = environment.getActiveProfiles()[0].equals(DEV.getModeName());
        loadAllDisabledPathsByReflections();
        LOGGER.info("Successful loaded excluded paths from Spring Security Context: {}", disabledNoMethodPaths);
        LOGGER.info("Successful loaded method excluded paths from Spring Security Context: {}", disabledMethodPaths);
    }

    /**
     * Inner method responsible for initially load all configuration.
     *
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    private void loadAllDisabledPathsByReflections() {
        if (isOAuth2Active) disabledNoMethodPaths.add("/oauth2/**");
        if (isDevelopmentModeActive) disabledNoMethodPaths.add("/h2-console/**");
        loadAllExcludedPathFromSecurityByReflectionFromMethods();
        loadAllExcludedPathFromSecurityByReflectionFromControllers();
    }

    /**
     * Method responsible for injecting reflected paths from controllers and rest methods into Spring Security filter
     * chain data. Put this method before authenticationEntryPoint and after oauth2Login (if you using OAuth2).
     *
     * @return {@link HttpSecurity} object with injected excluded paths from Spring Security Context
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws Exception in case of unexpected Spring Security filter chain issues
     * @throws IllegalArgumentException if passed {@link HttpSecurity} object is null
     */
    public HttpSecurity loadExcludedPathFromSpringSecurityContext(HttpSecurity httpSecurity) throws Exception {
        Assert.notNull(httpSecurity, "HttpSecurity object cannot be null.");
        final List<RequestMatcher> requestMatchers = new ArrayList<>();
        for (RestMethodTypeWithPath path : disabledMethodPaths) {
            requestMatchers.add(new AntPathRequestMatcher(path.getPath(), path.getHttpMethod().name()));
        }
        httpSecurity
                .authorizeRequests()
                .requestMatchers(requestMatchers.toArray(new RequestMatcher[0])).permitAll()
                .antMatchers(disabledNoMethodPaths.toArray(new String[0])).permitAll()
                .anyRequest().authenticated();
        return httpSecurity;
    }

    /**
     * Method responsible for determinate paths to excluded from JWT Authentication filter. Based passed in parameters
     * {@link HttpServletRequest} object and {@link AntPathMatcher} object return true, if path should be excluded,
     * otherwise return false.
     *
     * @return true, if path should be excluded, otherwise return false
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if passed {@link HttpServletRequest} object is null
     */
    public boolean excludePathFromJwtFilter(final HttpServletRequest req) {
        Assert.notNull(req, "HttpServletRequest object cannot be null.");
        final List<String> allExcludedPaths = new ArrayList<>(disabledNoMethodPaths);
        disabledMethodPaths.forEach(p -> allExcludedPaths.add(p.getPath()));
        final AntPathMatcher matcher = new AntPathMatcher();
        return allExcludedPaths.stream().anyMatch(p -> matcher.match(p, req.getServletPath()));
    }

    /**
     * Inner method responsible for reflective scanning and loading excluded paths from methods. For REST annotations
     * with <code>value()</code> parameter, return customized path (from value data). For REST annotations without
     * <code>value()</code> parameter, return object with base controller path and HTTP method.
     *
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws RestAnnotationNotFoundException if REST method in controller not included any type of annotation
     * @throws RestAnnotationMismatchException if REST method in controller not included one of the REST method
     */
    private void loadAllExcludedPathFromSecurityByReflectionFromMethods() {
        final org.reflections.Configuration configuration = new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath())
                .setScanners(MethodsAnnotated);

        final Reflections reflections = new Reflections(configuration);
        final Set<Method> exclPathsClazz = reflections.getMethodsAnnotatedWith(MethodSecurityPathExclude.class);
        final String annotationName = MethodSecurityPathExclude.class.getSimpleName();

        for (final Method method : exclPathsClazz) {
            final String basePath = getPathFromClassToExclude(method.getDeclaringClass());
            final Annotation[] restActionAnnotations = method.getAnnotations();
            if (restActionAnnotations.length == 0) {
                throw new RestAnnotationNotFoundException(annotationName, parseStringRestAnnotations());
            }
            for (final Annotation annotation : restActionAnnotations) {
                final Class<? extends Annotation> annotationClazz = annotation.annotationType();
                try {
                    final RestMethodClassWithType restMethodClassWithType = REST_ANNOTATIONS.stream()
                            .filter(c -> c.getMethodClazz().equals(annotationClazz))
                            .findFirst()
                            .orElseThrow(() -> { throw new RuntimeException(); });

                    final Method annotationMethod = annotationClazz.getDeclaredMethod("value");
                    final String[] path = (String[]) annotationMethod.invoke(annotation);
                    final String finalPath = path.length == 0 ? basePath + "/**" : basePath + (path[0].contains("{")
                            ? path[0].substring(0, path[0].indexOf('{')) : path[0]) + "/**";
                    final RestMethodTypeWithPath res = RestMethodTypeWithPath.builder()
                            .path(finalPath)
                            .httpMethod(restMethodClassWithType.getHttpMethod())
                            .build();
                    disabledMethodPaths.add(res);
                    break;
                } catch (Exception ex) {
                    throw new RestAnnotationMismatchException(parseStringRestAnnotations(), annotationName);
                }
            }
        }
    }

    /**
     * Inner method responsible for reflective scanning and loading excluded paths from controllers (full base paths).
     *
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws RestAnnotationNotFoundException if REST method in controller not included any type of annotation
     * @throws IllegalStateException if {@link RequestMapping} annotation not include custom path (value) property
     */
    private void loadAllExcludedPathFromSecurityByReflectionFromControllers() {
        final org.reflections.Configuration configuration = new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath())
                .setScanners(TypesAnnotated);

        final Reflections reflections = new Reflections(configuration);
        final Set<Class<?>> exclPathsClazz = reflections.getTypesAnnotatedWith(ControllerSecurityPathExclude.class);
        final String annotationName = ControllerSecurityPathExclude.class.getSimpleName();

        for (final Class<?> exclPathClazz : exclPathsClazz) {
            final Annotation annotation = exclPathClazz.getDeclaredAnnotation(RequestMapping.class);
            if (isNull(annotation)) {
                throw new RestAnnotationNotFoundException(annotationName);
            }
            if (exclPathClazz.getDeclaredAnnotation(RequestMapping.class).value().length == 0) {
                throw new IllegalStateException("@RequestMapping annotation must have custom path in value property.");
            }
            disabledNoMethodPaths.add(getPathFromClassToExclude(exclPathClazz) + "/**");
        }
    }

    /**
     * Inner method responsible for extracting path to exclude from Class type of controller passed in method
     * parameter.
     *
     * @param controllerClazz {@link Class} type of controller
     * @return extracted path from controller {@link RequestMapping} annotation value property
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if controllerClazz parameter is null
     * @throws RestAnnotationNotFoundException if {@link RequestMapping} annotation not found in controller declaration
     */
    private String getPathFromClassToExclude(final Class<?> controllerClazz) {
        Assert.notNull(controllerClazz, "Class type of controller cannot be null.");
        return Arrays.stream(controllerClazz.getAnnotationsByType(RequestMapping.class))
                .map(v -> v.value()[0])
                .findFirst()
                .orElseThrow(() -> {
                    throw new RestAnnotationNotFoundException(RequestMapping.class.getSimpleName());
                });
    }

    /**
     * Inner method responsible for format Spring Web REST annotations into array of object look, ex:
     * [ { foo, barr }, { foo, barr } ]
     *
     * @return formatted Spring Web REST annotations data
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    private String parseStringRestAnnotations() {
        return "[ " + REST_ANNOTATIONS.stream().map(c -> "@" + c.className()).collect(Collectors.joining(", ")) + " ]";
    }
}
