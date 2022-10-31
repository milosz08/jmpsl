/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: FileUtil.java
 * Last modified: 30/10/2022, 14:18
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

package pl.miloszgilga.lib.jmpsl.file;

import net.schmizz.sshj.sftp.*;

import java.util.List;
import java.io.IOException;

import static org.springframework.util.Assert.*;

/**
 * Class storing static util methods for manipulate files and directory's structures.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
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
        notNull(sftpClient, "Sftp client object cannot be null.");
        noNullElements(new Object[] { path, dirName }, "Path and dirname parameters cannot be null.");
        final List<RemoteResourceInfo> ls = sftpClient.ls(path);
        if (ls.stream().noneMatch(p -> p.getName().equals(dirName))) {
            sftpClient.cd(path);
            sftpClient.mkdir(dirName);
        }
    }
}
