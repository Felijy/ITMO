package handler;

import commandsHendler.Command;
import exceptions.IncorrectInputException;
import handler.CommandHandler;
import handler.terminalHandler;

public class Run {
    private enum exitCode {
        OK,
        ERROR,
        STOP,
        REPEAT;
    }

    public Run() {
    }

    public void userCommandFetch() {
        exitCode currentCode;
        do {
            String inputCommand = terminalHandler.readLine();
            if (inputCommand == null) currentCode = exitCode.REPEAT;
            else {
                if (inputCommand != "") {
                    String[] commandParts = inputCommand.split(" ", 2);
                    currentCode = executeCommand(commandParts);
                } else {
                    currentCode = exitCode.REPEAT;
                }
            }
        } while (currentCode != exitCode.STOP); // Возможно, добавить сюда STOP при сохранении/выходе досрочном и проверять его ниже
    }

    public exitCode executeCommand(String[] userCommand) {
        try {
            Command currentCommand = CommandHandler.getCommand(userCommand[0]);
            if (!currentCommand.hasAnArgument(userCommand.length)) {
                terminalHandler.printlnA("!!!Ошибка аргумента. Повторите ввод.");
                return exitCode.ERROR;
            }
            if (userCommand.length == 1) {
                if (currentCommand.execute(null)) terminalHandler.printlnA("Команда выполнена успешно");
                else terminalHandler.printlnA("!!!Ошибка при выполнении команды. Попробуйте снова.");
            } else {
                if (currentCommand.execute(userCommand[1])) terminalHandler.printlnA("Команда выполнена успешно");
                else terminalHandler.printlnA("!!!Ошибка при выполнении команды. Попробуйте снова.");
            }
        } catch (IncorrectInputException e) {
            terminalHandler.printlnA("!!!Команда не найдена. Повторите ввод.");
            return exitCode.ERROR;
        }
        return exitCode.OK;
    }
}
