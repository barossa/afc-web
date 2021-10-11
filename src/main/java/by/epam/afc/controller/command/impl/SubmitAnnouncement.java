package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import by.epam.afc.dao.entity.*;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.impl.AnnouncementServiceImpl;
import by.epam.afc.service.validator.impl.AnnouncementValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.epam.afc.controller.PagePath.*;
import static by.epam.afc.controller.RequestAttribute.*;
import static by.epam.afc.controller.SessionAttribute.UPLOADED_IMAGES;
import static by.epam.afc.controller.SessionAttribute.USER;
import static by.epam.afc.controller.command.Router.DispatchType.*;

public class SubmitAnnouncement implements Command {
    private static Logger logger = LogManager.getLogger(SubmitAnnouncement.class);
// TODO: 10/6/21 PERMISSION RULES ON COMMANDS

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        List<Image> uploadedImages = (List<Image>) session.getAttribute(UPLOADED_IMAGES);
        session.setAttribute(UPLOADED_IMAGES, null);
        if(uploadedImages.isEmpty()){
            logger.warn("No one image is uploaded for submitting announcement");
        }

        Map<String, String> attributes = getAnnouncementAttributes(request);
        AnnouncementValidatorImpl announcementValidator = AnnouncementValidatorImpl.getInstance();
        Map<String, String> validatedData = announcementValidator.validateData(attributes);

        if(!validatedData.containsValue("")){

            try {
                User owner = (User) session.getAttribute(USER);
                String title = validatedData.get(TITLE);
                BigDecimal price = new BigDecimal(validatedData.get(PRICE));
                String description = validatedData.get(DESCRIPTION);
                int categoryId = Integer.parseInt(validatedData.get(CATEGORY));
                Category category = new Category(categoryId);
                int regionId = Integer.parseInt(validatedData.get(REGION));
                Region region = new Region(regionId);

                Announcement announcement = Announcement.getBuilder()
                        .owner(owner)
                        .title(title)
                        .price(price)
                        .description(description)
                        .category(category)
                        .region(region)
                        .images(uploadedImages)
                        .build();

                AnnouncementServiceImpl announcementService = AnnouncementServiceImpl.getInstance();
                Optional<Announcement> saved = announcementService.save(announcement);

                if(saved.isPresent()){
                    logger.debug("Announcement saved");
                    return new Router(REDIRECT, INDEX);
                }else{
                    return new Router(FORWARD, SUBMIT_AD_PAGE);
                }

            } catch (ServiceException e) {
                logger.error("Error occurred while submitting announcement", e);
                return new Router(REDIRECT, request.getContextPath() + ERROR_500);
            }

        }else{
            //ERROR
            logger.debug("DATA CONTAINS INVALID DATA");
            System.out.println(validatedData);
            return new Router(FORWARD, SUBMIT_AD_PAGE);
        }
    }

    private Map<String, String> getAnnouncementAttributes(HttpServletRequest request){
        Map<String, String> attributes = new HashMap<>();

        String title = request.getParameter(TITLE);
        String price = request.getParameter(PRICE);
        String description = request.getParameter(DESCRIPTION);
        String region = request.getParameter(REGION);
        String category = request.getParameter(CATEGORY);

        attributes.put(TITLE, title);
        attributes.put(PRICE, price);
        attributes.put(DESCRIPTION, description);
        attributes.put(REGION, region);
        attributes.put(CATEGORY, category);

        return attributes;
    }

}
