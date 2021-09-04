package by.epam.afc.controller.command;

import by.epam.afc.controller.command.impl.LoginCommand;

import java.util.EnumMap;
import java.util.Optional;

import static by.epam.afc.controller.command.CommandType.LOGIN_COMMAND;
import static by.epam.afc.controller.command.CommandType.valueOf;

public class CommandProvider {
    private static CommandProvider instance;
    private final EnumMap<CommandType, Command> commands = new EnumMap<CommandType, Command>(CommandType.class);

    private CommandProvider() {
        commands.put(LOGIN_COMMAND, new LoginCommand());
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
