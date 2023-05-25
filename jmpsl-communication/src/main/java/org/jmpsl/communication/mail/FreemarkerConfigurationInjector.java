/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: FreemarkerConfigurationInjector.java
 * Last modified: 25/05/2023, 15:37
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

import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import org.jmpsl.communication.CommunicationEnv;

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
        this.freemarkerTemplatesDir = CommunicationEnv.__COM_FREEMARKER_PATH.getProperty(env);
    }

    @Primary
    @Bean
    public FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean() {
        final FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath(freemarkerTemplatesDir);
        return bean;
    }
}
