/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: EnvPropertyHandler.java
 * Last modified: 18.11.2022, 01:30
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

package pl.miloszgilga.lib.jmpsl.core.env;

import java.util.List;
import org.springframework.core.env.Environment;

import static java.util.Objects.isNull;

/**
 * Class storing static methods for handling environment variables from <code>application.properties</code> file.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class EnvPropertyHandler {

    /**
     * Available casted boxed types with reflection class type and lambda callback for changed type method.
     *
     * @since 1.0.2
     */
    private static final List<EnvCastData<?>> CAST_DATA_LIST = List.of(
            new EnvCastData<>(String.class, rawData -> rawData),
            new EnvCastData<>(Integer.class, Integer::valueOf),
            new EnvCastData<>(Boolean.class, Boolean::valueOf),
            new EnvCastData<>(Double.class, Double::valueOf),
            new EnvCastData<>(Float.class, Float::valueOf),
            new EnvCastData<>(Character.class, rawData -> rawData.charAt(0)),
            new EnvCastData<>(Byte.class, Byte::valueOf),
            new EnvCastData<>(Long.class, Long::valueOf)
    );

    private EnvPropertyHandler() { }

    /**
     * Static method responsible for getting property from <code>application.properties</code> file based passed type,
     * and additional payload as instance of {@link EnvDataPayload} class.
     *
     * @param envDataPayload instance of {@link EnvDataPayload} with basic informations about getting property
     * @param targetClazz casted property class (only boxed primitives)
     * @return property grabbed from <code>application.properties</code> file
     * @param <T> outgoing property type (primitive wrapper)
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws EnvPropertyNullableException if not found property and property is required
     * @throws EnvNotSupportedTypeException if property type cannot be cast to type T
     */
    @SuppressWarnings("unchecked")
    public static <T> T getPostTypedProperty(EnvDataPayload envDataPayload, Class<T> targetClazz) {
        final Environment env = envDataPayload.getEnv();
        if (isNull(envDataPayload.getDefValue())) {
            final T requiredProperty = env.getProperty(envDataPayload.getName(), targetClazz);
            if (isNull(requiredProperty) && envDataPayload.isRequired()) {
                throw new EnvPropertyNullableException(envDataPayload.getName());
            }
            return requiredProperty;
        }
        for (final EnvCastData<?> castData : CAST_DATA_LIST) {
            if (targetClazz.isAssignableFrom(castData.getCastedClazz())) {
                final T castedDefValue = (T) castData.getCallbackResolver().resolve(envDataPayload.getDefValue());
                return env.getProperty(envDataPayload.getName(), targetClazz, castedDefValue);
            }
        }
        throw new EnvNotSupportedTypeException(envDataPayload.getName(), targetClazz);
    }
}
