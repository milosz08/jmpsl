/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: EnvPropertyHandler.java
 * Last modified: 28/03/2023, 23:14
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
