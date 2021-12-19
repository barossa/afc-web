package by.epam.afc.controller.command;

import by.epam.afc.controller.command.impl.*;
import by.epam.afc.controller.command.impl.FindUsers;
import by.epam.afc.controller.command.impl.go.*;

import java.util.EnumMap;
import java.util.Optional;

import static by.epam.afc.controller.command.CommandType.*;

/**
 * The type Command provider.
 */
public class CommandProvider {
    private static final CommandProvider instance = new CommandProvider();
    private final EnumMap<CommandType, Command> commands = new EnumMap<>(CommandType.class);

    private CommandProvider() {
        commands.put(LOGIN_COMMAND, new LoginCommand());
        commands.put(CHANGE_LOCALE, new ChangeLocale());
        commands.put(REGISTER_COMMAND, new RegisterCommand());
        commands.put(LOGOUT_COMMAND, new LogoutCommand());
        commands.put(TO_LOGIN_PAGE, new ToLoginPage());
        commands.put(FIND_ANNOUNCEMENTS, new FindAnnouncements());
        commands.put(SUBMIT_ANNOUNCEMENT, new SubmitAnnouncement());
        commands.put(SHOW_ANNOUNCEMENT, new ShowAnnouncement());
        commands.put(TO_SUBMIT_AD_PAGE, new ToSubmitAdPage());
        commands.put(FIND_MY_ANNOUNCEMENTS, new FindMyAnnouncements());
        commands.put(TO_ADMINISTRATOR_PAGE, new FindUsers());
        commands.put(TO_MODERATOR_PAGE, new ToModeratorPage());
        commands.put(TO_REGISTER_PAGE, new ToRegisterPage());
        commands.put(TO_MY_ANNOUNCEMENTS_PAGE, new ToMyAnnouncementsPage());
        commands.put(TO_CONFIRM_PAGE, new ToConfirmPage());
        commands.put(TO_BAN_PAGE, new ToBanPage());
        commands.put(CONFIRM_ACCOUNT, new ConfirmAccount());
        commands.put(TO_MY_PROFILE, new ToMyProfile());
        commands.put(FIND_USERS, new FindUsers());
        commands.put(BAN_USER, new BanUser());
        commands.put(TO_EDIT_USER_MODAL, new ToEditUserModal());
        commands.put(UPDATE_MY_PROFILE, new UpdateMyProfile());
        commands.put(UPDATE_USER, new UpdateUser());
        commands.put(CONFIRM_ANNOUNCEMENT, new ConfirmAnnouncement());
        commands.put(DEACTIVATE_ANNOUNCEMENT, new DeactivateAnnouncement());
        commands.put(CHANGE_ANNOUNCEMENT_STATUS, new ChangeAnnouncementStatus());
        commands.put(TO_EDIT_ANNOUNCEMENT, new ToEditAnnouncement());
        commands.put(UPDATE_ANNOUNCEMENT, new UpdateAnnouncement());
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static CommandProvider getInstance() {
        return instance;
    }

    /**
     * Define command optional.
     *
     * @param commandName the command name
     * @return the optional
     */
    public Optional<Command> defineCommand(String commandName) {
        Optional<CommandType> commandType = typeForName(commandName);
        if (commandType.isPresent()) {
            Command command = commands.get(commandType.get());
            return Optional.of(command);
        }
        return Optional.empty();
    }

    /**
     * Type for name optional.
     *
     * @param commandName the command name
     * @return the optional
     */
    public Optional<CommandType> typeForName(String commandName) {
        try {
            CommandType commandType = valueOf(commandName.toUpperCase());
            return Optional.of(commandType);
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
