/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: BasicEnumConverter.java
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
