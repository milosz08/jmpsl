/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: FileHashCodeGenerator.java
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

package org.jmpsl.file.hashcode;

import org.springframework.util.Assert;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.regex.Pattern;

/**
 * Class storing methods responsible for generate hash (random characters blocks separated with declared separator) and
 * checked hash pattern.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class FileHashCodeGenerator {

    private FileHashCodeGenerator() {
    }

    /**
     * Static method responsible for generate hash code base parameters.
     *
     * @param separator hash code blocks separator (ex. "-" or "_")
     * @param countOfBlocks count of generated hash code blocks (separated sections)
     * @param blockLength count of characters in single hash code block
     * @return generated hashcode (as string value)
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public static String generateHashCode(char separator, byte countOfBlocks, byte blockLength) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < countOfBlocks; i++) {
            builder.append(RandomStringUtils.randomAlphanumeric(blockLength));
            if (i == countOfBlocks - 1) break;
            builder.append(separator);
        }
        return builder.toString();
    }

    /**
     * Static method responsible for generate hash code base details in <code>application.property</code> file.
     *
     * @return generated hashcode (as string value)
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public static String generateHashCode() {
        return generateHashCode(HashCodeConfigurationSettings.getHashCodeSeparator(),
            HashCodeConfigurationSettings.getHashSeqBlockCount(),HashCodeConfigurationSettings.getHashSeqBlockLenght());
    }

    /**
     * Method responsible for checked hash code pattern (based passed parameters).
     *
     * @param hashCode checking hashcode
     * @param separator hash code blocks separator (ex. "-" or "_")
     * @param countOfBlocks count of generated hash code blocks (separated sections)
     * @param blockLength count of characters in single hash code block
     * @return true, if hash code is valid, otherwise false
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if hashCode parameter is null
     */
    public static boolean hashCodeIsValid(String hashCode, char separator, byte countOfBlocks, byte blockLength) {
        Assert.notNull(hashCode, "Hash code parameter cannot be null.");
        final StringBuilder regexPattern = new StringBuilder();
        for (int i = 0; i < countOfBlocks; i++) {
            regexPattern.append(String.format("[a-zA-Z0-9]{%s}", blockLength));
            if (i == countOfBlocks - 1) break;
            regexPattern.append(separator);
        }
        final Pattern generatedPattern = Pattern.compile(regexPattern.toString());
        return generatedPattern.matcher(hashCode).matches();
    }

    /**
     * Method responsible for checked hash code pattern (based details in <code>application.property</code> file).
     *
     * @param hashCode checking hashcode
     * @return true, if hash code is valid, otherwise false
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if hashCode parameter is null
     */
    public static boolean hashCodeIsValid(String hashCode) {
        return hashCodeIsValid(hashCode, HashCodeConfigurationSettings.getHashCodeSeparator(),
            HashCodeConfigurationSettings.getHashSeqBlockCount(), HashCodeConfigurationSettings.getHashSeqBlockLenght());
    }
}
