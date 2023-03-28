/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: OAuth2Supplier.java
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

package org.jmpsl.oauth2;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.stream.Stream;

import org.jmpsl.core.converter.IBasicEnumConverter;

import static org.jmpsl.oauth2.OAuth2Exception.OAuth2SupplierNotImplementedException;

/**
 * Enum class storing all credentials suppliers via OAuth2 service.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Slf4j
@Getter
@AllArgsConstructor
public enum OAuth2Supplier implements IBasicEnumConverter {

    /**
     * Credentials supplier enum value for google OAuth2 authorization request.
     *
     * @since 1.0.2
     */
    GOOGLE("google"),

    /**
     * Credentials supplier enum value for facebook OAuth2 authorization request.
     *
     * @since 1.0.2
     */
    FACEBOOK("facebook"),

    /**
     * Credentials supplier enum value for github OAuth2 authorization request.
     *
     * @since 1.0.2
     */
    GITHUB("github"),

    /**
     * Credentials supplier enum value for linkedin OAuth2 authorization request.
     *
     * @since 1.0.2
     */
    LINKEDIN("linkedin"),

    /**
     * Credentials supplier enum value for local authorization request.
     *
     * @since 1.0.2
     */
    LOCAL("local");

    private final String supplierName;

    /**
     * Method responsible for checking, if supplierName string argument is one of the OAuth2 supplier enum value from
     * {@link OAuth2Supplier} enum class. If is not, throw an exception, otherwise return found enum value.
     *
     * @param supplierName enum representation as string value
     * @return if find, {@link OAuth2Supplier} enum object, otherwise throw an exception
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws OAuth2SupplierNotImplementedException if supplier based passed string name not exist
     */
    public static OAuth2Supplier checkIfSupplierIsValid(String supplierName) {
        return Stream.of(OAuth2Supplier.values())
            .filter(s -> s.supplierName.equals(supplierName))
            .findFirst()
            .orElseThrow(() -> {
                log.error("Passed supplier: {} is not valid credentials supplier name.", supplierName);
                throw new OAuth2SupplierNotImplementedException();
            });
    }

    /**
     * Method responsible for checking if supplierName string argument is one of the suppliers {@link Set} collection
     * element. If is not, throw an exception, otherwise return found {@link OAuth2Supplier} enum object.
     *
     * @param supplierName enum representation as string value
     * @param suppliers {@link Set} collection of all available suppliers
     * @return if find, {@link OAuth2Supplier} enum object, otherwise throw an exception
     *
     * @throws OAuth2SupplierNotImplementedException if supplier is not provided in this application
     */
    public static OAuth2Supplier checkIfSupplierExist(String supplierName, Set<OAuth2Supplier> suppliers) {
        return Stream.of(OAuth2Supplier.values())
            .filter(s -> s.supplierName.equals(supplierName) && suppliers.contains(s))
            .findFirst()
            .orElseThrow(() -> {
                log.error("Passed supplier: {} is not implemented in this application.", supplierName);
                throw new OAuth2SupplierNotImplementedException();
            });
    }

    @Override
    public String getEnumName() {
        return supplierName;
    }
}
