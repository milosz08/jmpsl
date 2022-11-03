/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: SshFileSocketConnector.java
 * Last modified: 30/10/2022, 17:52
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

package pl.miloszgilga.lib.jmpsl.file.socket;

import org.slf4j.*;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.StatefulSFTPClient;

import java.io.*;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.StringUtils.hasLength;
import static pl.miloszgilga.lib.jmpsl.file.FileUtil.createDirIfNotExist;

/**
 * Spring Bean component responsible for connecting and performing custom action on SFTP socket. Before run application,
 * declare following properties in <code>application.properties</code> file:
 *
 * <ul>
 *     <li><code>jmpsl.file.ssh.socket-host</code> - ssh socket host name, ex. 192.168.10.1</li>
 *     <li><code>jmpsl.file.ssh.socket-login</code> - ssh socket login or username</li>
 *     <li><code>jmpsl.file.ssh.known-hosts-file-name</code> - file name of known hosts (ROOT directory)</li>
 *     <li><code>jmpsl.file.ssh.user-private-key-file-name</code> - file name of user RSA private key (ROOT directory)</li>
 *     <li><code>jmpsl.file.sftp.server-url</code> - SFTP server path as URL (ROOT for static resources)</li>
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
    private final String sshHost;
    private final String sshLogin;
    private final String serverPath;
    private final String sftpServerUrl;
    private final File knownHostsFile;
    private final String sshUserPrivateKeyLocation;

    SshFileSocketConnector(Environment env) {
        sshHost = requireNonNull(env.getProperty("jmpsl.file.ssh.socket-host"));
        sshLogin = requireNonNull(env.getProperty("jmpsl.file.ssh.socket-login"));
        knownHostsFile = new File(requireNonNull(env.getProperty("jmpsl.file.ssh.known-hosts-file-name")));
        sshUserPrivateKeyLocation = requireNonNull(env.getProperty("jmpsl.file.ssh.user-private-key-file-name"));
        sftpServerUrl = requireNonNull(env.getProperty("jmpsl.file.sftp.server-url"));
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
        final String basicServerPath = requireNonNull(env.getProperty("jmpsl.file.basic-external-server-path"));
        appServerPath = env.getProperty("jmpsl.file.app-external-server-path", "");
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
