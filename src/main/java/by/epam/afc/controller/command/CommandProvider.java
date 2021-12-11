package by.epam.afc.controller.command;

import by.epam.afc.controller.command.impl.*;
import by.epam.afc.controller.command.impl.go.*;

import java.util.EnumMap;
import java.util.Optional;

import static by.epam.afc.controller.command.CommandType.*;

public class CommandProvider {
    private static final CommandProvider instance = new CommandProvider();
    private final EnumMap<CommandType, Command> commands = new EnumMap<>(CommandType.class);

    private CommandProvider() {
        commands.put(LOGIN_COMMAND, new LoginCommand());
        commands.put(CHANGE_LOCALE, new ChangeLocale());
        commands.put(FORGOT_PASSWORD, new ForgotPassword());
        commands.put(REGISTER_COMMAND, new RegisterCommand());
        commands.put(LOGOUT_COMMAND, new LogoutCommand());
        commands.put(TO_LOGIN_PAGE, new ToLoginPage());
        commands.put(FIND_ANNOUNCEMENTS, new FindAnnouncements());
        commands.put(SUBMIT_ANNOUNCEMENT, new SubmitAnnouncement());
        commands.put(SHOW_ANNOUNCEMENT, new ShowAnnouncement());
        commands.put(TO_SUBMIT_AD_PAGE, new ToSubmitAdPage());
        commands.put(FIND_MY_ANNOUNCEMENTS, new FindMyAnnouncements());
        commands.put(TO_ADMINISTRATOR_PAGE, new ToAdministratorPage());
        commands.put(TO_MODERATOR_PAGE, new ToModeratorPage());
        commands.put(TO_REGISTER_PAGE, new ToRegisterPage());
        commands.put(TO_MY_ANNOUNCEMENTS_PAGE, new ToMyAnnouncementsPage());
        commands.put(TO_CONFIRM_PAGE, new ToConfirmPage());
        commands.put(TO_BAN_PAGE, new ToBanPage());
        commands.put(CONFIRM_ACCOUNT, new ConfirmAccount());
        //commands.add(commandType,new Command());
    }

    public static CommandProvider getInstance() {
        return instance;
    }

    public Optional<Command> defineCommand(String commandName) {
        Optional<CommandType> commandType = typeForName(commandName);
        if (commandType.isPresent()) {
            Command command = commands.get(commandType.get());
            return Optional.of(command);
        }
        return Optional.empty();
    }

    public Optional<CommandType> typeForName(String commandName) {
        try {
            CommandType commandType = valueOf(commandName.toUpperCase());
            return Optional.of(commandType);
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
