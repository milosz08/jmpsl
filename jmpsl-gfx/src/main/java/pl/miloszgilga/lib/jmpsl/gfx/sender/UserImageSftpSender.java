/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: UserImageSender.java
 * Last modified: 31/10/2022, 19:11
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

package pl.miloszgilga.lib.jmpsl.gfx.sender;

import org.slf4j.*;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;

import net.schmizz.sshj.sftp.*;
import net.schmizz.sshj.xfer.FileSystemFile;

import java.io.*;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import pl.miloszgilga.lib.jmpsl.gfx.*;
import pl.miloszgilga.lib.jmpsl.file.*;
import pl.miloszgilga.lib.jmpsl.gfx.generator.*;
import pl.miloszgilga.lib.jmpsl.file.socket.SshFileSocketConnector;
import pl.miloszgilga.lib.jmpsl.file.hashcode.HashCodeFormatException;

import static java.util.Objects.*;
import static java.io.File.createTempFile;

import static pl.miloszgilga.lib.jmpsl.gfx.ImageExtension.PNG;
import static pl.miloszgilga.lib.jmpsl.file.FileUtil.createDirIfNotExist;
import static pl.miloszgilga.lib.jmpsl.file.hashcode.FileHashCodeGenerator.*;
import static pl.miloszgilga.lib.jmpsl.gfx.GfxUtil.generateByteStreamFromBufferedImage;

/**
 * Spring Bean service responsible for generate and sending user image to external SFTP server directory. Contains
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
@Service
public class UserImageSftpSender implements IUserImageSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserImageSftpSender.class);

    private final String imagesServerPath;
    private final UserImageGenerator imageGenerator;
    private final SshFileSocketConnector socketConnector;

    UserImageSftpSender(Environment env, UserImageGenerator imageGenerator, SshFileSocketConnector socketConnector) {
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
     * @throws ExternalFileServerMalfunctionException if unable to save image in SFTP external server.
     */
    @Override
    public BufferedImageGeneratorRes generateAndSaveDefaultUserImage(BufferedImageGeneratorPayload payload, ImageExtension extension) {
        final BufferedImageRes imageResponse = new BufferedImageRes();
        final GeneratedImageRes generatedImage = imageGenerator.generateDefaultUserImage(payload, extension);
        socketConnector.connectToSocketAndPerformAction(sftpClient -> {
            final TempImageSavePayload tempImageSavePayload = new TempImageSavePayload(payload, extension);
            tempImageSavePayload.setBytesRepresentation(generatedImage.getImageBytes());
            imageResponse.copyObject(generateTempImageAndSave(sftpClient, tempImageSavePayload));
        });
        LOGGER.info("Successful created default user avatar image. User hashcode: {}", imageResponse.getUserHashCode());
        return new BufferedImageGeneratorRes(imageResponse, generatedImage.getImageBackground());
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
     * @throws ExternalFileServerMalfunctionException if unable to save image in SFTP external server.
     */
    public BufferedImageGeneratorRes generateAndSaveDefaultUserImage(BufferedImageGeneratorPayload payload) {
        return generateAndSaveDefaultUserImage(payload, PNG);
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
     * @throws ExternalFileServerMalfunctionException if unable to save image in SFTP external server.
     */
    @Override
    public BufferedImageRes saveUserImage(BufferedImageSenderPayload payload, ImageExtension extension) {
        final BufferedImageRes imageResponse = new BufferedImageRes();
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(payload.getBytesRepresentation());
        socketConnector.connectToSocketAndPerformAction(sftpClient -> {
            try {
                final BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
                final BufferedImage resizeResult = Scalr.resize(bufferedImage, Scalr.Method.QUALITY, Scalr.Mode.FIT_EXACT,
                        payload.getPreferredWidth(), payload.getPreferredHeight(), Scalr.OP_ANTIALIAS);

                final TempImageSavePayload tempImageSavePayload = new TempImageSavePayload(payload, extension);
                tempImageSavePayload.setBytesRepresentation(generateByteStreamFromBufferedImage(resizeResult, extension));
                imageResponse.copyObject(generateTempImageAndSave(sftpClient, tempImageSavePayload));

            } catch (IOException ex) {
                LOGGER.error("Unable to send image to external server. Server path: {}", imagesServerPath);
                throw new ExternalFileServerMalfunctionException();
            }
        });
        LOGGER.info("Successful send user avatar image. User id: {}", imageResponse.getUserHashCode());
        return imageResponse;
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
     * @throws ExternalFileServerMalfunctionException if unable to save image in SFTP external server.
     */
    public BufferedImageRes saveUserImage(BufferedImageSenderPayload payload) {
        return saveUserImage(payload, PNG);
    }

    /**
     * Inner method responsible for checking if image directory already exist. If it exists, it does not delete the
     * directory, otherwise , it creates a new directory and returns information about the path to the created directory.
     *
     * @param sftpClient instance of {@link StatefulSFTPClient} socket for perform external SFTP server actions
     * @param payload instance of {@link TempImageSavePayload} POJO class with temporary file details
     * @return instance of {@link ImageExistPayload} with directory path and boolean flag indicated whether a directory
     *         has been created
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IOException if unable to create new directory on SFTP external server
     */
    private ImageExistPayload checkIfImageAlreadyExist(StatefulSFTPClient sftpClient, TempImageSavePayload payload)
            throws IOException {
        if (isNull(payload.getUserHashCode())) return new ImageExistPayload("", false);
        if (!hashCodeIsValid(payload.getUserHashCode())) throw new HashCodeFormatException();

        final String baseDir = "user" + payload.getId() + "_" + payload.getUserHashCode();
        final List<RemoteResourceInfo> rsImages = sftpClient.ls(imagesServerPath + "/" + baseDir);
        final Optional<String> foundImage = rsImages.stream().map(RemoteResourceInfo::getName)
                .filter(name -> name.startsWith(payload.getUniqueImagePrefix())).findFirst();

        if (foundImage.isPresent()) {
            sftpClient.cd(imagesServerPath + "/" + baseDir);
            sftpClient.rm(foundImage.get());
            return new ImageExistPayload(payload.getUserHashCode(), true);
        }
        return new ImageExistPayload(payload.getUserHashCode(), false);
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
     * @throws ExternalFileServerMalfunctionException if unable to save image in SFTP external server.
     */
    private BufferedImageRes generateTempImageAndSave(StatefulSFTPClient sftpClient, TempImageSavePayload payload) {
        final BufferedImageRes imageResponse = new BufferedImageRes();
        try {
            String userStaticImageDir, hashCode, relativeImagesPath;
            final String userImagePathPrefix = "user" + payload.getId() + "_";
            sftpClient.cd(imagesServerPath);

            final ImageExistPayload imageExistPayload = checkIfImageAlreadyExist(sftpClient, payload);
            if (imageExistPayload.getUserHashCode().isEmpty()) {
                hashCode = generateHashCode();
                userStaticImageDir = userImagePathPrefix + hashCode;
                createDirIfNotExist(sftpClient, imagesServerPath, userStaticImageDir);
            } else {
                hashCode = imageExistPayload.getUserHashCode();
                userStaticImageDir = userImagePathPrefix + imageExistPayload.getUserHashCode();
            }
            final File tempImage = createTempFile(payload.getUniqueImagePrefix() + "_", "." + payload.getExtensionName());
            if (hasLength(imagesRelativePath)) {
                relativeImagesPath = socketConnector.getAppServerPath() + "/" + imagesRelativePath + "/";
            } else {
                relativeImagesPath = socketConnector.getAppServerPath() + "/";
            }
            imageResponse.setLocation(relativeImagesPath + userStaticImageDir + "/" + tempImage.getName());

            Files.write(tempImage.toPath(), payload.getBytesRepresentation());
            sftpClient.put(new FileSystemFile(tempImage), imagesServerPath + "/" + userStaticImageDir);

            imageResponse.setBytesRepresentation(payload.getBytesRepresentation());
            imageResponse.setUserHashCode(hashCode);
            tempImage.deleteOnExit();
        } catch (IOException ex) {
            LOGGER.error("Unable to send image to external server. Server path: {}", imagesServerPath);
            throw new ExternalFileServerMalfunctionException();
        }
        return imageResponse;
    }

    /**
     * Inner method responsible for creating images server path (based default application server path). if parameter
     * <code>jmpsl.gfx.user-gfx.images-module-active</code> is setting to false, module not load and method to generating
     * image directory. Otherwise generated images directory (if not exist yet).
     *
     * @param env instance of {@link Environment} class passed from constructor
     * @return images full server path or null, if module is disabled
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    private String createImagesServerPath(Environment env) {
        final String imagesServerPath = env.getProperty("jmpsl.gfx.user-gfx.static-images-content-path");
        socketConnector.connectToSocketAndPerformAction(sftpClient -> {
            try {
                createDirIfNotExist(sftpClient, socketConnector.getServerPath(), imagesServerPath);
            } catch (IOException ex) {
                LOGGER.error("Unable to create static images server path. Images path: {}", imagesServerPath);
            }
        });
        return socketConnector.getServerPath() + "/" + imagesServerPath;
    }
}
