/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: IEnumerableUserRole.java
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

package org.jmpsl.security.user;

/**
 * Implement this interface on enum collection of used roles in application and override {@link #getRole()} abstract
 * method.
 *
 * @author Miłosz Gilga
 * @since 1.0.2_02
 * @see IAuthUserModel
 */
public interface IEnumerableUserRole {
    String getRole();
}
