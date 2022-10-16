/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: TimeUtil.java
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

import org.slf4j.*;

import java.util.*;
import java.text.*;
import java.time.Year;

/**
 * Utility class added additional static methods for management time in servlet applications. Include adding minutes,
 * hours to current or passed {@link Date} object and convert string date notation to object. Class was prepared for
 * datetime format: <code>yyyy-MM-dd HH:mm:ss</code> and date format: <code>dd/MM/yyyy</code>.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class TimeUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeUtil.class);

    private static final SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");

    private TimeUtil() {
    }

    /**
     * Static method responsible for adding minutes to passed {@link Date} object in method parameters. If minutes is
     * less than 1, throw exception.
     *
     * @param minutes count of minutes to add
     * @param date modyfiying {@link Date} object
     * @return {@link Date} object after added extra minutes
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if passed minutes to add is less than 1.
     */
    public static Date addMinutes(final int minutes, final Date date) {
        if (minutes < 1) throw new IllegalArgumentException("Passed minutes value must be greaten than 0");
        return new Date(date.getTime() + ((long) minutes * 60 * 1000));
    }

    /**
     * Static method responsible for adding minutes to current date. If minutes is less than 1, throw exception.
     *
     * @param minutes count of minutes to add
     * @return {@link Date} object after added extra minutes
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if passed minutes to add is less than 1.
     */
    public static Date addMinutes(final int minutes) {
        return addMinutes(minutes, new Date());
    }

    /**
     * Static method responsible for adding months to passed {@link Date} object in method parameters. If months is
     * less than 1, throw exception.
     *
     * @param months count of months to add
     * @param date modyfiying {@link Date} object
     * @return {@link Date} object after added extra months
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if passed months to add is less than 1.
     */
    public static Date addMonths(final int months, final Date date) {
        if (months < 1) throw new IllegalArgumentException("Passed months value must be greaten than 0");
        return new Date(date.getTime() + ((long) months * 31 * 24 * 60 * 60 * 1000));
    }

    /**
     * Static method responsible for adding months to current date. If months is less than 1, throw exception.
     *
     * @param months count of months to add
     * @return {@link Date} object after added extra months
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if passed months to add is less than 1.
     */
    public static Date addMonths(final int months) {
        return addMonths(months, new Date());
    }

    /**
     * Static method responsible for removing the selected number of years from the current year based on the method
     * parameter yearsToRemove. If years to remove is less than 1, throw exception.
     *
     * @param yearsToRemove count of years to remove
     * @return modified year value
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if passed years to remove is less than 1.
     */
    public static int currYearMinusAcceptableAge(int yearsToRemove) {
        if (yearsToRemove < 1) throw new IllegalArgumentException("Passed year value must be greaten than 0");
        return Year.now().getValue() - yearsToRemove;
    }

    /**
     * Static method responsible for deserialize date from string value. Acceptable date format to serialized is
     * <code>dd/MM/yyyy</code>. In other formats, method return empty {@link Optional} object. Otherwise return
     * {@link Date} as object.
     *
     * @param date passed string date to deserialize (pattern: <code>dd/MM/yyyy</code>)
     * @return {@link Optional} with date, if deserialization ended successful, otherwise return empty {@link Optional}
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws NullPointerException if passed date string notation is null
     */
    public static Optional<Date> deserialize(final String date) {
        if (Objects.isNull(date)) throw new NullPointerException("Passed date string notation cannot be null.");
        try {
            return Optional.of(DATE_FORMATTER.parse(date));
        } catch (ParseException ex) {
            LOGGER.error("Unable to parse date string to object base date string parameter: {}" + date);
        }
        return Optional.empty();
    }

    /**
     * Static method responsible for serialized {@link Date} passed in method parameter.
     *
     * @param date passed {@link Date} object to serialize
     * @return serialized passed {@link Date} object
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws NullPointerException if passed {@link Date} object is null
     */
    public static String serializedUTC(final Date date) {
        if (Objects.isNull(date)) throw new NullPointerException("Passed date object cannot be null");
        return DATE_TIME_FORMATTER.format(date);
    }

    /**
     * Static method responsible for serialized current date (as {@link Date} object)
     *
     * @return serialized passed {@link Date} object
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public static String serializedUTC() {
        return serializedUTC(new Date());
    }

    /**
     * Static method responsible for checking if passed {@link Date} object is expired. If expired return true,
     * otherwise return false.
     *
     * @param date checking {@link Date} object, if passed date is expired
     * @return true, if passed {@link Date} object is expired, otherwise return false
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws NullPointerException if passed {@link Date} object is null
     */
    public static boolean isExpired(Date date) {
        if (Objects.isNull(date)) throw new NullPointerException("Passed date object cannot be null");
        return date.after(new Date());
    }

    /**
     * Static method responsible for checking if passed {@link Date} object is non expired. If expired return false,
     * otherwise return true.
     *
     * @param date checking {@link Date} object, if passed date is non expired
     * @return true if passed {@link Date} object is non expired, otherwise return false
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws NullPointerException if passed {@link Date} object is null
     */
    public static boolean isNonExpired(Date date) {
        if (Objects.isNull(date)) throw new NullPointerException("Passed date object cannot be null");
        return date.before(new Date());
    }
}
