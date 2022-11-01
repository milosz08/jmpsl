/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: RndSeqGenerator.java
 * Last modified: 15/10/2022, 12:56
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

package pl.miloszgilga.lib.jmpsl.util;

import java.util.Random;
import java.util.stream.*;

import static org.springframework.util.Assert.notNull;

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
        notNull(prefix, "Passed prefix value cannot be null.");
        return prefix + generator(seqLenght);
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
        notNull(suffix, "Passed prefix value cannot be null.");
        return generator(seqLenght) + suffix;
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

    /**
     * Inside private method responsible for generating random number sequence based length parameter. Throw exception,
     * if lenght is less than 1.
     *
     * @param seqLenght random number sequence lenght
     * @return generated random number sequence (after parse to string)
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if passed seqLenght parameter is less than 1
     */
    private static String generator(int seqLenght) {
        if (seqLenght < 1) {
            throw new IllegalArgumentException("Lenght of random numbers generator must be greater than 0");
        }
        return IntStream.generate(() -> RANDOM.nextInt(10)).limit(seqLenght)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());
    }
}
