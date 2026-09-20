/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: OAuth2SupplierPersistenceEnumConverter.java
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

import jakarta.persistence.Convert;
import jakarta.persistence.Converter;

import org.jmpsl.core.converter.BasicEnumConverter;

/**
 * Custom Javax persistence converted for {@link OAuth2Supplier} enum class. To use this converter in JPA
 * entity, refer via {@link Convert} annotation on selected entity field.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Converter(autoApply = true)
public class OAuth2SupplierPersistenceEnumConverter extends BasicEnumConverter<OAuth2Supplier> {

    protected OAuth2SupplierPersistenceEnumConverter() {
        super(OAuth2Supplier.class);
    }
}
