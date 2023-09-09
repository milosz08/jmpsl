/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: StaticJsonFileLoader.java
 * Last modified: 28/03/2023, 23:14
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

package org.jmpsl.core.loader;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Custom class available loading static json file and parsing to simple POJO class based T type sended in class
 * definition. Loader only available parsing JSON files.
 *
 * @param <T> type of POJO json model
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Slf4j
public class StaticJsonFileLoader<T extends IStaticJsonLoaderModel> {

    private static final Pattern JSON_PATTERN = Pattern.compile(".*\\.json");

    private T loadedData;
    private final String filePath;
    private final String fileName;
    private final Class<T> mappingTypeClazz;

    /**
     * Create static data loader instance with default files path (classpath:resources/static/data/). Loader available
     * only load JSON files.
     *
     * @param fileName loaded file name from {@link IStaticJsonLoaderFiles} function interface
     * @param mappingTypeClazz mapped class type of data structure object in type T
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public StaticJsonFileLoader(IStaticJsonLoaderFiles fileName, Class<T> mappingTypeClazz) {
        checkIfFileIsJson(fileName.getFileName());
        this.fileName = fileName.getFileName();
        this.mappingTypeClazz = mappingTypeClazz;
        this.filePath = "/static/data/";
        loadDataFromFile();
    }

    /**
     * Create static data loader instance with custom files path (but must be declared in application resources
     * directory). Loader available only load JSON files.
     *
     * @param fileName loaded file name from {@link IStaticJsonLoaderFiles} function interface
     * @param mappingTypeClazz mapped class type of data structure object in type T
     * @param filePath custom path to file, ex. <code>/static/data</code>
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public StaticJsonFileLoader(IStaticJsonLoaderFiles fileName, Class<T> mappingTypeClazz, String filePath) {
        checkIfFileIsJson(fileName.getFileName());
        this.fileName = fileName.getFileName();
        this.mappingTypeClazz = mappingTypeClazz;
        this.filePath = StringUtils.defaultIfBlank(filePath, "/static/data/");
        loadDataFromFile();
    }

    private void loadDataFromFile() {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            try (final InputStream inputStream = StaticJsonFileLoader.class.getResourceAsStream(filePath + fileName)) {
                if (inputStream == null) throw new IOException();
                loadedData = objectMapper.readValue(new String(inputStream.readAllBytes()), mappingTypeClazz);
                log.info("Static data file: {} was loaded successfuly", fileName);
            }
        } catch (IOException ex) {
            log.error("File: {} not exist or is corrupted or is not JSON file", fileName);
        }
    }

    private void checkIfFileIsJson(String fileName) {
        final Matcher matcher = JSON_PATTERN.matcher(fileName);
        if (!matcher.matches()) throw new IllegalStateException("Passed file name not is JSON file. File name: " + fileName);
    }

    /**
     * @return loaded data object of type T
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public T getLoadedData() {
        return loadedData;
    }
}
