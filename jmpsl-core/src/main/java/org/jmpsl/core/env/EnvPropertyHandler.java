/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: EnvPropertyHandler.java
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
 * The software is provided “as is”, without warranty of any kind, express or implied, including but not limited
 * to the warranties of merchantability, fitness for a particular purpose and noninfringement. In no event
 * shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an
 * action of contract, tort or otherwise, arising from, out of or in connection with the software or the use
 * or other dealings in the software.
 */

package org.jmpsl.core.env;

import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Objects;


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
        final Environment env = envDataPayload.env();
        if (Objects.isNull(envDataPayload.defValue())) {
            final T requiredProperty = env.getProperty(envDataPayload.name(), targetClazz);
            if (Objects.isNull(requiredProperty) && envDataPayload.required()) {
                throw new EnvPropertyNullableException(envDataPayload.name());
            }
            return requiredProperty;
        }
        for (final EnvCastData<?> castData : CAST_DATA_LIST) {
            if (targetClazz.isAssignableFrom(castData.getCastedClazz())) {
                final T castedDefValue = (T) castData.getCallbackResolver().resolve(envDataPayload.defValue());
                return env.getProperty(envDataPayload.name(), targetClazz, castedDefValue);
            }
        }
        throw new EnvNotSupportedTypeException(envDataPayload.name(), targetClazz);
    }
}
