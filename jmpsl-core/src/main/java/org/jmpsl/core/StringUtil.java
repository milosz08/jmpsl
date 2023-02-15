/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: StringUtil.java
 * Last modified: 13/02/2023, 02:29
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

package org.jmpsl.core;

import static java.util.Locale.ROOT;
import static java.util.Objects.isNull;
import static org.springframework.util.Assert.noNullElements;

/**
 * Class storing static methods for string operations. This class extends the standard
 * methods available in basic Java packages.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class StringUtil {

    private StringUtil() {
    }

    /**
     * Static method available to generated initials (first letters of multipe string arguments). The minimum number
     * of arguments is 2. Passed arguments cannot contain null values and empty characters.
     *
     * @param values multiple string values to extract first letter
     * @return first letter of passed string arguments (all letters are converted to uppercase)
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalStateException if passed arguments number are less than 2
     * @throws NullPointerException if at least one string value is null
     * @throws IllegalArgumentException if at least one string value contain one blank character
     */
    public static String initials(final String... values) {
        if (values.length < 2) throw new IllegalStateException("Method require at least 2 arguments");
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (values[i] == null) throw new NullPointerException("Value " + i + " must not be null");
            if (values[i].contains(" ")) {
                throw new IllegalArgumentException("Value " + i + " must not contain empty characters");
            }
            builder.append(values[i].charAt(0));
        }
        return builder.toString().toUpperCase(ROOT);
    }

    /**
     * Static method available to generate initials based name and surname parameters. Return initials as characters
     * array. Passed name and surname parameters cannot be null.
     *
     * @param name user name (extracted and capitalized first letter)
     * @param surname user surname (extracted and capitalized first letter)
     * @return characters array with generated user initials
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if name or surname is null
     */
    public static char[] initialsAsCharsArray(final String name, final String surname) {
        noNullElements(new Object[] { name, surname }, "Name or surname parameter cannot be null.");
        return initials(name, surname).toCharArray();
    }

    /**
     * Static method responsible for adding extra dot after string sequence, if not included. If string null, method
     * throwing exception.
     *
     * @param value string sequence
     * @return string sequence after added extra dot character
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws NullPointerException if passed string sequence is null
     */
    public static String addDot(final String value) {
        if (isNull(value)) return "";
        if (value.charAt(value.length() - 1) == '.') return value;
        return value + ".";
    }

    /**
     * Static method responsible for hashed string value (in parameter) based delimiter and hashValue (for example '*').
     * Based delimiter position, method hashed selected characters in count of such a parameter hashCount.
     *
     * @param value string value to hashed
     * @param hashValue hash character to replate original string characters (for example, '*')
     * @param delimiter hashing position careet (for example in email '@')
     * @param hashCount count of hashing values at left of delimiter
     * @return hashed string value
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws NullPointerException if passed string value to hash is null
     */
    public static String hashValue(final String value, final char hashValue, final char delimiter, final int hashCount) {
        if (isNull(value)) throw new NullPointerException("Passed string value to hash cannot be null.");
        final String hashedPart = value.substring(0, value.indexOf(delimiter));
        int hashingCharsCount = hashCount;
        if (hashedPart.length() < 5) hashingCharsCount = 1;
        final String hashPartVisible = value.substring(0, hashingCharsCount);
        final String hashPartNonVisible = value.substring(hashingCharsCount + 1, value.indexOf(delimiter));
        final String nonHashPart = value.substring(value.indexOf(delimiter) + 1);
        return hashPartVisible + Character.toString(hashValue).repeat(hashPartNonVisible.length())
                + delimiter + nonHashPart;
    }

    /**
     * Static method responsible for hashed user email value (based string email parameter). Method hashed all data,
     * without delimiter (<code>@</code>) and 3 first charaters with email domain name, for example for email
     * <code>example@gmail.com</code> hashed value is <code>exa****@gmail.com</code>
     *
     * @param value user email string value to hash (ex. <code>example@gmail.com</code>)
     * @return user email after hashed (ex. <code>exa****@gmail.com</code>)
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public static String hashValue(final String value) {
        return hashValue(value, '*', '@', 3);
    }
}
