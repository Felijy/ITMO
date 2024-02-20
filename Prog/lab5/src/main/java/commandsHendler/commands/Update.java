package commandsHendler.commands;

import commandsHendler.AddValue;
import data.*;
import handler.mapHandler;
import handler.terminalHandler;

import java.time.LocalDate;

/**
 * Команда для изменения в коллекции элемента по его ключу
 */
public class Update extends AddValue {
    private String key;
    private Ticket ticket;
    private Venue venue;
    private Coordinates coordinates;

    public Update() {
        super(true);
    }

    @Override
    public boolean execute(String args) {
        this.key = args;
        if (mapHandler.checkKey(key)) {
            return false;
        }
        System.out.print("Начато изменение элемента с ключом " + this.key + "\n");
        ticket = new Ticket();
        venue = new Venue();
        coordinates = new Coordinates();
        ticket.setName(startAdding("название билета (не пустое)", false, null, null, false));
        ticket.setPrice(Float.parseFloat(startAdding("цену (больше нуля)", false, 0, null, false)));
        ticket.setDiscount(Integer.parseInt(startAdding("скидку (больше нуля, меньше 100)", false, 0, 100, false)));
        ticket.setComment(startAdding("комментарий", true, null, null, true));
        String currentType;
        do {
            currentType = startAdding("тип билета (USUAL, BUDGETARY, CHEAP)", false, null, null, false);
            if (!checkTicketTypeEnum(currentType)) System.out.println("!!!Ошибка. Попробуйте еще раз.");
        } while (!checkTicketTypeEnum(currentType));
        ticket.setType(TicketType.valueOf(currentType));
        coordinates.setX(Float.parseFloat(startAdding("координату по оси X, меньше 766", false, null, 766, false)));
        coordinates.setY(Float.parseFloat(startAdding("координату по оси Y", false, null, null, false)));
        ticket.setCoordinates(coordinates);
        venue.setName(startAdding("название места (не пустое)", false, null, null, false));
        venue.setCapacity(Long.parseLong(startAdding("вместимость, больше нуля", false, 0, null, false)));
        do {
            currentType = startAdding("тип места (BAR, LOFT, OPEN_AREA, THEATRE, STADIUM)", false, null, null, false);
            if (!checkVenueTypeEnum(currentType)) System.out.println("!!!Ошибка. Попробуйте еще раз.");
        } while (!checkVenueTypeEnum(currentType));
        venue.setType(VenueType.valueOf(currentType));
        venue.setNewId();
        ticket.setVenue(venue);
        ticket.setNewId();
        ticket.setCreationDate(LocalDate.now());
        mapHandler.makeNewTicket(key, ticket);
        return true;
    }


    /**
     * Метод для старта добавления нового элемента. Получив ввод от пользователя, вызывает метод ValidateNew() в абстрактном супер-классе
     */
    private String startAdding (String name, boolean canBeNull, Integer moreThen,
                                Integer lessThen, boolean canBeEmptyString) {
        boolean isEnd;
        String currentInput;
        do {
            System.out.print("Введите " + name + ": ");
            currentInput = terminalHandler.readLine();
            isEnd = validateNew(currentInput, canBeNull, moreThen, lessThen, canBeEmptyString);
            if (!isEnd) System.out.print("!!!Ошибка ввода. Попробуйте еще раз\n");
        } while (!isEnd);
        return currentInput;
    }

    /**
     * Метод для проверки, входит ли значение arg в Enum TicketType
     * @param arg значение, которое нужно проверить
     * @return true, если значение входит в enum, false -- в обратном случае
     */
    private boolean checkTicketTypeEnum(String arg) {
        try {
            TicketType.valueOf(arg);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    /**
     * Метод для проверки, входит ли значение arg в Enum VenueType
     * @param arg значение, которое нужно проверить
     * @return true, если значение входит в enum, false -- в обратном случае
     */
    private boolean checkVenueTypeEnum(String arg) {
        try {
            VenueType.valueOf(arg);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "Изменяет элемент в коллекции, в качестве аргумента указывается ключ изменяемого элемента";
    }

    @Override
    public String getName() {
        return "update <key> {element}";
    }
}
