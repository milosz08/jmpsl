/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: DateFromStringToObjectConverter.java
 * Last modified: 13/02/2023, 02:49
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

package org.jmpsl.core.mapper.converter;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.Date;

import pl.miloszgilga.lib.jmpsl.core.mapper.*;

import static pl.miloszgilga.lib.jmpsl.core.TimeUtil.deserialize;

/**
 * Custom mapper converter allows to deserialize date from {@link Date} mapped A object into string date format (mapped
 * B object).
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Component
@MappingConverter
public class DateFromStringToObjectConverter extends AbstractConverter<String, Date> {

    @Override
    public Date convert(String source) {
        return deserialize(source).orElse(new Date());
    }
}
