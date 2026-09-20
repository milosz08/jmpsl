/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: FileEnv.java
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

package org.jmpsl.file;

import lombok.Getter;
import lombok.AllArgsConstructor;

import org.springframework.core.env.Environment;

import org.jmpsl.core.env.EnvDataPayload;
import org.jmpsl.core.env.IEnvEnumExtender;
import org.jmpsl.core.env.EnvPropertyHandler;

/**
 * Enum responsible for storing all environment variables from <code>application.properties</code> file for JMPSL File
 * library module.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 * @see EnvPropertyHandler
 * @see EnvDataPayload
 */
@Getter
@AllArgsConstructor
public enum FileEnv implements IEnvEnumExtender {

    /**
     * Define, if SSH/SFTP service is active. By default "true". Property required.
     *
     * @since 1.0.2
     */
    __JFM_SSH_ACTIVE("jmpsl.file.ssh.active", "true", true),

    /**
     * Define SSH/SFTP service host address. By default "127.0.0.1" (localhost). Property required.
     *
     * @since 1.0.2
     */
    __JFM_SSH_HOST("jmpsl.file.ssh.socket-host", "127.0.0.1", true),

    /**
     * Define SSH/SFTP service login. Property required.
     *
     * @since 1.0.2
     */
    __JFM_SSH_LOGIN("jmpsl.file.ssh.socket-login", null, true),

    /**
     * Define file name for known hosts in SSH/SFTP service. By default "known_hosts.dat". Property required.
     * File must be located in ROOT project directory.
     *
     * @since 1.0.2
     */
    __JFM_SSH_KNOWN_HOSTS("jmpsl.file.ssh.known-hosts-file-name", "known_hosts.dat", true),

    /**
     * Define file name for private client RSA key in SSH/SFTP service. By default "id_rsa". Property required.
     * File must be located in ROOT project directory.
     *
     * @since 1.0.2
     */
    __JFM_SSH_PRIVATE_KEY("jmpsl.file.ssh.user-private-key-file-name", "id_rsa", true),

    /**
     * Define SFTP server address. By default "127.0.0.1" (localhost). Property required.
     *
     * @since 1.0.2
     */
    __JFM_SFTP_SERVER_URL("jmpsl.file.sftp.server-url", "127.0.0.1", true),

    /**
     * Define SSH/SFTP path from server root to domain directory. Property required. Property must be end with "/"
     * character.
     *
     * @since 1.0.2
     */
    __JFM_SSH_BASIC_EXT_SERVER_URL("jmpsl.file.basic-external-server-path", null, true),

    /**
     * Define SSH/SFTP directory name for application static resources. By default "". Property required. Property
     * cannot be end with "/" character.
     *
     * @since 1.0.2
     */
    __JFM_SSH_APP_EXT_SERVER_URL("jmpsl.file.app-external-server-path", "", false),

    /**
     * Define file name hash generator separator. By default "-". Property non-required.
     *
     * @since 1.0.2
     */
    __JFM_HASH_SEPARATOR("jmpsl.file.hash-code.separator", "-", false),

    /**
     * Single sequences count in all hash word. By default "4". Property required. Only unsigned values (1-255).
     *
     * @since 1.0.2
     */
    __JFM_HASH_SEQ_COUNT("jmpsl.file.hash-code.count-of-sequences", "4", true),

    /**
     * Define count of characters in single hash sequence. By default "5". Property required. Only unsigned values
     * (1-255).
     *
     * @since 1.0.2
     */
    __JFM_HASH_SEQ_LENGTH("jmpsl.file.hash-code.sequence-length", "5", true);

    private final String name;
    private final String defaultValue;
    private final boolean isRequired;

    @Override
    public String getProperty(Environment env) {
        return EnvPropertyHandler.getPostTypedProperty(new EnvDataPayload(env, name, defaultValue, isRequired), String.class);
    }

    @Override
    public <T> T getProperty(Environment env, Class<T> targetClazz) {
        return EnvPropertyHandler.getPostTypedProperty(new EnvDataPayload(env, name, defaultValue, isRequired), targetClazz);
    }
}
