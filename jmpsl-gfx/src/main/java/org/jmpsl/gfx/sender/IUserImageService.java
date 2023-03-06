/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: IUserImageService.java
 * Last modified: 03/11/2022, 00:42
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

package org.jmpsl.gfx.sender;

import org.jmpsl.gfx.ImageExtension;
import org.jmpsl.gfx.generator.BufferedImageGeneratorRes;
import org.jmpsl.gfx.generator.BufferedImageGeneratorPayload;

/**
 * Universal interface defined methods for generate default user image and save and save already generated user image.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
interface IUserImageService {
    BufferedImageGeneratorRes generateAndSaveDefaultUserImage(BufferedImageGeneratorPayload payload, ImageExtension extension);
    BufferedImageRes saveUserImage(BufferedImageSenderPayload payload, ImageExtension extension);
    void deleteUserImage(BufferedImageDeletePayload payload);
}
