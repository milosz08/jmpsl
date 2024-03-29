/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: FileHashCodeGenerator.java
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
