/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: FileUtil.java
 * Last modified: 06/03/2023, 17:16
 * Project name: jmps-library
 *
 * Licensed under the MIT license; you may not use this file except in compliance with the License.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * THE ABOVE COPYRIGHT NOTICE AND THIS PERMISSION NOTICE SHALL BE INCLUDED IN ALL COPIES OR
 * SUBSTANTIAL PORTIONS OF THE SOFTWARE.
 *
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited
 * to the warranties of merchantability, fitness for a particular purpose and noninfringement. In no event
 * shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an
 * action of contract, tort or otherwise, arising from, out of or in connection with the software or the use
 * or other dealings in the software.
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
