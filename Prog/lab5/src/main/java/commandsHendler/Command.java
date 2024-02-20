package commandsHendler;

import data.Ticket;
import exceptions.ArgumentException;

import java.util.HashMap;

/**
 * Абстрактный класс-родитель для всех классов, служащих для выполнения команд
 */
public abstract class Command implements Executable{
    /**
     * Поле, обозначающие, нужен ли команде аргумент или нет
     */
    private final boolean anArgument;

    /**
     * Если аргумент нужен, он помещается в это поле
     */
    private Object argument;

    /**
     * Конструктор, устанавливающий значение полю anArgument
     * @param arg true, если аргумент нужен, false в обратном случае
     */
    public Command(boolean arg) {
        this.anArgument = arg;
    }

    /**
     * Абстрактный метод для выполнения тела команды
     */
    public abstract boolean execute(String args);

    /**
     * Абстрактный метод для получения описания команды
     * @return описание команды
     */
    public abstract String getDescription();

    /**
     * Абстрактный метод для получения названия команды
     * @return название команды
     */
    public abstract String getName();

    /**
     * Метод, возвращающий проверку функции на наличие/отсутствие аргумента
     * @return true, если наличие/отсутствие аргумента корректно, false в обратном случае
     */
    public boolean hasAnArgument(int length) {
        if (anArgument && (length > 1)) return true;
        if (!anArgument && (length == 1)) return true;
        return false;
    }
}
