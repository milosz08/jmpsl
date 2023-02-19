/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: ServletMailService.java
 * Last modified: 15/02/2023, 02:11
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

package org.jmpsl.communication.mail;

import org.slf4j.*;
import freemarker.template.*;

import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.Map;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import static org.jmpsl.communication.mail.MailException.UnableToSendEmailException;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED;

/**
 * Custom Java Spring Bean service responsible for management and sending emails to user/multiple users with basic
 * email data, inline blob data and attachments.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Service
public class ServletMailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServletMailService.class);

    private final JavaMailSender sender;
    private final Configuration freemarkerConfiguration;

    public ServletMailService(JavaMailSender sender, Configuration freemarkerConfiguration) {
        this.sender = sender;
        this.freemarkerConfiguration = freemarkerConfiguration;
    }

    /**
     * Method responsible for sending email to selected users with inline resources, attachments and other informations
     * (based request POJO DTO class {@link MailRequestDto}) and insert mapped attributes from model to freemarker
     * template based passed enum templates class.
     *
     * @param reqDto POJO mail data transfer object. Refer to {@link MailRequestDto} class
     * @param model template properties as key->value to change in Freemarker template
     * @param template freemarker template enum type implements interface {@link IMailEnumeratedTemplate}
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws UnableToSendEmailException when method unable to send email to request users
     */
    public void sendEmail(final MailRequestDto reqDto, final Map<String, Object> model, IMailEnumeratedTemplate template) {
        final MimeMessage mimeMessage = sender.createMimeMessage();
        try {
            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            final Template mailTemplate = freemarkerConfiguration.getTemplate(template.getTemplateName());

            for (final String client : reqDto.getSendTo()) {
                mimeMessageHelper.setTo(client);
            }
            mimeMessageHelper.setText(FreeMarkerTemplateUtils.processTemplateIntoString(mailTemplate, model), true);
            for (final ResourceDto inlineRes : reqDto.getInlineResources()) {
                mimeMessageHelper.addInline(inlineRes.mimeVariableName(), inlineRes.fileHandler());
            }
            for (final ResourceDto attachementRes : reqDto.getAttachments()) {
                mimeMessageHelper.addAttachment(attachementRes.mimeVariableName(), attachementRes.fileHandler());
            }
            mimeMessageHelper.setSubject(reqDto.getMessageSubject());
            mimeMessageHelper.setFrom(reqDto.getSendFrom());

            sender.send(mimeMessage);
            LOGGER.info("Email message from template {} was successfully send. Request parameters: {}",
                    template.getTemplateName(), reqDto);
            return;

        } catch (MessagingException| IOException ex) {
            LOGGER.error("Sender mail exception. {}. Request parameters: {}", ex.getMessage(), reqDto);
        } catch (TemplateException ex) {
            LOGGER.error("Template exception. {}. Request parameters: {}", ex.getMessage(), reqDto);
        } catch (Exception ex) {
            LOGGER.error("Unexpected mail sender exception. {}, Request parameters: {}", ex.getMessage(), reqDto);
        }
        throw new UnableToSendEmailException(reqDto.getSendTo());
    }
}
