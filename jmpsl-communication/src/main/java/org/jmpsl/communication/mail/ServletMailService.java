/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: ServletMailService.java
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

import lombok.extern.slf4j.Slf4j;

import freemarker.template.Template;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.Map;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.jmpsl.communication.mail.MailException.UnableToSendEmailException;

/**
 * Custom Java Spring Bean service responsible for management and sending emails to user/multiple users with basic
 * email data, inline blob data and attachments.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Slf4j
@Service
public class ServletMailService {

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
            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

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
            log.info("Email message from template {} was successfully send. Request parameters: {}",
                template.getTemplateName(), reqDto);
            return;

        } catch (MessagingException | IOException ex) {
            log.error("Sender mail exception. {}. Request parameters: {}", ex.getMessage(), reqDto);
        } catch (TemplateException ex) {
            log.error("Template exception. {}. Request parameters: {}", ex.getMessage(), reqDto);
        } catch (Exception ex) {
            log.error("Unexpected mail sender exception. {}, Request parameters: {}", ex.getMessage(), reqDto);
        }
        throw new UnableToSendEmailException(reqDto.getSendTo());
    }
}
