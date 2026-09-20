/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: FileUtil.java
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

import lombok.extern.slf4j.Slf4j;

import net.schmizz.sshj.sftp.RemoteResourceInfo;
import net.schmizz.sshj.sftp.StatefulSFTPClient;

import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Arrays;
import java.util.Objects;
import java.io.IOException;

import org.jmpsl.file.exception.SendingFormFileNotExistException;
import org.jmpsl.file.exception.NotAcceptableFileExtensionException;

/**
 * Class storing static util methods for files and directory's structures.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Slf4j
public class FileUtil {

    private FileUtil() {
    }

    /**
     * Static method responsible for create directory in selected path, if does not exist yet. Throws {@link IOException}
     * if passed base directory not exist or unable to invoke creating directory action.
     *
     * @param sftpClient instance of {@link StatefulSFTPClient} class.
     * @param path base directory path, where method put new directory (simple name, without any slashes)
     * @param dirName name of creating directory (simple name, without any slashes)
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IOException if {@link StatefulSFTPClient} class instance unable to create new directory
     * @throws IllegalArgumentException if {@link StatefulSFTPClient} or path or dirName is null
     */
    public static void createDirIfNotExist(StatefulSFTPClient sftpClient, String path, String dirName) throws IOException {
        Assert.notNull(sftpClient, "Sftp client object cannot be null.");
        Assert.noNullElements(new Object[] { path, dirName }, "Path and dirname parameters cannot be null.");
        final List<RemoteResourceInfo> ls = sftpClient.ls(path);
        if (ls.stream().noneMatch(p -> p.getName().equals(dirName))) {
            sftpClient.cd(path);
            sftpClient.mkdir(dirName);
        }
    }

    /**
     * Static method responsible for checking, if passed file extension is matched with passed content types multiple
     * arguments array (enums of {@link ContentType} class). If it is invalid, throw
     * {@link NotAcceptableFileExtensionException} exception.
     *
     * @param file instance of {@link MultipartFile} data from Tomcat Servlet Container
     * @param types multiple enum types of {@link ContentType} class
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if passed {@link MultipartFile} instance is null
     * @throws IllegalStateException if types argument is empty array
     * @throws NotAcceptableFileExtensionException if file extension is not supported
     */
    public static void checkIfFileExtensionIsSupported(MultipartFile file, ContentType... types) {
        Assert.notNull(file, "Multipart file object cannot be null");
        if (types.length == 0) throw new IllegalStateException("Content type args must be at least one.");
        if (Arrays.stream(types).noneMatch(t -> Objects.equals(file.getContentType(), t.getContentTypeName()))) {
            log.error("Attempt to send file with not supported extension. Extension: {}, supported extensions: {}",
                    file.getContentType(), types);
            throw new NotAcceptableFileExtensionException(types);
        }
    }

    /**
     * Static method responsible for checking, if sended file by multipart form data acually exist and is not null.
     * If file not exist or is null, throw {@link SendingFormFileNotExistException}.
     *
     * @param file instance of {@link MultipartFile} with passed file in form data
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws SendingFormFileNotExistException if passed file not exist or is null
     */
    public static void isFileExist(MultipartFile file) {
        if (Objects.isNull(file) || file.isEmpty() || file.getSize() == 0) {
            log.error("Attempt to send file without actual file instance.");
            throw new SendingFormFileNotExistException();
        }
    }
}
