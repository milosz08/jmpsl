/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: DateFromStringToObjectConverter.java
 * Last modified: 27/10/2022, 04:12
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

package pl.miloszgilga.lib.jmpsl.core.mapper.converter;

import ma.glasnost.orika.*;
import ma.glasnost.orika.metadata.Type;

import org.springframework.stereotype.Component;

import pl.miloszgilga.lib.jmpsl.core.mapper.*;
import pl.miloszgilga.lib.jmpsl.core.TimeUtil;

import java.util.Date;

import static pl.miloszgilga.lib.jmpsl.core.mapper.converter.Converter.DATE_FROM_STRING_TO_OBJECT;

/**
 * Custom mapper converter allows to deserialize date from {@link Date} mapped A object into string date format (mapped
 * B object). Insert this converter in mapping chain factory via {@link Converter} enum name.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Component
@MappingConverter
public class DateFromStringToObjectConverter extends CustomConverter<String, Date> implements IReflectConverter {

    @Override
    public Date convert(String source, Type<? extends Date> destinationType, MappingContext mappingContext) {
        return TimeUtil.deserialize(source).orElse(new Date());
    }

    @Override
    public String getConverterType() {
        return DATE_FROM_STRING_TO_OBJECT.getName();
    }
}
