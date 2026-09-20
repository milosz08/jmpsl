/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: HashCodeConfigurationSettings.java
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
