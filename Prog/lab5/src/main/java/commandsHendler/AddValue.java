package commandsHendler;

import data.TicketType;

import java.util.Objects;

public abstract class AddValue extends Command {
    /**
     * Конструктор, устанавливающий значение полю anArgument
     *
     * @param arg true, если аргумент нужен, false в обратном случае
     */
    public AddValue(boolean arg) {
        super(arg);
    }

    //true если все ок
    protected boolean validateNew (String obj, boolean canBeNull, Integer moreThen, Integer lessThen,
                                  boolean canBeEmptyString) {
        if ((!canBeNull) && (obj == null)) return false;
        if (obj == null) return true;
        try {
            if ((moreThen != null) && (Double.parseDouble(obj) <= moreThen)) return false;
            if ((lessThen != null) && (Double.parseDouble(obj) >= lessThen)) return false;
        } catch (NumberFormatException e) {
            return false;
        }
        if ((!canBeEmptyString) && (Objects.equals(obj, ""))) return false;
        return true;
    }
}

