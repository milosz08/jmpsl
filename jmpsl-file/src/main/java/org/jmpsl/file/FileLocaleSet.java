/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: FileLocaleSet.java
 * Last modified: 17/03/2023, 10:33
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

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.jmpsl.core.i18n.ILocaleEnumSet;

/**
 * Enum set storing all placeholders of internationalization messages for file JMPS library module.
 * Implementing {@link ILocaleEnumSet} interface from JMPSL core module.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 * @see ILocaleEnumSet
 */
@Getter
@RequiredArgsConstructor
public enum FileLocaleSet implements ILocaleEnumSet {
    UNABLE_TO_PERFORM_SFTP_ACTION_EXC           ("jmpsl.file.exception.UnableToPerformSftpActionException"),
    EXTERNAL_FILE_SERVER_MALFUNCTION_EXC        ("jmpsl.file.exception.ExternalFileServerMalfunctionException"),
    NOT_ACCEPTABLE_FILE_EXTENSION_EXC           ("jmpsl.file.exception.NotAcceptableFileExtensionException"),
    HASH_CODE_FORMAT_EXC                        ("jmpsl.file.exception.HashCodeFormatException"),
    SENDING_FORM_FILE_NOT_EXIST_EXC             ("jmpsl.file.exception.SendingFormFileNotExistException");

    /**
     * Internationalization property holder.
     *
     * @since 1.0.2
     */
    private final String holder;
}
