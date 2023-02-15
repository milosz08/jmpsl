/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: FreemarkerConfigurationInjector.java
 * Last modified: 18/11/2022, 04:39
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

package org.jmpsl.mail;

import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import static org.jmpsl.mail.MailEnv.__COM_FREEMARKER_PATH;

/**
 * Spring auto-configuration class for creating freemarker templates configuration bean. To determinate freemarker
 * templates def dir, insert <code>jmpsl.mail.freemarker-templates-dir</code> value in <code>application.properties</code>
 * file (ex. classpath:/freemarker/templates/). By default, dir is <code>classpath:/templates</code>.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Configuration
class FreemarkerConfigurationInjector {

    private final String freemarkerTemplatesDir;

    FreemarkerConfigurationInjector(Environment env) {
        this.freemarkerTemplatesDir = __COM_FREEMARKER_PATH.getProperty(env);
    }

    @Primary
    @Bean
    public FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean() {
        final FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath(freemarkerTemplatesDir);
        return bean;
    }
}
