/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: SshFileSocketConnector.java
 * Last modified: 18/11/2022, 04:35
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

package org.jmpsl.file.socket;

import org.slf4j.*;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.StatefulSFTPClient;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.*;

import static org.jmpsl.file.FileEnv.*;
import static org.jmpsl.file.FileUtil.createDirIfNotExist;
import static org.springframework.util.StringUtils.hasLength;

/**
 * Spring Bean component responsible for connecting and performing custom action on SFTP socket. Before run application,
 * declare following properties in <code>application.properties</code> file:
 *
 * <ul>
 *     <li><code>jmpsl.file.ssh.active</code> - ssh socket host name, by default true</li>
 *     <li><code>jmpsl.file.ssh.socket-host</code> - ssh socket host name, by default 127.0.0.1</li>
 *     <li><code>jmpsl.file.ssh.socket-login</code> - ssh socket login or username</li>
 *     <li><code>jmpsl.file.ssh.known-hosts-file-name</code> - file name of known hosts, by default 'known_hosts.dat'</li>
 *     <li><code>jmpsl.file.ssh.user-private-key-file-name</code> - file name of user RSA private key, by default 'id_rsa'</li>
 *     <li><code>jmpsl.file.sftp.server-url</code> - SFTP server path as URL (ROOT for static resources), by default 127.0.0.1</li>
 *     <li><code>jmpsl.file.basic-external-server-path</code> - basic server path from root to domain directory (by default is "")</li>
 *     <li><code>jmpsl.file.app-external-server-path</code> - application name (directory for all application resources)</li>
 * </ul>
 *
 * This class is using RSA public and private keys to connect and authenticate to the SFTP server. Check, if you SFTP
 * server support this type of credentials verifications.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Component
public class SshFileSocketConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(SshFileSocketConnector.class);

    private String appServerPath;
    private String sshHost;
    private String sshLogin;
    private String serverPath;
    private String sftpServerUrl;
    private File knownHostsFile;
    private String sshUserPrivateKeyLocation;

    SshFileSocketConnector(Environment env) {
        if (!__JFM_SSH_ACTIVE.getProperty(env, Boolean.class)) {
            LOGGER.info("SSH service is not active. To activate service, set 'jmpsl.file.ssh.active' to true");
            return;
        }
        sshHost = __JFM_SSH_HOST.getProperty(env);
        sshLogin =  __JFM_SSH_LOGIN.getProperty(env);
        knownHostsFile = new File(__JFM_SSH_KNOWN_HOSTS.getProperty(env));
        sshUserPrivateKeyLocation = __JFM_SSH_PRIVATE_KEY.getProperty(env);
        sftpServerUrl = __JFM_SFTP_SERVER_URL.getProperty(env);
        serverPath = createBasicSfptServerPath(env);
        LOGGER.info("Successful loaded SSH socket configuration from configuration properties file.");
    }

    /**
     * Method responsible for connecting and perform custom action declared in lambda function anonymous class.
     *
     * @param executor anonymous class from {@link ISshFileSocketExecutor} interface
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws UnableToPerformSftpActionException if unable to connect with SFTP server or unable to perform other action
     */
    public void connectToSocketAndPerformAction(ISshFileSocketExecutor executor) {
        try (final SSHClient sshClient = new SSHClient()) {
            sshClient.loadKnownHosts(knownHostsFile);
            sshClient.connect(sshHost);
            sshClient.authPublickey(sshLogin, sshUserPrivateKeyLocation);

            try (final StatefulSFTPClient sftpClient = (StatefulSFTPClient) sshClient.newStatefulSFTPClient()) {
                executor.execute(sftpClient);
            } catch (IOException ex) {
                LOGGER.error("ERROR! Unable to invoke stateful SFTP client execution from SSHClient socket.");
                throw new UnableToPerformSftpActionException();
            }
        } catch (IOException ex) {
            LOGGER.error("ERROR! Unable to connect with SSH socket. Check connecting parameters.");
            throw new UnableToPerformSftpActionException();
        }
    }

    /**
     * Inner method responsible for created app SFTP directory (based <code>jmpsl.file.basic-external-server-path</code>
     * and <code>jmpsl.file.app-external-server-path</code> where first is basic server path and second is creating
     * custom application directory (optionally, by default is ROOT). If directory already exist, skipped creating.
     *
     * @param env {@link Environment} instance passed from injected bean in constructor
     * @return server path (combined basic server path and application generated directory)
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws NullPointerException if basicServerPath or appServerPath properties are null
     */
    private String createBasicSfptServerPath(Environment env) {
        final String basicServerPath = __JFM_SSH_BASIC_EXT_SERVER_URL.getProperty(env);
        appServerPath = __JFM_SSH_APP_EXT_SERVER_URL.getProperty(env);
        if (!hasLength(appServerPath)) {
            return basicServerPath;
        }
        connectToSocketAndPerformAction(sftpClient -> {
            try {
                createDirIfNotExist(sftpClient, basicServerPath, appServerPath);
            } catch (IOException ex) {
                LOGGER.error("Unable to create basic server path. Server path: {}, app server path: {}",
                        basicServerPath, appServerPath);
            }
        });
        return basicServerPath + appServerPath;
    }

    /**
     * @return declared server path (basic server path with generated application directory)
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public String getServerPath() {
        return serverPath;
    }

    /**
     * @return declared app server path (relative path to main domain, without protected directories)
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public String getAppServerPath() {
        return hasLength(appServerPath) ? sftpServerUrl + "/" + appServerPath : sftpServerUrl;
    }
}
