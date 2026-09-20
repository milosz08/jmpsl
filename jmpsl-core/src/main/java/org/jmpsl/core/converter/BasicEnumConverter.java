/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: BasicEnumConverter.java
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

package org.jmpsl.core.converter;

import jakarta.persistence.AttributeConverter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Abstract converter class available to create custom enum converter. The converter is responsible for converting the
 * way enum values are stored in the database from numeric values to text, passed through a method from the interface
 * {@link IBasicEnumConverter}. Converter changeTo create a converter, extend the converter class with this abstract
 * class, and in the constructor pass the .class parameter of the created class implementing the abstraction.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public abstract class BasicEnumConverter<T extends IBasicEnumConverter> implements AttributeConverter<T, String> {

    private final Class<T> enumClazz;

    /**
     * Construct via super keyword with passing {@link Class} type.
     *
     * @param enumClazz custom converter class type
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    protected BasicEnumConverter(Class<T> enumClazz) {
        this.enumClazz = enumClazz;
    }

    /**
     * Method responsible for convert enum value (from number) to string value before save in database.
     *
     * @param attribute the entity attribute value to be converted
     * @return enum attribute name
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    @Override
    public String convertToDatabaseColumn(T attribute) {
        if (Objects.isNull(attribute)) return null;
        return attribute.getEnumName();
    }

    /**
     * Method responsible for convert enum value from database and return T enum type of converted value.
     *
     * @param dbData the data from the database column to be converted
     * @return T type of converter enum value from database
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    @Override
    public T convertToEntityAttribute(String dbData) {
        if (Objects.isNull(dbData)) return null;
        return Stream.of(enumClazz.getEnumConstants())
            .filter(g -> g.getEnumName().equals(dbData))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
