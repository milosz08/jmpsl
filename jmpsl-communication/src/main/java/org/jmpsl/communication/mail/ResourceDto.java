/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: ResourceDto.java
 * Last modified: 25/05/2023, 15:38
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

package org.jmpsl.communication.mail;

import lombok.Builder;

import java.io.File;

/**
 * Simple POJO record representing mail resource data (resource name with extension) ex. inline resource or attachment.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Builder
public record ResourceDto(String mimeVariableName, File fileHandler) {
}
