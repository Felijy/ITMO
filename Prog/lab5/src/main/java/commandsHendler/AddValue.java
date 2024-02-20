package commandsHendler;

import data.TicketType;

import java.util.Objects;

/**
 * Абстрактный класс, являющиеся родительским для всех классов, добавляющих элементы в коллекцию
 */
public abstract class AddValue extends Command implements Executable {
    /**
     * Конструктор, устанавливающий значение полю anArgument
     *
     * @param arg true, если аргумент нужен, false в обратном случае
     */
    public AddValue(boolean arg) {
        super(arg);
    }

    /**
     * Метод для валидации данных, которые должны быть добавлены в коллекцию
     * @param obj строка -- то, что пользователь ввёл
     * @param canBeNull может ли строка быть null
     * @param moreThen должно ли число быть больше какого-то значения (если получаемые данные -- строка или число без ограничения сверху/снизу, то указывается null)
     * @param lessThen должно ли число быть меньше какого-то значения (если получаемые данные -- строка или число без ограничения сверху/снизу, то указывается null)
     * @param canBeEmptyString может ли строка быть пустой
     * @return true, если валидация успешна, в ином случае -- false
     */
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

