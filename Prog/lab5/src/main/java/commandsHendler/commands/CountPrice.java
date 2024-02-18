package commandsHendler.commands;

import commandsHendler.Command;
import handler.mapHandler;
import handler.terminalHandler;

public class CountPrice extends Command {
    public CountPrice() {
        super(true);
    }

    @Override
    public boolean execute(String args) {
        var keys = mapHandler.getCollection().keySet();
        int count = 0;
        for (String i : keys) {
            try {
                if (mapHandler.getCollection().get(i).getPrice() == Float.parseFloat(args)) count++;
            } catch (NumberFormatException e) {
                terminalHandler.printlnA("!!!Ошибка числа. Повторите ввод");
            }
        }
        terminalHandler.printlnA("Количество элементов с price = " + args + ": " + count);
        return true;
    }

    @Override
    public String getDescription() {
        return "вывести количество элементов, значение price которых равно заданному в аргументе";
    }

    @Override
    public String getName() {
        return "count_by_price";
    }
}
