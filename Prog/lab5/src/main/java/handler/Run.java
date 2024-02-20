package handler;

import commandsHendler.Command;
import exceptions.IncorrectInputException;
import handler.CommandHandler;
import handler.terminalHandler;

/**
 * Класс для старта и перехода в интерактивный режим работы с пользователем
 */
public class Run {
    /**
     * enum, в котором хранятся exitCode, которые возвращаются в зависимости от успешности/не успешности завершения команды
     */
    private enum exitCode {
        OK,
        ERROR,
        STOP,
        REPEAT;
    }

    /**
     * Пустой конструктор (т.к. класс не имеет полей)
     */
    public Run() {
    }

    /**
     * Переход в интерактивный режим, в котором пользователь может вводить команды
     */
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
        } while (currentCode != exitCode.STOP);
    }

    /**
     * Метод для выполнения команды, введенной пользователей
     * @param userCommand строка -- команда, которую ввел пользователь
     * @return exitCode в зависимости от успешности/не успешности выполнения команды
     */
    public exitCode executeCommand(String[] userCommand) {
        try {
            Command currentCommand = CommandHandler.getCommand(userCommand[0]);
            if (!currentCommand.hasAnArgument(userCommand.length)) {
                terminalHandler.printlnA("!!!Ошибка аргумента. Повторите ввод.");
                return exitCode.ERROR;
            }
            if (userCommand.length == 1) {
                if (currentCommand.execute(null)) terminalHandler.println("Команда выполнена успешно");
                else terminalHandler.printlnA("!!!Ошибка при выполнении команды. Попробуйте снова.");
            } else {
                if (currentCommand.execute(userCommand[1])) terminalHandler.println("Команда выполнена успешно");
                else terminalHandler.printlnA("!!!Ошибка при выполнении команды. Попробуйте снова.");
            }
        } catch (IncorrectInputException e) {
            terminalHandler.printlnA("!!!Команда не найдена. Повторите ввод.");
            return exitCode.ERROR;
        }
        return exitCode.OK;
    }
}
