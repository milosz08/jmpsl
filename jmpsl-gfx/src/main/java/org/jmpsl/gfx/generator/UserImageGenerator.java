/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: UserImageGenerator.java
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

package org.jmpsl.gfx.generator;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import org.jmpsl.gfx.GfxEnv;
import org.jmpsl.gfx.GfxUtil;
import org.jmpsl.gfx.ImageExtension;

import static org.jmpsl.gfx.generator.ImageGeneratorException.FontSizeNotSupportedException;
import static org.jmpsl.gfx.generator.ImageGeneratorException.TooMuchInitialsCharactersException;
import static org.jmpsl.gfx.generator.ImageGeneratorException.ImageNotSupportedDimensionsException;

/**
 * Class storing methods responsible for generating default user image. Before run application, declare following
 * properties in <code>application.properties</code> file:
 *
 * <ul>
 *     <li><code>jmpsl.gfx.user-gfx.preferred-font-link</code> - path to font *.ttf file in /resources directory</li>
 *     <li><code>jmpsl.gfx.user-gfx.preferred-font-name</code> - name of custom loaded font</li>
 *     <li><code>jmpsl.gfx.user-gfx.preferred-hex-colors</code> - preferred colors for image generator, as hex array</li>
 *     <li><code>jmpsl.gfx.user-gfx.preferred-foreground-color</code> - preferred image initials text color, by default #fff</li>
 * </ul>
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Slf4j
@Service
public class UserImageGenerator {

    private static final Random RANDOM = new Random();

    private static final int MIN_IMAGE_SIZE = 20;
    private static final int MAX_IMAGE_SIZE = 5000;
    private static final int MAX_FONT_SIZE = 500;

    /**
     * Default static colors using for user image generator. Can be overrite by property
     * <code>jmpsl.file.user-gfx.preferred-hex-colors</code> in <code>application.properties</code> file.
     *
     * @since 1.0.2
     */
    private static final String[] DEF_COLORS = {
            "#f83f3d", "#fe5430", "#ff9634", "#ffbf41", "#cad958", "#85c15d", "#029489", "#00bcd2", "#1197ec",
            "#4151b0", "#6a3ab0", "#a128a9", "#ee1860",
    };

    private String fontName;
    private final Environment env;
    private final String foregroundImageColor;
    private final List<Color> preferredHexColors = new ArrayList<>();

    UserImageGenerator(Environment env) {
        this.env = env;
        foregroundImageColor = GfxEnv.__GFX_USER_FG_COLOR.getProperty(env);
        loadCustomFontFromExternalFile();
        convertAndLoadHexToRgbColorsArray();
        log.info("Successful insert properties in IserImageGenerator from application.properties file.");
    }

    /**
     * Method responsible for creating default user avatar (simple image with random color and initials). Generates
     * image based passed {@link BufferedImageGeneratorPayload} instance. If payload instance has preferredColor value,
     * then generate image with this color value instead of random color (from declared statically or in properties file).
     *
     * @param payload instance of {@link BufferedImageGeneratorPayload} class with image properties
     * @param extension {@link ImageExtension} enum type of image (png, jpeg etc.)
     * @return instance of {@link GeneratedImageRes} with generated user image as byte array output stream and color
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalStateException if image size or font size is too tiny or large or initials array has not 2 elements
     */
    public GeneratedImageRes generateDefaultUserImage(BufferedImageGeneratorPayload payload, ImageExtension extension) {
        if (payload.size() < MIN_IMAGE_SIZE || payload.size() > MAX_IMAGE_SIZE) {
            throw new ImageNotSupportedDimensionsException();
        }
        if (payload.fontSize() < 1 || payload.fontSize() > MAX_FONT_SIZE) throw new FontSizeNotSupportedException();
        if (payload.initials().length != 2) throw new TooMuchInitialsCharactersException();

        Color generatedColor;
        final String userInitials = String.valueOf(payload.initials());
        final BufferedImage bufferedImage = new BufferedImage(payload.size(), payload.size(), BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics = bufferedImage.createGraphics();

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        graphics.setFont(new Font(fontName, Font.PLAIN, payload.fontSize()));
        if (Objects.nonNull(payload.preferredColor())) {
            generatedColor = payload.preferredColor();
        } else {
            generatedColor = preferredHexColors.get(RANDOM.nextInt(preferredHexColors.size() - 1));
        }
        graphics.setPaint(generatedColor);
        graphics.fillRect(0, 0, payload.size(), payload.size());
        graphics.setColor(Color.decode(foregroundImageColor));

        int xPos = (payload.size() - graphics.getFontMetrics().stringWidth(userInitials)) / 2;
        int yPos = ((payload.size() - graphics.getFontMetrics().getHeight()) / 2) + graphics.getFontMetrics().getAscent();

        graphics.drawString(userInitials, xPos, yPos);
        graphics.dispose();

        log.info("Successful generated default image for user. Image properties data: {}", payload);
        return new GeneratedImageRes(GfxUtil.generateByteStreamFromBufferedImage(bufferedImage, extension), generatedColor);
    }

    /**
     * Method responsible for creating default user avatar (simple image with random color and initials). Generates
     * image based passed {@link BufferedImageGeneratorPayload} instance. If payload instance has preferredColor value,
     * then generate image with this color value instead of random color (from declared statically or in properties file).
     * Generate image with .png extension. To generate image with another extension use {@link #generateDefaultUserImage}
     * metod with {@link ImageExtension} parameter.
     *
     * @param payload instance of {@link BufferedImageGeneratorPayload} class with image properties
     * @return instance of {@link GeneratedImageRes} with generated user image as byte array output stream and color
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalStateException if image size or font size is too tiny or large or initials array has not 2 elements
     */
    public GeneratedImageRes generateDefaultUserImage(BufferedImageGeneratorPayload payload) {
        return generateDefaultUserImage(payload, ImageExtension.PNG);
    }

    /**
     * Inner method responsible for load custom font for user image generator. If custom font not specified, load default
     * system font. Otherwise load font from <code>/resources</code> directory.
     *
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws NullPointerException if <code>jmpsl.file.user-gfx.preferred-font-name</code> property is null
     */
    private void loadCustomFontFromExternalFile() {
        final String fontPath = GfxEnv.__GFX_USER_FONT_LINK.getProperty(env);
        if (Objects.isNull(fontPath)) {
            fontName = GfxUtil.getDefaultFont().getFontName();
            return;
        }
        fontName = GfxEnv.__GFX_USER_FONT_NAME.getProperty(env);
        try {
            final GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            final ClassPathResource resource = new ClassPathResource(fontPath);
            graphicsEnvironment.registerFont(Font.createFont(Font.TRUETYPE_FONT, resource.getFile()));
            log.info("Successful loaded custom font from external file. Font full path: {}", fontPath);
        } catch (IOException | FontFormatException ex) {
            log.error("Unable to load custom font from file. Exception: {}", ex.getMessage());
        }
    }

    /**
     * Inner method responsible for load preferred randomizer user generator image colors from
     * <code>application.properties</code> file. If colors array is null, insert default colors, declared in this class.
     *
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    private void convertAndLoadHexToRgbColorsArray() {
        final String hexColors = GfxEnv.__GFX_USER_HEX_COLORS.getProperty(env);
        if (Objects.isNull(hexColors) || hexColors.split(",").length == 0) {
            preferredHexColors.addAll(Arrays.stream(DEF_COLORS).map(Color::decode).collect(Collectors.toSet()));
            return;
        }
        for (final String hexColor : hexColors.split(",")) {
            try {
                preferredHexColors.add(Color.decode(hexColor));
            } catch (NumberFormatException ex) {
                log.error("Passed hex color is invalid. Color {} not loaded.", hexColor);
            }
        }
        log.info("Successful loaded user image generator colors: {}", GfxUtil.toHexStringArray(preferredHexColors));
    }
}
