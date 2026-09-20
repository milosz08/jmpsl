/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: BufferedFileRes.java
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

package org.jmpsl.file;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Simple POJO class storing generated file data (bytes representation and SFTP server location).
 *
 * <ul>
 *     <li><code>bytesRepresentation</code> - file as bytes array stream</li>
 *     <li><code>location</code> - file location on external SFTP server</li>
 * </ul>
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BufferedFileRes {
    private byte[] bytesRepresentation;
    private String location;
}
