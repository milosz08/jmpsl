/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: ReturnEmptyStringIfIsNullConverter.java
 * Last modified: 27/10/2022, 04:24
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

package pl.miloszgilga.lib.jmpsl.util.mapper.converter;

import ma.glasnost.orika.*;
import ma.glasnost.orika.metadata.Type;

import org.springframework.stereotype.Component;

import pl.miloszgilga.lib.jmpsl.util.mapper.*;
import pl.miloszgilga.lib.jmpsl.util.StringUtil;

import static java.util.Objects.isNull;
import static pl.miloszgilga.lib.jmpsl.util.mapper.converter.Converter.RETURN_EMPTY_STRING_IF_IS_NULL;

/**
 * Custom mapper converter allows to map A string object to B, where when A object is null, B is empty string. Otherwise
 * return pre-converted value. Insert this converter in mapping chain factory via {@link Converter} enum name.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Component
@MappingConverter
public class ReturnEmptyStringIfIsNullConverter extends CustomConverter<String, String> implements IReflectConverter {

    @Override
    public String convert(String source, Type<? extends String> destinationType, MappingContext mappingContext) {
        return isNull(source) ? StringUtil.EMPTY : source;
    }

    @Override
    public String getConverterType() {
        return RETURN_EMPTY_STRING_IF_IS_NULL.getName();
    }
}
