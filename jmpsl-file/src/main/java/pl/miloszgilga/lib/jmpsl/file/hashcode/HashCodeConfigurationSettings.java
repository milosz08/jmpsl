/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: HashCodeConfigurationSettings.java
 * Last modified: 30/10/2022, 17:49
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

package pl.miloszgilga.lib.jmpsl.file.hashcode;

import org.springframework.core.env.Environment;
import org.springframework.context.annotation.Configuration;

import static pl.miloszgilga.lib.jmpsl.file.FileEnv.*;

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
        hashCodeSeparator = __JFM_HASH_SEPARATOR.getProperty(env, Character.class);
        hashSeqBlockCount = __JFM_HASH_SEQ_COUNT.getProperty(env, Byte.class);
        hashSeqBlockLenght = __JFM_HASH_SEQ_LENGTH.getProperty(env, Byte.class);
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
