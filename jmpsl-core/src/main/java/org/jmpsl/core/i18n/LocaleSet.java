/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: LocaleSet.java
 * Last modified: 17/03/2023, 16:02
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
 * The software is provided “as is”, without warranty of any kind, express or implied, including but not limited
 * to the warranties of merchantability, fitness for a particular purpose and noninfringement. In no event
 * shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an
 * action of contract, tort or otherwise, arising from, out of or in connection with the software or the use
 * or other dealings in the software.
 */

package org.jmpsl.core.i18n;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum set storing all placeholders of internationalization messages for all JMPS library modules.
 * Implementing {@link ILocaleEnumSet} interface from JMPSL core module.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 * @see ILocaleEnumSet
 */
@Getter
@RequiredArgsConstructor
public enum LocaleSet implements ILocaleEnumSet {

    // JMPSL Communication module
    COMMUNICATION_UNABLE_TO_SEND_EMAIL_EXC              ("jmpsl.communication.exception.UnableToSendEmailException"),
    COMMUNICATION_INCORRECT_MAIL_PARAMS_EXC             ("jmpsl.communication.exception.IncorrectMailParametersException"),

    // JMPSL Core module
    NO_HANDLER_FOUND_EXC                                ("jmpsl.core.exception.NoHandlerFoundException"),
    HTTP_MESSAGE_NOT_READABLE_EXC                       ("jmpsl.core.exception.HttpMessageNotReadableException"),
    INTERNAL_SERVER_ERROR_EXC                           ("jmpsl.core.exception.InternalServerError"),

    // JMPSL File module
    FILE_UNABLE_TO_PERFORM_SFTP_ACTION_EXC              ("jmpsl.file.exception.UnableToPerformSftpActionException"),
    FILE_EXTERNAL_FILE_SERVER_MALFUNCTION_EXC           ("jmpsl.file.exception.ExternalFileServerMalfunctionException"),
    FILE_NOT_ACCEPTABLE_FILE_EXTENSION_EXC              ("jmpsl.file.exception.NotAcceptableFileExtensionException"),
    FILE_HASH_CODE_FORMAT_EXC                           ("jmpsl.file.exception.HashCodeFormatException"),
    FILE_SENDING_FORM_FILE_NOT_EXIST_EXC                ("jmpsl.file.exception.SendingFormFileNotExistException"),

    // JMPSL Gfx module
    GFX_IMAGE_NOT_SUPPORTED_DIMENSIONS_EXC              ("jmpsl.gfx.exception.ImageNotSupportedDimensionsException"),
    GFX_FONT_SIZE_NOT_SUPPORTED_EXC                     ("jmpsl.gfx.exception.FontSizeNotSupportedException"),
    GFX_TOO_MUCH_INITIALS_CHARACTERS_EXC                ("jmpsl.gfx.exception.TooMuchInitialsCharactersException"),

    // JMPSL OAuth2 module
    OAUTH2_AUTHENTICATION_PROCESSING_EXC                ("jmpsl.oauth2.exception.OAuth2AuthenticationProcessingException"),
    OAUTH2_SUPPLIER_NOT_IMPLEMENTED_EXC                 ("jmpsl.oauth2.exception.OAuth2SupplierNotImplementedException"),
    OAUTH2_URI_NOT_SUPPORTED_EXC                        ("jmpsl.oauth2.exception.OAuth2UriNotSupportedException"),

    // JMPSL Security module
    SECURITY_AUTHENTICATION_EXC                         ("jmpsl.security.AuthenticationException");

    /**
     * Internationalization property holder.
     *
     * @since 1.0.2
     */
    private final String holder;
}
