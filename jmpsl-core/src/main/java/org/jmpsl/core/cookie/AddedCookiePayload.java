/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: AddedCookiePayload.java
 * Last modified: 02/11/2022, 14:32
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

package org.jmpsl.core.cookie;

import lombok.*;

/**
 * POJO payload record for storing adding cookie value. Include cookie name, cookie value and maxAge property.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Builder
public record AddedCookiePayload(String name, String value, int maxAge) {
}
