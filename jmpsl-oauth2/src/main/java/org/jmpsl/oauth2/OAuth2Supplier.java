/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: OAuth2Supplier.java
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
