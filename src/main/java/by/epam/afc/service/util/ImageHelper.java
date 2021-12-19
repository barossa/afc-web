package by.epam.afc.service.util;

import by.epam.afc.dao.entity.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * The type Image helper.
 */
public class ImageHelper {
    private static final Logger logger = LogManager.getLogger(ImageHelper.class);

    private static final ImageHelper instance = new ImageHelper();

    private static final String NO_IMAGE_PIC_PATH = "images/no-image.jpg";
    private static final int EOF = -1;
    private static final String FIX_TARGET = " ";
    private static final String FIX_CONTENT = "+";

    private Image noImage;

    private ImageHelper() {
        try {
            noImage = loadImage(NO_IMAGE_PIC_PATH);
        } catch (IOException e) {
            logger.error("Error occurred while initializing default pictures", e);
        }
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ImageHelper getInstance() {
        return instance;
    }

    /**
     * Gets no image picture.
     *
     * @return the no image picture
     */
    public Image getNoImagePicture() {
        return noImage;
    }

    /**
     * Load image image.
     *
     * @param filepath the filepath
     * @return the image
     * @throws IOException the io exception
     */
    public Image loadImage(String filepath) throws IOException {
        Image image = Image.getBuilder()
                .build();
        InputStream fileStream = ImageHelper.class.getClassLoader().getResourceAsStream(filepath);
        byte[] bytes = readInputStream(fileStream);
        String encodedImage = Base64.getEncoder().encodeToString(bytes);
        image.setImage(encodedImage);
        return image;
    }

    /**
     * Read input stream byte [ ].
     *
     * @param inputStream the input stream
     * @return the byte [ ]
     * @throws IOException the io exception
     */
    public byte[] readInputStream(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            logger.error("Image input stream is empty!");
            return new byte[1];
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        while (true) {
            int value = inputStream.read();
            if (value == EOF) {
                break;
            }
            outputStream.write(value);
        }
        return outputStream.toByteArray();
    }

    /**
     * Fix image string.
     *
     * @param base64 the base 64
     * @return the string
     */
    public String fixImage(String base64) {
        return base64.replaceAll(FIX_TARGET, FIX_CONTENT);
    }
}
