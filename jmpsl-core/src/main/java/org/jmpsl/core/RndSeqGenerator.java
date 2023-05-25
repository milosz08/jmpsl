/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: RndSeqGenerator.java
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

package org.jmpsl.core;

import org.springframework.util.Assert;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

/**
 * Class storing static methods for generating random sequences. Methods of this class might be used for example in
 * generating user nicknames, email and other data, where data repeatability is strictly prohibited.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class RndSeqGenerator {

    private static final Random RANDOM = new Random();

    private RndSeqGenerator() {
    }

    /**
     * Static method available to generate random number sequence (lenght based <code>seqLenght</code> param) and
     * include after passed string prefix value.
     *
     * @param prefix string appended before the generated string of numbers
     * @param seqLenght length of the generated sequence of numbers
     * @return initial text from the parameter with a string of random numeric values added
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if passed prefix value is null or seqLenght parameter is less than 1
     */
    public static String addEndRndSeq(String prefix, int seqLenght) {
        Assert.notNull(prefix, "Passed prefix value cannot be null.");
        return prefix + RandomStringUtils.randomNumeric(seqLenght);
    }

    /**
     * Static method available to generate random number sequence (lenght based <code>seqLength</code> param) and
     * include before passed string prefix value.
     *
     * @param suffix string appended after the generated string of numbers
     * @param seqLenght length of the generated sequence of numbers
     * @return initial text from the parameter with a string of random numeric values added
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if passed suffix value is null or seqLenght parameter is less than 1
     */
    public static String addBeforeRndSeq(String suffix, int seqLenght) {
        Assert.notNull(suffix, "Passed prefix value cannot be null.");
        return RandomStringUtils.randomNumeric(seqLenght) + suffix;
    }

    /**
     * Static method available to generate random number sequence with default lenght (3) and include after
     * passed string prefix value.
     *
     * @param prefix string appended before the generated string of numbers
     * @return initial text from the parameter with a string of random numeric values added
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if passed suffix value is null
     */
    public static String addEndRndSeq(String prefix) {
        return addEndRndSeq(prefix, 3);
    }

    /**
     * Static method available to generate random number sequence with default lenght (3) and include before
     * passed string prefix value.
     *
     * @param prefix string appended after the generated string of numbers
     * @return initial text from the parameter with a string of random numeric values added
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if passed suffix value is null
     */
    public static String addBeforeRndSeq(String prefix) {
        return addBeforeRndSeq(prefix, 3);
    }
}
