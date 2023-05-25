/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: MapperConfigurationLoader.java
 * Last modified: 28/03/2023, 23:27
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

package org.jmpsl.core.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auto-loading mapper configuration class and method creating bean of {@link ModelMapper} instance.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Configuration
class MapperConfigurationLoader {

    @Bean
    public ModelMapper mapperFactory() {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.STANDARD)
            .setFieldMatchingEnabled(true)
            .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
            .setAmbiguityIgnored(true);
        return modelMapper;
    }
}
