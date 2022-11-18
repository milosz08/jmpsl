/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: EnvPropertyExtender.java
 * Last modified: 18.11.2022, 00:06
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

package pl.miloszgilga.lib.jmpsl.core.env;

import org.springframework.core.env.Environment;

/**
 * Apply this interface on every enum, which storing refers to environment variables in
 * <code>application.properties</code> file.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public interface IEnvEnumExtender {
    String getProperty(Environment env);
    <T> T getProperty(Environment env, Class<T> targetClazz);
}
