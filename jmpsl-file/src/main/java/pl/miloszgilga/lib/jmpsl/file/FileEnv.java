/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: FileEnv.java
 * Last modified: 18.11.2022, 01:39
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

package pl.miloszgilga.lib.jmpsl.file;

import lombok.*;
import org.springframework.core.env.Environment;

import pl.miloszgilga.lib.jmpsl.core.env.*;
import static pl.miloszgilga.lib.jmpsl.core.env.EnvPropertyHandler.getPostTypedProperty;

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
        return getPostTypedProperty(new EnvDataPayload(env, name, defaultValue, isRequired), String.class);
    }

    @Override
    public <T> T getProperty(Environment env, Class<T> targetClazz) {
        return getPostTypedProperty(new EnvDataPayload(env, name, defaultValue, isRequired), targetClazz);
    }
}
