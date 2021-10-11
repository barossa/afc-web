package by.epam.afc.controller;

import by.epam.afc.dao.entity.Image;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static by.epam.afc.controller.SessionAttribute.UPLOADED_IMAGES;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(urlPatterns = {"/upload"})
public class UploadController extends HttpServlet {
    private static Logger logger = LogManager.getLogger(UploadController.class);

    private static final String TMP_PREFIX = "tmp";
    private static final int MAX_UPLOAD_FILES = 5;

    private static String UPLOAD_PATH;

    @Override
    public void init() throws ServletException {
        UPLOAD_PATH = getServletContext().getRealPath("") + File.separator + TMP_PREFIX;
        File uploadDir = new File(UPLOAD_PATH);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
    }

    @Override
    public void destroy() {
        try {
            Path tempPath = Paths.get(UPLOAD_PATH);
            Files.walk(tempPath)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            logger.error("Error occurred while clearing tmp directory", e);
        }
        logger.debug("Tmp directory successfully cleaned!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Set<String> files = new HashSet<>();
        try {

            for (Part part : req.getParts()) {
                String fileName = part.getSubmittedFileName();
                if (!fileName.isEmpty()) {
                    files.add(fileName);
                    part.write(UPLOAD_PATH + File.separator + fileName);
                } else {
                    logger.debug("No file to upload");
                    resp.sendError(500);
                    return;
                }
            }
            HttpSession session = req.getSession();
            boolean result = loadImagesToSession(files, session);
            if (!result) {
                logger.error("Images aren't uploaded!");
                resp.sendError(500);
            }

        } catch (IOException e) {
            logger.error("Error occurred while uploading files", e);
            resp.sendError(500);
        }
    }

    private boolean loadImagesToSession(Set<String> files, HttpSession session) {
        if (files.size() > MAX_UPLOAD_FILES) {
            return false;
        }

        try {
            List<Image> images = new ArrayList<>();
            for (String filepath : files) {
                File file = new File(UPLOAD_PATH + File.separator + filepath);
                if (!file.exists()) {
                    return false;
                }
                Image image = encodeImage(file);
                images.add(image);
            }

            session.setAttribute(UPLOADED_IMAGES, images);
            return true;

        } catch (IOException e) {
            logger.debug("Error occurred while loading images to session", e);
            return false;
        }
    }

    private Image encodeImage(File file) throws IOException {
        byte[] bytes = Files.readAllBytes(file.toPath());
        String encodedImage = Base64.getEncoder().encodeToString(bytes);
        Image image = Image.getBuilder()
                .base64(encodedImage)
                .build();
        return image;
    }
}
