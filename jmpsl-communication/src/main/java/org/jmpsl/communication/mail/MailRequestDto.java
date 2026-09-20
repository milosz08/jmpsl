/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: MailRequestDto.java
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
