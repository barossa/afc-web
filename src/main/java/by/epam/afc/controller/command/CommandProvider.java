package by.epam.afc.controller.command;

import by.epam.afc.controller.command.impl.*;
import by.epam.afc.controller.command.impl.go.*;

import java.util.EnumMap;
import java.util.Optional;

import static by.epam.afc.controller.command.CommandType.*;

public class CommandProvider {
    private static CommandProvider instance;
    private final EnumMap<CommandType, Command> commands = new EnumMap<CommandType, Command>(CommandType.class);

    private CommandProvider() {
        commands.put(LOGIN_COMMAND, new LoginCommand());
        commands.put(CHANGE_LOCALE, new ChangeLocale());
        commands.put(FORGOT_PASSWORD, new ForgotPassword());
        commands.put(REGISTER_COMMAND, new RegisterCommand());
        commands.put(LOGOUT_COMMAND, new LogoutCommand());
        commands.put(TO_CONFIRM_PAGE, new ToConfirmPage());
        commands.put(TO_LOGIN_PAGE, new ToLoginPage());
        commands.put(FIND_ANNOUNCEMENTS, new FindAnnouncements());
        commands.put(SUBMIT_ANNOUNCEMENT, new SubmitAnnouncement());
        commands.put(SHOW_ANNOUNCEMENT, new ShowAnnouncement());
        commands.put(TO_SUBMIT_AD_PAGE, new ToSubmitAdPage());
        commands.put(FIND_MY_ANNOUNCEMENTS, new FindMyAnnouncements());
        commands.put(TO_ADMINISTRATOR_PAGE, new ToAdministratorPage());
        commands.put(TO_MODERATOR_PAGE, new ToModeratorPage());
        //commands.add(commandType,new Command());
    }

    public static CommandProvider getInstance() {
        if (instance == null) {
            instance = new CommandProvider();
        }
        return instance;
    }

    public Optional<Command> defineCommand(String commandName) {
        try {
            CommandType commandType = valueOf(commandName.toUpperCase());
            Command command = commands.get(commandType);
            return Optional.of(command);
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }


    }
}
