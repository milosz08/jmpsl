/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: FileUtil.java
 * Last modified: 03/11/2022, 01:49
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

package org.jmpsl.file;

import org.slf4j.*;
import org.springframework.web.multipart.MultipartFile;

import net.schmizz.sshj.sftp.*;

import java.util.*;
import java.io.IOException;

import org.jmpsl.file.exception.*;

import static org.springframework.util.Assert.*;

/**
 * Class storing static util methods for files and directory's structures.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

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
        notNull(sftpClient, "Sftp client object cannot be null.");
        noNullElements(new Object[] { path, dirName }, "Path and dirname parameters cannot be null.");
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
        notNull(file, "Multipart file object cannot be null");
        if (types.length == 0) throw new IllegalStateException("Content type args must be at least one.");
        if (Arrays.stream(types).noneMatch(t -> Objects.equals(file.getContentType(), t.getContentTypeName()))) {
            LOGGER.error("Attempt to send file with not supported extension. Extension: {}, supported extensions: {}",
                    file.getContentType(), types);
            throw new NotAcceptableFileExtensionException(file.getOriginalFilename(), types);
        }
    }

    /**
     * Static method responsible for checking, if sended file by multipart form data acually exist and is not null.
     * If file not exist or is null, throw {@link SendingFormFileNotExistException}.
     *
     * @param file instance of {@link MultipartFile} with passed file in form data
     * @param message exception message show in JSON response object
     * @param args additional exception message formatting arguments
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws SendingFormFileNotExistException if passed file not exist or is null
     */
    public static void isFileExist(MultipartFile file, String message, Object... args) {
        if (Objects.isNull(file) || file.isEmpty() || file.getSize() == 0) {
            LOGGER.error("Attempt to send file without actual file instance.");
            throw new SendingFormFileNotExistException(message, args);
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
            LOGGER.error("Attempt to send file without actual file instance.");
            throw new SendingFormFileNotExistException();
        }
    }
}
