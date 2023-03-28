/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: SshFileSocketConnector.java
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

package org.jmpsl.file.socket;

import lombok.extern.slf4j.Slf4j;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.StatefulSFTPClient;

import org.springframework.util.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

import org.jmpsl.file.FileEnv;
import org.jmpsl.file.FileUtil;

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
@Slf4j
@Component
public class SshFileSocketConnector {

    private String appServerPath;
    private String sshHost;
    private String sshLogin;
    private String serverPath;
    private String sftpServerUrl;
    private File knownHostsFile;
    private String sshUserPrivateKeyLocation;

    SshFileSocketConnector(Environment env) {
        if (!FileEnv.__JFM_SSH_ACTIVE.getProperty(env, Boolean.class)) {
            log.info("SSH service is not active. To activate service, set 'jmpsl.file.ssh.active' to true");
            return;
        }
        sshHost = FileEnv.__JFM_SSH_HOST.getProperty(env);
        sshLogin =  FileEnv.__JFM_SSH_LOGIN.getProperty(env);
        knownHostsFile = new File(FileEnv.__JFM_SSH_KNOWN_HOSTS.getProperty(env));
        sshUserPrivateKeyLocation = FileEnv.__JFM_SSH_PRIVATE_KEY.getProperty(env);
        sftpServerUrl = FileEnv.__JFM_SFTP_SERVER_URL.getProperty(env);
        serverPath = createBasicSfptServerPath(env);
        log.info("Successful loaded SSH socket configuration from configuration properties file.");
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
                log.error("Unable to invoke stateful SFTP client execution from SSHClient socket.");
                throw new UnableToPerformSftpActionException();
            }
        } catch (IOException ex) {
            log.error("Unable to connect with SSH socket. Check connecting parameters.");
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
        final String basicServerPath = FileEnv.__JFM_SSH_BASIC_EXT_SERVER_URL.getProperty(env);
        appServerPath = FileEnv.__JFM_SSH_APP_EXT_SERVER_URL.getProperty(env);
        if (!StringUtils.hasLength(appServerPath)) {
            return basicServerPath;
        }
        connectToSocketAndPerformAction(sftpClient -> {
            try {
                FileUtil.createDirIfNotExist(sftpClient, basicServerPath, appServerPath);
            } catch (IOException ex) {
                log.error("Unable to create basic server path. Server path: {}, app server path: {}",
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
        return StringUtils.hasLength(appServerPath) ? sftpServerUrl + "/" + appServerPath : sftpServerUrl;
    }
}
