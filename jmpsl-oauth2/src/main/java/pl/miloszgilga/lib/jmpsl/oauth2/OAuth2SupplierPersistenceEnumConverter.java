/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: OAuth2SupplierPersistenceEnumConverter.java
 * Last modified: 19/10/2022, 17:27
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

package pl.miloszgilga.lib.jmpsl.oauth2;

import javax.persistence.*;
import pl.miloszgilga.lib.jmpsl.core.converter.BasicEnumConverter;

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
