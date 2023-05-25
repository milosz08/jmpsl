/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: MailException.java
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

import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.Set;

import org.jmpsl.core.i18n.LocaleSet;
import org.jmpsl.core.exception.RestServiceServerException;

/**
 * Email exceptions generating JSON object from {@link RestServiceServerException} object structure model.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class MailException {

    /**
     * Exception for unable to send email (when email template is malformed, SMTP server not responding or other email
     * services malfunctions). Exception generated JSON object from {@link RestServiceServerException} object structure model.
     *
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public static class UnableToSendEmailException extends RestServiceServerException {
        public UnableToSendEmailException(Set<String> emailRecipents) {
            super(HttpStatus.SERVICE_UNAVAILABLE, LocaleSet.COMMUNICATION_UNABLE_TO_SEND_EMAIL_EXC,
                Map.of("emailAddress", String.join(", ", emailRecipents)));
        }
    }

    /**
     * Exception for unable to insert parameters for sending email message.
     *
     * @author Miłosz Gilga
     * @since 1.0.2_02
     */
    public static class IncorrectMailParametersException extends RestServiceServerException {
        public IncorrectMailParametersException() {
            super(HttpStatus.SERVICE_UNAVAILABLE, LocaleSet.COMMUNICATION_INCORRECT_MAIL_PARAMS_EXC);
        }
    }
}
