/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: IReflectConverter.java
 * Last modified: 16/10/2022, 00:58
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

package pl.miloszgilga.lib.jmpsl.core.mapper;

import ma.glasnost.orika.CustomConverter;

/**
 * Implement this interface on custom converter class (extending {@link CustomConverter} class) and override
 * <code>getConverterType()</code> method for return converter name (to enable auto-load at application start).
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public interface IReflectConverter {
    String getConverterType();
}
