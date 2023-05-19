/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: MailRequestDto.java
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

package org.jmpsl.communication.mail;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;

import java.util.*;
import jakarta.servlet.http.HttpServletRequest;

import org.jmpsl.communication.mail.MailException.IncorrectMailParametersException;

/**
 * Simple POJO class responsible for stored all data for sending email, including sender, recievier and additional
 * attachements and inline resources for embeded email message.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Getter
@Setter
@Builder
@ToString
public class MailRequestDto {
    private Set<String> sendTo;
    private String sendFrom;
    private String messageSubject;
    private List<ResourceDto> inlineResources;
    private List<ResourceDto> attachments;
    private Locale locale;
    private HttpServletRequest request;
    private String appName;
    private String replyAddress;

    public MailRequestDto(
        Set<String> sendTo, String sendFrom, String messageSubject, List<ResourceDto> inlineResources,
        List<ResourceDto> attachments, Locale locale, HttpServletRequest request, String appName, String replyAddress
    ) {
        if (Objects.isNull(sendTo) || sendTo.isEmpty() || Objects.isNull(sendFrom) || sendFrom.isBlank()) {
            throw new IncorrectMailParametersException();
        }
        this.sendTo = sendTo;
        this.sendFrom = sendFrom;
        this.messageSubject = messageSubject;
        this.inlineResources = Objects.isNull(inlineResources) ? new ArrayList<>() : inlineResources;
        this.attachments = Objects.isNull(attachments) ? new ArrayList<>() : attachments;
        this.locale = locale;
        this.request = request;
        this.appName = appName;
        this.replyAddress = replyAddress;
    }
}
