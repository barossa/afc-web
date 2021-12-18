package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import by.epam.afc.dao.entity.*;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.impl.AnnouncementServiceImpl;
import by.epam.afc.service.util.ImageHelper;
import by.epam.afc.service.util.RequestParameterConverter;
import by.epam.afc.service.validator.AnnouncementValidator;
import by.epam.afc.service.validator.ImageValidator;
import by.epam.afc.service.validator.impl.AnnouncementValidatorImpl;
import by.epam.afc.service.validator.impl.ImageValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static by.epam.afc.controller.PagePath.*;
import static by.epam.afc.controller.RequestAttribute.*;
import static by.epam.afc.controller.SessionAttribute.USER;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;
import static by.epam.afc.dao.entity.Announcement.Status.MODERATING;
import static by.epam.afc.service.validator.impl.CredentialsValidatorImpl.NOT_VALID;

public class SubmitAnnouncement implements Command {
    private static final Logger logger = LogManager.getLogger(SubmitAnnouncement.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        RequestParameterConverter parameterConverter = RequestParameterConverter.getInstance();
        AnnouncementValidator announcementValidator = AnnouncementValidatorImpl.getInstance();
        ImageValidator imageValidator = ImageValidatorImpl.getInstance();
        Map<String, String> announcementData = parameterConverter.findAnnouncementData(request.getParameterMap());
        Map<String, String> validatedData = announcementValidator.validateData(announcementData);
        List<Image> images = validateImages(request.getParameterValues(IMAGE), imageValidator);
        try {
            if (!validatedData.containsValue(NOT_VALID) && !images.isEmpty()) {
                HttpSession session = request.getSession();
                User owner = (User) session.getAttribute(USER);
                Announcement announcement = buildAnnouncement(validatedData);
                announcement.setOwner(owner);
                images.forEach(image -> image.setUploadedBy(owner));
                announcement.setImages(images);
                AnnouncementServiceImpl announcementService = AnnouncementServiceImpl.getInstance();
                Optional<Announcement> saved = announcementService.save(announcement);
                if (saved.isPresent()) {
                    return new Router(FORWARD, MY_ANNOUNCEMENTS_PAGE);
                }

            }
            return new Router(FORWARD, SUBMIT_AD_PAGE);
        } catch (ServiceException e) {
            logger.error("Error occurred while submitting announcement", e);
            request.setAttribute(EXCEPTION_MESSAGE, "Error occurred while submitting announcement");
            return new Router(FORWARD, request.getContextPath() + ERROR_500);
        }
    }

    private Announcement buildAnnouncement(Map<String, String> validatedData) {
        String title = validatedData.get(TITLE);
        String description = validatedData.get(DESCRIPTION);
        BigDecimal price = new BigDecimal(validatedData.get(PRICE));
        int categoryId = Integer.parseInt(validatedData.get(CATEGORY));
        int regionId = Integer.parseInt(validatedData.get(REGION));
        Category category = new Category(categoryId);
        Region region = new Region(regionId);

        Announcement announcement = Announcement.getBuilder()
                .title(title)
                .price(price)
                .description(description)
                .category(category)
                .region(region)
                .status(MODERATING)
                .build();
        return announcement;
    }

    private List<Image> validateImages(String[] images, ImageValidator imageValidator) {
        if (images == null) {
            return new ArrayList<>();
        }
        ImageHelper imageHelper = ImageHelper.getInstance();
        List<Image> validatedImages = Arrays.stream(images)
                .filter(imageValidator::validateImage)
                .map(imageHelper::fixImage)
                .map(base64 -> Image.getBuilder()
                        .base64(base64)
                        .uploadData(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
        return validatedImages;
    }
}
