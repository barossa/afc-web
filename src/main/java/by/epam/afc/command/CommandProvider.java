package by.epam.afc.command;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Optional;

import static by.epam.afc.command.CommandType.*;

public class CommandProvider {
    private static CommandProvider instance;
    private final EnumMap<CommandType, Command> commands = new EnumMap<CommandType, Command>(CommandType.class);

    private CommandProvider() {
        //commands.add(commandType,new Command());
    }

    public static CommandProvider getInstance() {
        if (instance == null) {
            instance = new CommandProvider();
        }
        return instance;
    }

    public Optional<Command> defineCommand(String commandName){
        return Arrays.stream(CommandType.values())
                .filter(commandType -> commandType.name().equalsIgnoreCase(commandName))
                .map(commands::get)
                .findFirst();
    }
}
