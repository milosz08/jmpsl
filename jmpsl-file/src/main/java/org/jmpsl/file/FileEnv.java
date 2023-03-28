/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: FileEnv.java
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
