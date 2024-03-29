/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: HashCodeConfigurationSettings.java
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

import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Configuration;

import org.jmpsl.file.FileEnv;

/**
 * Configuration class responsible for storing hash code properties. One of this properties are:
 *
 * <ul>
 *     <li><code>jmpsl.file.hash-code.separator</code> - by default "-"</li>
 *     <li><code>jmpsl.file.hash-code.count-of-sequences</code> - by default 4 sequences</li>
 *     <li><code>jmpsl.file.hash-code.sequence-length</code> - by default 5 characters</li>
 * </ul>
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Configuration
public class HashCodeConfigurationSettings {

    private static char hashCodeSeparator;
    private static byte hashSeqBlockCount;
    private static byte hashSeqBlockLenght;

    HashCodeConfigurationSettings(Environment env) {
        hashCodeSeparator = FileEnv.__JFM_HASH_SEPARATOR.getProperty(env, Character.class);
        hashSeqBlockCount = FileEnv.__JFM_HASH_SEQ_COUNT.getProperty(env, Byte.class);
        hashSeqBlockLenght = FileEnv.__JFM_HASH_SEQ_LENGTH.getProperty(env, Byte.class);
        if (hashSeqBlockCount < 1 || hashSeqBlockLenght < 1) {
            throw new IllegalArgumentException("Hash sequence length properties cannot be less than 1");
        }
    }

    /**
     * @return hash code separator sign
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    static char getHashCodeSeparator() {
        return hashCodeSeparator;
    }

    /**
     * @return single hash code sequence count of characters
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    static byte getHashSeqBlockLenght() {
        return hashSeqBlockLenght;
    }

    /**
     * @return count of sequences in hash code
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    static byte getHashSeqBlockCount() {
        return hashSeqBlockCount;
    }
}
