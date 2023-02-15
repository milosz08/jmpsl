/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: ChangeAllLettersToLowerConverter.java
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

import java.util.Locale;

import org.jmpsl.core.mapper.MappingConverter;

/**
 * Custom mapper converter allows to change all mapping string A object value letters to lower.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Component
@MappingConverter
public class ChangeAllLettersToLowerConverter extends AbstractConverter<String, String> {

    @Override
    protected String convert(String source) {
        return source.toLowerCase(Locale.ROOT);
    }
}
