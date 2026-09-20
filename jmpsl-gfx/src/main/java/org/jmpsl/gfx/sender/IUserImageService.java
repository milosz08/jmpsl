/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: IUserImageService.java
 * Last modified: 28/03/2023, 23:14
 * Project name: jmps-library
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     <http://www.apache.org/license/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the license.
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
