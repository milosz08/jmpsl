/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: StaticDataLoader.java
 * Last modified: 15/10/2022, 22:03
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

package pl.miloszgilga.lib.jmpsl.core.loader;

import org.slf4j.*;

import pl.miloszgilga.lib.jmpsl.core.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.regex.*;

/**
 * Custom class available loading static json file and parsing to simple POJO class based T type sended in class
 * definition. Loader only available parsing JSON files.
 *
 * @param <T> type of POJO json model
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class StaticJsonFileLoader<T extends IStaticJsonLoaderModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StaticJsonFileLoader.class);
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
        this.filePath = StringUtil.ifNullDefault(filePath, "/static/data/");
        loadDataFromFile();
    }

    private void loadDataFromFile() {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            try (final InputStream inputStream = StaticJsonFileLoader.class.getResourceAsStream(filePath + fileName)) {
                if (inputStream == null) throw new IOException();
                loadedData = objectMapper.readValue(new String(inputStream.readAllBytes()), mappingTypeClazz);
                LOGGER.info("Static data file: {} was loaded successfuly", fileName);
            }
        } catch (IOException ex) {
            LOGGER.error("File: {} not exist or is corrupted or is not JSON file", fileName);
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
