/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: ImageExistPayload.java
 * Last modified: 31/10/2022, 19:12
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

package pl.miloszgilga.lib.jmpsl.gfx.sender;

import lombok.*;

/**
 * Simple POJO class stored result of method in {@link UserImageSftpSender} which available for checking if image already
 * exist.
 *
 * <ul>
 *     <li><code>userHashCode</code> - user hash code from database</li>
 *     <li><code>exist</code> - boolean flag, true if exist otherwise false</li>
 * </ul>
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Data
@AllArgsConstructor
public class ImageExistPayload {
    private String userHashCode;
    private boolean exist;
}
