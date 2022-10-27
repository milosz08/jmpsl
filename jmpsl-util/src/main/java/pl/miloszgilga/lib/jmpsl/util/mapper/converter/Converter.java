/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: Converter.java
 * Last modified: 27/10/2022, 04:06
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

import lombok.*;

/**
 * Custom mapper converter names. Insert this refer name in mapper chain converted method.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Getter
@AllArgsConstructor
public enum Converter {
    DATE_FROM_STRING_TO_OBJECT("date-from-string-to-object"),
    CAPITALIZED_FIRST_LETTER("capitalized-first-letter"),
    CHANGE_ALL_LETTERS_TO_LOWER("change-all-letters-to-lower"),
    INSERT_NULL_IF_STRING_IS_EMPTY("unsert-null-if-string-is-empty"),
    RETURN_EMPTY_STRING_IF_IS_NULL("return-empty-string-if-is-null");

    private final String name;
}
