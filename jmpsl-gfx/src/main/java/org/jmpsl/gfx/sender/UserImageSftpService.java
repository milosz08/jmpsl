/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: UserImageSftpService.java
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
 * The software is provided “as is”, without warranty of any kind, express or implied, including but not limited
 * to the warranties of merchantability, fitness for a particular purpose and noninfringement. In no event
 * shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an
 * action of contract, tort or otherwise, arising from, out of or in connection with the software or the use
 * or other dealings in the software.
 */

package org.jmpsl.gfx.sender;

import lombok.extern.slf4j.Slf4j;

import org.imgscalr.Scalr;
import org.jmpsl.file.FileUtil;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;

import net.schmizz.sshj.xfer.FileSystemFile;
import net.schmizz.sshj.sftp.RemoteResourceInfo;
import net.schmizz.sshj.sftp.StatefulSFTPClient;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.nio.file.Files;
import java.io.File;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.jmpsl.gfx.GfxEnv;
import org.jmpsl.gfx.GfxUtil;
import org.jmpsl.gfx.ImageExtension;
import org.jmpsl.gfx.generator.GeneratedImageRes;
import org.jmpsl.gfx.generator.UserImageGenerator;
import org.jmpsl.gfx.generator.BufferedImageGeneratorRes;
import org.jmpsl.gfx.generator.BufferedImageGeneratorPayload;
import org.jmpsl.file.socket.SshFileSocketConnector;
import org.jmpsl.file.hashcode.FileHashCodeGenerator;
import org.jmpsl.file.hashcode.HashCodeFormatException;
import org.jmpsl.file.exception.ExternalFileServerMalfunctionException;

/**
 * Spring Bean service responsible for generate sending and deleting user image to external SFTP server directory. Contains
 * methods for generating default user image and save in external storage, and also saving already passed image (as byte
 * array stream) into external storage. Before using this class, create following properties:
 *
 * <ul>
 *     <li><code>jmpsl.gfx.user-gfx.static-images-content-path</code> - path to SFTP images content, without any slashes</li>
 * </ul>
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Slf4j
@Service
public class UserImageSftpService implements IUserImageService {

    private String imagesRelativePath;
    private final String imagesServerPath;
    private final UserImageGenerator imageGenerator;
    private final SshFileSocketConnector socketConnector;

    UserImageSftpService(Environment env, UserImageGenerator imageGenerator, SshFileSocketConnector socketConnector) {
        this.imageGenerator = imageGenerator;
        this.socketConnector = socketConnector;
        imagesServerPath = createImagesServerPath(env);
    }

    /**
     * Override method responsible for generate basic user image and save on external SFTP server.
     *
     * @param payload instance of {@link BufferedImageGeneratorPayload} class with sending image details
     * @param extension image extension as {@link ImageExtension} type (ex. png, jpeg etc.)
     * @return instance of {@link BufferedImageGeneratorRes} with generated and saved image data
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws ExternalFileServerMalfunctionException if unable to save image on SFTP external server.
     */
    @Override
    public BufferedImageGeneratorRes generateAndSaveDefaultUserImage(
        BufferedImageGeneratorPayload payload, ImageExtension extension
    ) {
        final BufferedImageRes imageResponse = new BufferedImageRes();
        final GeneratedImageRes generatedImage = imageGenerator.generateDefaultUserImage(payload, extension);
        socketConnector.connectToSocketAndPerformAction(sftpClient -> {
            final TempImageSavePayload tempImageSavePayload = new TempImageSavePayload(generatedImage.imageBytes(),
                payload, extension);
            imageResponse.copyObject(generateTempImageAndSave(sftpClient, tempImageSavePayload));
        });
        log.info("Successful created default user avatar image. User hashcode: {}", imageResponse.getUserHashCode());
        return new BufferedImageGeneratorRes(imageResponse, generatedImage.imageBackground());
    }

    /**
     * Override method responsible for generate basic user image and save on external SFTP server. Method already set
     * image extension to PNG format.
     *
     * @param payload instance of {@link BufferedImageGeneratorPayload} class with sending image details
     * @return instance of {@link BufferedImageGeneratorRes} with generated and saved image data
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws ExternalFileServerMalfunctionException if unable to save image on SFTP external server.
     */
    public BufferedImageGeneratorRes generateAndSaveDefaultUserImage(BufferedImageGeneratorPayload payload) {
        return generateAndSaveDefaultUserImage(payload, ImageExtension.PNG);
    }

    /**
     * Override method responsible for saving already generated or sended user image as byte array stream into SFTP
     * external server. Method also resize image to preferred width and height base {@link BufferedImageSenderPayload}
     * instance values.
     *
     * @param payload instance of {@link BufferedImageSenderPayload} class with sending image details
     * @param extension image extension as {@link ImageExtension} type (ex. png, jpeg etc.)
     * @return instance of {@link BufferedImageRes} with saved image data
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws ExternalFileServerMalfunctionException if unable to save image on SFTP external server.
     */
    @Override
    public BufferedImageRes saveUserImage(BufferedImageSenderPayload payload, ImageExtension extension) {
        final BufferedImageRes imageResponse = new BufferedImageRes();
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(payload.bytesRepresentation());
        socketConnector.connectToSocketAndPerformAction(sftpClient -> {
            try {
                final BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
                final BufferedImage resizeResult = Scalr.resize(bufferedImage, Scalr.Method.QUALITY, Scalr.Mode.FIT_EXACT,
                    payload.preferredWidth(), payload.preferredHeight(), Scalr.OP_ANTIALIAS);

                final TempImageSavePayload tempImageSavePayload = new TempImageSavePayload(
                    GfxUtil.generateByteStreamFromBufferedImage(resizeResult, extension), payload, extension);
                imageResponse.copyObject(generateTempImageAndSave(sftpClient, tempImageSavePayload));

            } catch (IOException ex) {
                log.error("Unable to send image to external server. Server path: {}", imagesServerPath);
                throw new ExternalFileServerMalfunctionException();
            }
        });
        log.info("Successful send user avatar image. User id: {}", imageResponse.getUserHashCode());
        return imageResponse;
    }

    /**
     * Override method responsible for deleting already sended user image from external SSH/SFTP static resources server.
     * All parameters should be filled in passed {@link BufferedImageDeletePayload} class instance.
     *
     * @param payload instance of {@link BufferedImageDeletePayload} class with deleting image details
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws ExternalFileServerMalfunctionException if unable to delete image from SFTP external server.
     * @throws HashCodeFormatException if passed user hash code is not valid with saved template
     */
    @Override
    public void deleteUserImage(BufferedImageDeletePayload payload) {
        Assert.notNull(payload, "Payload object cannot be null.");
        Assert.noNullElements(new Object[] { payload.userHashCode(), payload.uniqueImagePrefix(), payload.id() },
                "Payload data (userHashCode, uniqueImagePrefix, id) cannot be null.");
        socketConnector.connectToSocketAndPerformAction(sftpClient -> {
            try {
                ifResourceIsPresetRemove(sftpClient, payload.id(), payload.userHashCode(),
                    payload.uniqueImagePrefix());
            } catch (IOException ex) {
                log.error("Unable to remove image from external server. Image payload data: {}", payload);
                throw new ExternalFileServerMalfunctionException();
            }
        });
        log.info("Successful remove selected user image from external SFTP server. Image payload data: {}", payload);
    }

    /**
     * Override method responsible for saving already generated or sended user image as byte array stream into SFTP
     * external server. Method also resize image to preferred width and height base {@link BufferedImageSenderPayload}
     * instance values. Image extension is set to PNG format.
     *
     * @param payload instance of {@link BufferedImageSenderPayload} class with sending image details
     * @return instance of {@link BufferedImageRes} with saved image data
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws ExternalFileServerMalfunctionException if unable to save image on SFTP external server.
     */
    public BufferedImageRes saveUserImage(BufferedImageSenderPayload payload) {
        return saveUserImage(payload, ImageExtension.PNG);
    }

    /**
     * Inner method responsible for generating temporary image and save in SFTP external server. If file already exist,
     * remove and place in same location. Throw {@link ExternalFileServerMalfunctionException} if unable to save or
     * remove already existing image from SFTP external server.
     *
     * @param sftpClient instance of {@link StatefulSFTPClient} socket for perform external SFTP server actions
     * @param payload instance of {@link TempImageSavePayload} POJO class with temporary file details
     * @return instance of {@link BufferedImageRes} class storing generated file informations
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws ExternalFileServerMalfunctionException if unable to save image on SFTP external server.
     */
    private BufferedImageRes generateTempImageAndSave(StatefulSFTPClient sftpClient, TempImageSavePayload payload) {
        final BufferedImageRes imageResponse = new BufferedImageRes();
        try {
            String userStaticImageDir, hashCode, relativeImagesPath;
            final String userImagePathPrefix = "user" + payload.id() + "_";

            sftpClient.cd(imagesServerPath);
            ifResourceIsPresetRemove(sftpClient, payload.id(), payload.userHashCode(), payload.uniqueImagePrefix());

            if (Objects.isNull(payload.userHashCode())) {
                hashCode = FileHashCodeGenerator.generateHashCode();
                userStaticImageDir = userImagePathPrefix + hashCode;
                FileUtil.createDirIfNotExist(sftpClient, imagesServerPath, userStaticImageDir);
            } else {
                hashCode = payload.userHashCode();
                userStaticImageDir = userImagePathPrefix + payload.userHashCode();
            }
            final File tempImage = File.createTempFile(payload.uniqueImagePrefix() + "_", "." + payload.extensionName());
            if (StringUtils.hasLength(imagesRelativePath)) {
                relativeImagesPath = socketConnector.getAppServerPath() + "/" + imagesRelativePath + "/";
            } else {
                relativeImagesPath = socketConnector.getAppServerPath() + "/";
            }
            imageResponse.setLocation(relativeImagesPath + userStaticImageDir + "/" + tempImage.getName());

            Files.write(tempImage.toPath(), payload.bytesRepresentation());
            sftpClient.put(new FileSystemFile(tempImage), imagesServerPath + "/" + userStaticImageDir);

            imageResponse.setBytesRepresentation(payload.bytesRepresentation());
            imageResponse.setUserHashCode(hashCode);
            tempImage.deleteOnExit();
        } catch (IOException ex) {
            log.error("Unable to send image to external server. Server path: {}", imagesServerPath);
            throw new ExternalFileServerMalfunctionException();
        }
        return imageResponse;
    }

    /**
     * Inner method responsible for removing image resource from external SSH/SFTP static resources server basic user
     * hash code, user id and resource unique prefix.
     * Otherwise removing resource image and return
     *
     * @param sftpClient instance of {@link StatefulSFTPClient} socket for perform external SFTP server actions
     * @param id user id (from database)
     * @param userHashCode random characters identifier code (generated by {@link FileHashCodeGenerator}, from database)
     * @param prefix user image prefix (avatar, banner etc.)<
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IOException if unable to remove directory from SFTP external server
     * @throws HashCodeFormatException if passed user hash code is not valid with saved template
     */
    private void ifResourceIsPresetRemove(StatefulSFTPClient sftpClient, Long id, String userHashCode, String prefix)
            throws IOException {
        if (Objects.isNull(userHashCode)) return;
        if (!FileHashCodeGenerator.hashCodeIsValid(userHashCode)) throw new HashCodeFormatException();
        final String baseDir = "user" + id + "_" + userHashCode;
        final List<RemoteResourceInfo> rsImages = sftpClient.ls(imagesServerPath + "/" + baseDir);
        final Optional<String> foundImage = rsImages.stream().map(RemoteResourceInfo::getName)
                .filter(name -> name.startsWith(prefix)).findFirst();
        if (foundImage.isPresent()) {
            sftpClient.cd(imagesServerPath + "/" + baseDir);
            sftpClient.rm(foundImage.get());
        }
    }

    /**
     * Inner method responsible for creating images server path (based default application server path). If property
     * <code>jmpsl.gfx.user-gfx.static-images-content-path</code> not present, image path is ROOT directory.
     *
     * @param env instance of {@link Environment} class passed from constructor
     * @return images full server path or null, if module is disabled
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    private String createImagesServerPath(Environment env) {
        imagesRelativePath = GfxEnv.__GFX_SFTP_PATH.getProperty(env);
        if (!StringUtils.hasLength(imagesRelativePath)) {
            return socketConnector.getServerPath();
        }
        socketConnector.connectToSocketAndPerformAction(sftpClient -> {
            try {
                FileUtil.createDirIfNotExist(sftpClient, socketConnector.getServerPath(), imagesRelativePath);
            } catch (IOException ex) {
                log.error("Unable to create static images server path. Images path: {}", imagesRelativePath);
            }
        });
        return socketConnector.getServerPath() + "/" + imagesRelativePath;
    }
}
